package uz.devapp.elonuz.screen.main.add_ad.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.EventModel
import uz.devapp.elonuz.databinding.ActivitySelectCategoryBinding
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.PrefUtils
import uz.devapp.elonuz.view.SelectCategoryAdapter
import uz.devapp.elonuz.view.SelectCategoryAdapterCallback

class SelectCategoryActivity : AppCompatActivity(), SelectCategoryAdapterCallback {
    lateinit var binding: ActivitySelectCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = SelectCategoryAdapter(PrefUtils.getCategories(), this)
    }

    override fun onSelectCategory(item: CategoryModel) {
        EventBus.getDefault().post(EventModel(Constants.EVENT_SELECT_CATEGORY, item))
        finish()
    }
}