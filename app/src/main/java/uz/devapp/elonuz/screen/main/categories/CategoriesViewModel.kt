package uz.devapp.elonuz.screen.main.categories

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

class CategoriesViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _categoriesData = MutableLiveData<List<CategoryModel>>()
    var categoryData: LiveData<List<CategoryModel>> = _categoriesData

    fun getCategories(parentId: Int) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val r = repository.getCategories()) {
                is DataResult.Error -> {
                    _errorLiveData.value = r.message
                }
                is DataResult.Success -> {
                    _categoriesData.value = (r.result)
                }
            }

            _progressLiveData.value = false
        }
    }

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