package uz.devapp.elonuz.screen.main.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.databinding.ActivityCategoriesBinding
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.showMessage
import uz.devapp.elonuz.view.AdsAdapter
import uz.devapp.elonuz.view.GridCategoryAdapter

class CategoriesActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoriesBinding
    lateinit var viewModel: CategoriesViewModel

    lateinit var parentCategory: CategoryModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parentCategory = intent.getSerializableExtra(Constants.EXTRA_DATA) as CategoryModel

        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]

        viewModel.errorLiveData.observe(this, Observer {
            showMessage(it)
        })

        viewModel.progressLiveData.observe(this) {
            binding.swipe.isRefreshing = it
        }

        viewModel.categoryData.observe(this) {
            binding.recyclerCategories.adapter =
                GridCategoryAdapter(it.filter { it.parent_id == parentCategory.id }, it)
        }

        viewModel.adsData.observe(this) {
            binding.recyclerAds.adapter = AdsAdapter(it)
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.recyclerCategories.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerAds.layoutManager = LinearLayoutManager(this)


        loadData()


    }

    fun loadData() {
        viewModel.getCategories(parentCategory.id)
        viewModel.getAds(AdsFilter(category_id = parentCategory.id))
    }
}