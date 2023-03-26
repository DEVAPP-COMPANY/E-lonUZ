package uz.devapp.elonuz.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.elonuz.data.model.AdsModel
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.data.model.request.LoginRequest
import uz.devapp.elonuz.data.model.request.RegistrationRequest
import uz.devapp.elonuz.data.model.response.AuthResponse
import uz.devapp.elonuz.data.repository.UserRepository
import uz.devapp.elonuz.data.repository.sealed.DataResult
import uz.devapp.elonuz.utils.PrefUtils

class AuthViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _authData = MutableLiveData<AuthResponse>()
    var authData: LiveData<AuthResponse> = _authData

    fun registration(request: RegistrationRequest) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.registration(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    PrefUtils.setToken(r.result!!.token)
                    _authData.value = r.result!!
                }
            }
            _progressLiveData.value = false
        }
    }

    fun login(request: LoginRequest) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.login(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    PrefUtils.setToken(r.result!!.token)
                    _authData.value = r.result!!
                }
            }
            _progressLiveData.value = false
        }
    }
}