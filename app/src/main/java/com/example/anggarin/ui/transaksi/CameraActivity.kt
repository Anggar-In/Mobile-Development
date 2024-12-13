package com.example.anggarin.ui.transaksi

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.anggarin.R
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.BudgetRequest
import com.example.anggarin.databinding.ActivityCameraBinding
import com.example.anggarin.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menyiapkan Executor untuk thread kamera
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Langsung mulai kamera tanpa pemeriksaan izin
        startCamera()

        binding.captureButton.setOnClickListener {
            Log.d("CameraActivity", "Capture button clicked")
            imageCapture?.let {
                Log.d("CameraActivity", "Starting image capture...")
                captureImage(it)
            } ?: run {
                Log.e("CameraActivity", "ImageCapture is not initialized")
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            // Mendapatkan camera provider
            cameraProvider = cameraProviderFuture.get()

            // Membuat preview use case
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

            // Membuat capture use case untuk mengambil gambar (opsional)
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(windowManager.defaultDisplay.rotation)
                .build()

            // Memilih kamera belakang sebagai kamera default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Menyambungkan use case ke camera provider
                cameraProvider.unbindAll() // Membebaskan kamera yang sedang digunakan
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.e("CameraActivity", "Binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun captureImage(imageCapture: ImageCapture) {
        // Tempat file untuk menyimpan gambar
        val photoFile = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "receipt_${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        Log.d("CameraActivity", "Attempting to capture image...")

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CameraActivity", "Image saved successfully: ${photoFile.absolutePath}")
                    uploadReceipt(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraActivity", "Image capture failed", exception)
                    Toast.makeText(this@CameraActivity, "Failed to capture image", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun uploadReceipt(photoFile: File) {
        val userPreference = UserPreference(applicationContext)

        lifecycleScope.launch {
            val token = userPreference.getToken().first() // Ambil token dari penyimpanan
            if (token.isNullOrEmpty()) {
                Toast.makeText(this@CameraActivity, "Token tidak valid, silakan login kembali.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CameraActivity, LoginActivity::class.java))
                finish()
                return@launch
            }

            try {
                // Buat request body
                val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("receipt", photoFile.name, requestFile)

                // Ambil instance API service
                val apiService = ApiConfig.getApiService(applicationContext)

                // Kirim request ke server
                val response = apiService.uploadReceipt("Bearer $token", imagePart)

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!

                    // Proses respons server
                    val extractedText = responseBody.extractedText // Contoh respons
                    val company = responseBody.parsedReceipt?.company
                    val total = responseBody.parsedReceipt?.total
                    val items = responseBody.parsedReceipt?.items

                    // Lanjutkan ke activity lain
                    val intent = Intent(this@CameraActivity, PengeluaranActivity::class.java).apply {
                        putExtra("EXTRACTED_TEXT", extractedText)
                        putExtra("COMPANY", company)
                        putExtra("TOTAL", total)
                        putExtra("ITEMS", items.toString())
                    }
                    startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CameraActivity", "Error uploading receipt: $errorBody")
                    Toast.makeText(this@CameraActivity, "Upload gagal: $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("CameraActivity", "Upload error", e)
                Toast.makeText(this@CameraActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }
}
