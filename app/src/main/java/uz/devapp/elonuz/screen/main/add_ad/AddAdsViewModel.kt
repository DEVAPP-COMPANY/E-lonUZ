package uz.devapp.elonuz.screen.main.add_ad

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

class AddAdsViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _addData = MutableLiveData<Boolean>()
    var addData: LiveData<Boolean> = _addData

    fun addAds(mainImage: String,
               title: String,
               comment: String,
               address: String,
               phone: String,
               price: Double,
               categoryId: Int,
               districtId: Int,) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.addAds(mainImage, title, comment, address, phone, price, categoryId, districtId)) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    _addData.value = true
                }
            }
            _progressLiveData.value = false
        }
    }
}