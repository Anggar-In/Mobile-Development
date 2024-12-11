import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.RegisterRequest
import com.example.anggarin.data.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>()
    val registrationResult: LiveData<Result<RegisterResponse>> = _registrationResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    fun registerUser(context: Context, nama: String, email: String, password: String) {
        viewModelScope.launch {
            // Input validation
            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                _registrationResult.postValue(Result.Error("Semua field harus diisi"))
                return@launch
            }

            if (!isValidEmail(email)) {
                _registrationResult.postValue(Result.Error("Format email salah"))
                return@launch
            }

            if (password.length < 8) {
                _registrationResult.postValue(Result.Error("Kata sandi minimal 8 karakter"))
                return@launch
            }


            _isLoading.value = true
            _error.value = null
            try {
                // Call the API and handle the response
                val request = RegisterRequest(name = nama, email = email, password = password)
                val response = ApiConfig.getApiService(context).registerUser(registerRequest = request)
                Log.i(TAG, "isSuccessful ${response.isSuccessful}")
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.body()}")

                    val registerResponse = response.body()
                    pref.saveUserData(nama, email)
                    _registrationResult.postValue(Result.Success(registerResponse!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterResponse", "Error body: $errorBody")
                    _registrationResult.postValue(Result.Error("Registrasi gagal: $errorBody"))
                }
            } catch (e: Exception) {
                // Handle any exceptions (e.g., network issues)
                _registrationResult.postValue( Result.Error("Error: ${e.localizedMessage}"))
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}
