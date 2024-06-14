package ai.ftech.fstt

import ai.ftech.fsttsdk.domain.exceptions.AppException
import ai.ftech.fsttsdk.sdk.STTManager
import ai.ftech.fsttsdk.sdk.IInitGatewayCallback
import ai.ftech.fsttsdk.sdk.ISTTCallback
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, you can start recording audio
            } else {
                // Permission denied, handle accordingly
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val initButton = findViewById<AppCompatButton>(R.id.btnInit)
        val startButton = findViewById<AppCompatButton>(R.id.btnRecord)
        val stopButton = findViewById<AppCompatButton>(R.id.btnStopRecord)
        val tvResult = findViewById<AppCompatTextView>(R.id.tvResult)

        if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
        }

        STTManager.init(applicationContext)

        STTManager.registerSTTCallback(object : ISTTCallback {
            override fun onStart() {
                startButton.text = "Recording..."
                startButton.isEnabled = false
                stopButton.isEnabled = true
                tvResult.text = ""
            }

            override fun onRecording() {

            }

            override fun onFail(error: AppException?) {
                runOnUiThread {
                    startButton.text = "Start"
                    startButton.isEnabled = true
                    stopButton.isEnabled = false
                    tvResult.text = error?.message
                }
            }

            override fun onSuccess(result: String) {
                runOnUiThread {
                    startButton.text = "Start"
                    startButton.isEnabled = true
                    stopButton.isEnabled = false
                    tvResult.text = result
                }
            }

        })

        initButton.setOnClickListener {
            STTManager.initGateway("103209", "988c8ce3b7773b46f391b772bcfbac1e", object : IInitGatewayCallback {
                override fun onSuccess() {
                    runOnUiThread {
                        initButton.visibility = View.INVISIBLE
                        startButton.visibility = View.VISIBLE
                        startButton.isEnabled = true
                        stopButton.visibility = View.VISIBLE
                        stopButton.isEnabled = false
                    }
                }

                override fun onFail(error: AppException?) {

                }
            })
        }

        startButton.setOnClickListener {
            STTManager.startSTT()
        }

        stopButton.setOnClickListener {
            startButton.text = "Processing..."
            stopButton.isEnabled = false
            STTManager.stopSTT()
        }
    }
}