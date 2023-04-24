package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityStartBinding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)


        val goBackButton: RelativeLayout = findViewById(R.id.cameraModeGoBack)
        goBackButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class    .java)
            startActivity(intent)
        }

    }

    override fun onSignDetected(detected:Boolean) {}
}