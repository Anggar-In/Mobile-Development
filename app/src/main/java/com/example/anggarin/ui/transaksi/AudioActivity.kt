package com.example.anggarin.ui.transaksi

import android.Manifest
import android.app.VoiceInteractor
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.IOException
import com.example.anggarin.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.util.*

class AudioActivity : AppCompatActivity() {


    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var tvTranscription: TextView
    private val REQUEST_RECORD_AUDIO_PERMISSION = 1
    private val token = "your_token_here"
    private val categoryId = "your_category_id"
    private val apiUrl = "https://your-cloud-api.com/voice-input" // Ganti dengan URL API kamu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)

        tvTranscription = findViewById(R.id.tvTranscription)
        val btnStartListening: Button = findViewById(R.id.btnStartListening)

        // Cek izin untuk merekam audio
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext, "Listening...", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    val transcript = matches[0]
                    tvTranscription.text = transcript
                    sendToServer(transcript) // Kirim teks transkripsi ke server
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        btnStartListening.setOnClickListener {
            startListening()
        }
    }

    // Mulai mendengarkan
    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID")  // Ganti dengan bahasa yang sesuai
        speechRecognizer.startListening(intent)
    }

    // Kirim transkripsi ke server
    private fun sendToServer(transcript: String) {
        val client = OkHttpClient()
        val json = JSONObject()
        json.put("fullTranscriptText", transcript)

        // Membuat request body yang sesuai dengan format JSON
        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        // Membuat request dengan builder
        val request = Request.Builder()
            .url(apiUrl)  // Pastikan URL yang digunakan sudah benar
            .post(requestBody)  // Menggunakan metode POST dengan body yang telah dibuat
            .addHeader("Authorization", "Bearer $token")  // Ganti dengan token yang sesuai
            .addHeader("category_id", categoryId)  // Ganti dengan category ID yang sesuai
            .build()

        // Menjalankan request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to send request", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        val responseBody = response.body?.string()
                        val responseJson = JSONObject(responseBody)
                        val incomeId = responseJson.getString("income_id")
                        Toast.makeText(applicationContext, "Income recorded: $incomeId", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    // Menangani izin
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, sekarang dapat mulai mendengarkan
                startListening()
            } else {
                Toast.makeText(this, "Permission required to record audio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
