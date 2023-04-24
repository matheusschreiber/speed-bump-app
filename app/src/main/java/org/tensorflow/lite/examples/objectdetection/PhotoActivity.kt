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

class PhotoActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityStartBinding: ActivityPhotoBinding

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
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    galleryPhoto.setImageBitmap(pickedBitMap)
                    galleryText.setVisibility(View.INVISIBLE)
                    galleryButton.setVisibility(View.INVISIBLE)
                }
            }


        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSignDetected(detected:Boolean) {}


}