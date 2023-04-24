/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var alertDetectionText: ImageView
    private var detectedState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val buttonCameraMode: LinearLayout = findViewById(R.id.buttonCameraMode)
        buttonCameraMode.setOnClickListener{
            val intent = Intent(this, CameraActivity::class    .java)
            startActivity(intent)
        }

        val buttonPhotoMode: LinearLayout = findViewById(R.id.buttonTestImage)
        buttonPhotoMode.setOnClickListener{
            val intent = Intent(this, PhotoActivity::class    .java)
            startActivity(intent)
        }

        alertDetectionText = findViewById(R.id.alert_detection)
        scaleView(alertDetectionText, startScale = .8f, endScale = 0f)

    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    fun scaleView(v: ImageView , startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            startScale, endScale,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, .5f,
            Animation.RELATIVE_TO_SELF, .5f);
        anim.fillAfter = true;
        anim.duration = 500;
        v.startAnimation(anim);
    }

    override fun onSignDetected(detected:Boolean) {
        runOnUiThread{
            if (detected && !detectedState){
                detectedState=true
                scaleView(alertDetectionText, startScale = .0f, endScale = .8f)
            } else if (!detected && detectedState) {
                scaleView(alertDetectionText, startScale = .8f, endScale = 0f)
                detectedState=false
            }
        }
    }
}
