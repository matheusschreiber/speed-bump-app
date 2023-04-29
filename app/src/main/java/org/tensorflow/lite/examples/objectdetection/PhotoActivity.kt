package org.tensorflow.lite.examples.objectdetection

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityPhotoBinding
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class PhotoActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityStartBinding: ActivityPhotoBinding
    data class DetectionResult(val boundingBox: RectF, val text: String)

    var pickedPhoto : Uri? = null
    var pickedBitMap : Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)

        val goBackButton: RelativeLayout = findViewById(R.id.photoModeGoBack)
        goBackButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class    .java)
            startActivity(intent)
        }

    }

    fun pickPhoto(view: View){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        } else {
            val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val galleryPhoto = findViewById<ImageView>(R.id.gallerySelected)
        val galleryButton = findViewById<ImageView>(R.id.galleryButton)
        val galleryText = findViewById<TextView>(R.id.galleryText)

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    galleryPhoto.setImageBitmap(pickedBitMap)
                    runObjectDetection(pickedBitMap!!, galleryPhoto)
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    galleryPhoto.setImageBitmap(pickedBitMap)
                    runObjectDetection(pickedBitMap!!, galleryPhoto)
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSignDetected(detected:Boolean) {}

    private fun runObjectDetection(bitmap: Bitmap, galleryPhoto: ImageView) {
        // Step 1: Create TFLite's TensorImage object
        var processedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
        val image = TensorImage.fromBitmap(processedBitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.3f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            this,
            "speedbump.tflite",
            options
        )

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text)
        }
        // Draw the detection result on the bitmap and show it.
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        runOnUiThread {
            galleryPhoto.setImageBitmap(imgWithResult)
        }
    }

    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = 96F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

}