package uz.devapp.elonuz.utils

import com.orhanobut.hawk.Hawk
import uz.devapp.elonuz.MyApp
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.RegionModel

object PrefUtils {
    const val PREF_TOKEN = "token"
    const val PREF_REGIONS = "regions"
    const val PREF_CATEGORIES = "categories"

    fun init() {
        Hawk.init(MyApp.app).build()
    }

    fun setToken(value: String?) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setCategories(value: List<CategoryModel>) {
        Hawk.put(PREF_CATEGORIES, value)
    }

    fun getCategories(): List<CategoryModel> {
        return Hawk.get(PREF_CATEGORIES, listOf())
    }

    fun setRegions(value: List<RegionModel>) {
        Hawk.put(PREF_REGIONS, value)
    }

    fun getRegions(): List<RegionModel> {
        return Hawk.get(PREF_REGIONS, listOf())
    }

    fun clear() {
        Hawk.deleteAll()
    }
}