package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityCameraBinding
import org.tensorflow.lite.task.vision.detector.Detection

class CameraActivity : AppCompatActivity(), OnSignDetectedAlert {

    private lateinit var activityStartBinding: ActivityCameraBinding
    private var mMediaPlayer: MediaPlayer? = null
    private var detectedState: Boolean = false
    private var hasPlayedRecently: Boolean = false
    private var detectionsCounter: Int = 0
    private var detectionsRequiredShared: Int = 30
    private var detectionsOverlapShared: Int = 30


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)

        val intent = Intent(this, MainActivity::class    .java)
        detectionsRequiredShared = intent.getIntExtra("detections_required", 30)
        detectionsOverlapShared = intent.getIntExtra("detections_overlap", 30)

        val goBackButton: RelativeLayout = findViewById(R.id.cameraModeGoBack)
        goBackButton.setOnClickListener{
            intent.putExtra("detection_required", detectionsRequiredShared)
            intent.putExtra("detection_overlap", detectionsOverlapShared)
            startActivity(intent)
        }

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
            detectedState = false
            if (results != null) {

                // counting the successful detections
                for (result in results){
                    if (result.categories[0].score > threshold){
                        detectionsCounter+=1
                        detectedState=true
                    }
                }

                Log.d("COUNTER_CAMERA", detectionsCounter.toString())

                // if num of detection is already above the required, sign the alert
                if (detectedState && detectionsCounter>=detectionsRequired) {
                    detectionsCounter=detectionsOverlap

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

            // playing sound alert
            if (detectedState && !hasPlayedRecently) {
                soundAlert()
                hasPlayedRecently = true
            }

            // receiving the updated parameters from the controllers on UI
            detectionsRequiredShared = detectionsRequired
            detectionsOverlapShared = detectionsOverlap
        }

    }
}