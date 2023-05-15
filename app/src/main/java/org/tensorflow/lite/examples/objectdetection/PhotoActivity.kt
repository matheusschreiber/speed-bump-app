package org.tensorflow.lite.examples.objectdetection

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityPhotoBinding
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.File


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

                val cursor = contentResolver.query(pickedPhoto!!, arrayOf(MediaStore.Images.Media.DISPLAY_NAME), null, null, null)
                cursor?.moveToFirst()
                val imageName = cursor?.getString(0)
                cursor?.close()


                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    galleryPhoto.setImageBitmap(pickedBitMap)
                    runObjectDetection(pickedBitMap!!, galleryPhoto, imageName)
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    galleryPhoto.setImageBitmap(pickedBitMap)
                    runObjectDetection(pickedBitMap!!, galleryPhoto, imageName)
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }


            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSignDetected(detected:Boolean) {}

    private fun runObjectDetection(bitmap: Bitmap, galleryPhoto: ImageView, imageName: String?) {
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

        // Logging the detection result on a file
        val directory = getExternalFilesDir(null)
        val imageNameWithoutExtension = imageName?.split('.')?.get(0)
        val fileName = "output-$imageNameWithoutExtension.txt"
        try {
            val file = File(directory, fileName)
            file.writeText(resultToDisplay.toString())
            Log.d("FileWrite", "Successfully wrote file $fileName")
        } catch (e: Exception) {
            Log.e("FileWrite", "Error writing file $fileName: ${e.message}")
        }

        // Debug
        Log.d("RESULTS", resultToDisplay.toString())

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
            pen.strokeWidth = 16F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)

            val tagSize = Rect(0, 0, 0, 0)
            pen.textSize = 136F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)

            // Calculate the bounding rectangle for the text
            val textRect = RectF(
                box.left,
                box.top,
                box.left + tagSize.width(),
                box.top + tagSize.height()*1.3F
            )

            pen.style = Paint.Style.FILL
            pen.color = Color.BLACK
            canvas.drawRect(textRect, pen)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.WHITE
            pen.strokeWidth = 2F

            canvas.drawText(
                it.text, box.left,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

}