package uz.devapp.elonuz.screen.main.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.elonuz.data.model.AdsModel
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.data.repository.UserRepository
import uz.devapp.elonuz.data.repository.sealed.DataResult

class AdsViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _adsData = MutableLiveData<List<AdsModel>>()
    var adsData: LiveData<List<AdsModel>> = _adsData

    fun getAds(filter: AdsFilter) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.getAds(filter)) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    _adsData.value = (r.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}