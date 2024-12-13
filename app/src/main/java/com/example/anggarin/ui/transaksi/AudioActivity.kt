package com.example.anggarin.ui.transaksi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.anggarin.R
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.VoiceInputRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AudioActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var tvTranscription: TextView
    private val REQUEST_RECORD_AUDIO_PERMISSION = 1

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
                // Log untuk mengecek apakah sudah siap untuk mendengarkan
                Log.d("AudioActivity", "Ready to start listening...")
                Toast.makeText(applicationContext, "Listening...", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                // Log error yang terjadi pada speech recognition
                Log.d("AudioActivity", "Speech recognition error: $error")
                Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.d("AudioActivity", "Speech results: $matches")
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
        val userPreference = UserPreference(applicationContext)

        lifecycleScope.launch {
            val token = userPreference.getToken().first()

            if (!token.isNullOrEmpty()) {
                val voiceRequest = VoiceInputRequest(fullTranscriptText = transcript)
                val apiService = ApiConfig.getApiService(this@AudioActivity)

                try {
                    // Log untuk memastikan data yang akan dikirim ke server
                    Log.d("AudioActivity", "Sending transcript to server: $transcript")
                    val response = apiService.submitVoiceInput("Bearer $token", voiceRequest)

                    if (response.isSuccessful && response.body() != null) {
                        runOnUiThread {
                            val responseBody = response.body()!!
                            val incomeId = responseBody.incomeId
                            Toast.makeText(applicationContext, "Income recorded: $incomeId", Toast.LENGTH_SHORT).show()

                            // Pindah ke activity Pengeluaran
                            val intent = Intent(this@AudioActivity, PengeluaranActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                        runOnUiThread {
                            Log.e("AudioActivity", "Error response: $errorMsg")
                            Toast.makeText(applicationContext, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Log.e("AudioActivity", "Exception: ${e.message}")
                        Toast.makeText(applicationContext, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Menangani izin
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, sekarang dapat mulai mendengarkan
                Log.d("AudioActivity", "Permission granted for audio recording")
                startListening()
            } else {
                Log.d("AudioActivity", "Permission denied for audio recording")
                Toast.makeText(this, "Permission required to record audio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
