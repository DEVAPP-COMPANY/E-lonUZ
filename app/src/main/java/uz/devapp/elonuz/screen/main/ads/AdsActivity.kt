package uz.devapp.elonuz.screen.main.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.devapp.elonuz.R
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.databinding.ActivityAdsBinding
import uz.devapp.elonuz.databinding.ActivityCategoriesBinding
import uz.devapp.elonuz.screen.main.categories.CategoriesViewModel
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.showMessage
import uz.devapp.elonuz.view.AdsAdapter
import uz.devapp.elonuz.view.GridCategoryAdapter

class AdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdsBinding
    lateinit var viewModel: AdsViewModel
    lateinit var parentCategory: CategoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parentCategory = intent.getSerializableExtra(Constants.EXTRA_DATA) as CategoryModel

        viewModel = ViewModelProvider(this)[AdsViewModel::class.java]

        viewModel.errorLiveData.observe(this, Observer {
            showMessage(it)
        })

        viewModel.progressLiveData.observe(this) {
            binding.swipe.isRefreshing = it
        }

        viewModel.adsData.observe(this) {
            binding.recycler.adapter = AdsAdapter(it)
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)

        loadData()

    }

    fun loadData(){
        viewModel.getAds(AdsFilter(category_id = parentCategory.id))
    }

}