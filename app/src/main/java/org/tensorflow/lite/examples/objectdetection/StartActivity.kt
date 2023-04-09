package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityStartBinding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)


        val goBackButton: RelativeLayout = findViewById(R.id.cameraModeGoBack)
        goBackButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class    .java)
            startActivity(intent)
        }

    }

    override fun onSignDetected(detected:Boolean) {}
}