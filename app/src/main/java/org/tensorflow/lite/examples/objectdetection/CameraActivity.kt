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
    private var hasPlayedRecently: Boolean = false

    private var hasDetected: Boolean = false
    private var detectionsCounter: Int = 0

    private var detectionsRequiredShared: Int = 20
    private var detectionsOverlapShared: Int = 60


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStartBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(activityStartBinding.root)

        val intent = Intent(this, MainActivity::class    .java)
        detectionsRequiredShared = intent.getIntExtra("detections_required", 20)
        detectionsOverlapShared = intent.getIntExtra("detections_overlap", 60)

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
            hasDetected = false
            if (results != null) {

                // counting the successful detections
                for (result in results){
                    if (result.categories[0].score > threshold){
                        detectionsCounter+=1
                        hasDetected=true
                    }
                }

                Log.d("RESULTS", results.toString())
                Log.d("COUNTER_CAMERA", detectionsCounter.toString())

                // if num of detection is already at the required level, sound the alert
                if (hasDetected && detectionsCounter==detectionsRequired) {
                    soundAlert()
                    detectionsCounter=detectionsOverlap

                // if num of detections is above the required, it means the alert has been played recently
                } else if (hasDetected && detectionsCounter>detectionsRequired) {
                    detectionsCounter = detectionsOverlap

                // the counter goes down with the passage of time
                } else if (!hasDetected && detectionsCounter>0) detectionsCounter-=1
            }

            // TODO: procurar por detectedState nas outras aprtes do código para limpar depois

            // receiving the updated parameters from the controllers on UI
            detectionsRequiredShared = detectionsRequired
            detectionsOverlapShared = detectionsOverlap
        }

    }
}