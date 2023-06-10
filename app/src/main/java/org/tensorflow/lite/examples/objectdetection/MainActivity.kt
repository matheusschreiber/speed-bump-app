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
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding
import org.tensorflow.lite.task.vision.detector.Detection

class MainActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var alertDetectionText: ImageView
    private var mMediaPlayer: MediaPlayer? = null
    private var hasPlayedRecently: Boolean = true
    private var detectedState: Boolean = false
    private var detectionsCounter: Int = 0
    private var detectionsRequiredShared: Int = 5
    private var detectionsOverlapShared: Int = 5



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val intent = Intent(this, CameraActivity::class    .java)
        detectionsRequiredShared = intent.getIntExtra("detections_required", 30)
        detectionsOverlapShared = intent.getIntExtra("detections_overlap", 30)

        val buttonCameraMode: LinearLayout = findViewById(R.id.buttonCameraMode)
        buttonCameraMode.setOnClickListener{
            intent.putExtra("detection_required", detectionsRequiredShared)
            intent.putExtra("detection_overlap", detectionsOverlapShared)
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

    private fun soundAlert() {
        Toast.makeText(this, "Speed bump detected", Toast.LENGTH_SHORT).show()
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.detection_soundeffect)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }


    override fun onSignDetected(results:MutableList<Detection>?, threshold:Float, detectionsRequired:Int, detectionsOverlap: Int) {
        runOnUiThread{
            detectedState=false
            if (results != null) {

                // counting the successful detections
                for (result in results){
                    if (result.categories[0].score > threshold){
                        detectionsCounter+=1
                        detectedState=true
                    }
                }

                Log.d("COUNTER_MAIN",detectionsCounter.toString())

                // if num of detection is already above the required, sign the alert
                if (detectedState && detectionsCounter>=detectionsRequiredShared) {
                    detectionsCounter=detectionsOverlapShared

                // if its below, wait until the overlap ends before releasing the alert
                } else if (detectedState && detectionsCounter>0) {
                    detectedState=false

                    // if no detections are encountered for a while and the counter is now 0, then it can alert again
                } else if (detectionsCounter==0) {
                    detectedState=false
                    hasPlayedRecently=false
                }

                // the counter goes down with the passage of time
                if (detectionsCounter>0) detectionsCounter-=1
            }

            // applying scale and playing sound alert
            if (detectedState && !hasPlayedRecently) {
                scaleView(alertDetectionText, startScale = .0f, endScale = .8f)
                hasPlayedRecently = true
                soundAlert()
            } else if (hasPlayedRecently){
                scaleView(alertDetectionText, startScale = .8f, endScale = 0f)
            }
        }
    }
}