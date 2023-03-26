package uz.devapp.elonuz.screen.main.home

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
import uz.devapp.elonuz.utils.PrefUtils

class MainViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _categoriesData = MutableLiveData<List<CategoryModel>>()
    var categoryData: LiveData<List<CategoryModel>> = _categoriesData

    private var _adsData = MutableLiveData<List<AdsModel>>()
    var adsData: LiveData<List<AdsModel>> = _adsData

    fun getCategories() {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.getCategories()) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    PrefUtils.setCategories(r.result)
                    _categoriesData.value = (r.result)
                }
            }

            _progressLiveData.value = false
            getRegions()
        }
    }

    fun getRegions() {
        viewModelScope.launch {
            when (val r = repository.getRegions()) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    PrefUtils.setRegions(r.result)
                }
            }
        }
    }

    fun getAds(filter: AdsFilter) {
        viewModelScope.launch {
            when (val r = repository.getAds(filter)) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    _adsData.value = (r.result)
                }
            }
        }
    }
}