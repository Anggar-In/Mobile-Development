import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.ResendOtpRequest
import com.example.anggarin.data.response.ResendOtpResponse
import com.example.anggarin.data.response.VerifikasiOtpRequest
import com.example.anggarin.data.response.VerifikasiOtpResponse
import kotlinx.coroutines.launch

class VerifikasiViewModel : ViewModel() {
    private val _verificationResult = MutableLiveData<Result.Result<VerifikasiOtpResponse>>()
    val verificationResult: LiveData<Result.Result<VerifikasiOtpResponse>> = _verificationResult

    private val _resendOtpResult = MutableLiveData<Result.Result<ResendOtpResponse>>()
    val resendOtpResult: LiveData<Result.Result<ResendOtpResponse>> = _resendOtpResult

    fun verifyOtp(context: Context, email: String, otp: String) {

        if (otp.isEmpty()) {
            _verificationResult.postValue(Result.Result.Error("OTP tidak boleh kosong"))
            return
        }
        viewModelScope.launch {
            try {
                // Call the API and handle the response
                val response = ApiConfig.getApiService(context).verificationUser(
                    verifikasiOtpRequest = VerifikasiOtpRequest(
                        email = email,
                        otp = otp
                    )
                )
                Log.i(TAG, "isSuccessful ${response.isSuccessful}")
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.body()}")
                    val verifikasiOtpResponse = response.body()
                    _verificationResult.postValue(Result.Result.Success(verifikasiOtpResponse!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    _resendOtpResult.postValue(Result.Result.Error("verifikasi OTP gagal: $errorBody"))
                }
            } catch (e: Exception) {
                _resendOtpResult.postValue(Result.Result.Error("Error: ${e.localizedMessage}"))
            }
        }
    }

    fun resendOtp(context: Context, email: String) {
        viewModelScope.launch {
            try {
                // Call the API and handle the response
                val response = ApiConfig.getApiService(context)
                    .resendOtp(resendOtpRequest = ResendOtpRequest(email = email))
                Log.i(TAG, "isSuccessful ${response.isSuccessful}")
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.body()}")
                    val resendOtpResponse = response.body()
                    _resendOtpResult.postValue(Result.Result.Success(resendOtpResponse!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    _resendOtpResult.postValue(Result.Result.Error("verifikasi OTP gagal: $errorBody"))
                }
            } catch (e: Exception) {
                _resendOtpResult.postValue(Result.Result.Error("Error: ${e.localizedMessage}"))
            }
        }
    }

    companion object {
        private const val TAG = "VerifikasiViewModel"
    }

}