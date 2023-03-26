package uz.devapp.elonuz.screen.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.devapp.elonuz.data.model.AdsModel
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.databinding.FragmentMainBinding
import uz.devapp.elonuz.utils.showMessage
import uz.devapp.elonuz.view.AdsAdapter
import uz.devapp.elonuz.view.HorizontalCategoryAdapter

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.errorLiveData.observe(this) {
            requireActivity().showMessage(it)
        }

        viewModel.progressLiveData.observe(this) {
            binding.swipe.isRefreshing = it
        }

        viewModel.categoryData.observe(this) {
            binding.recyclerCategories.adapter = HorizontalCategoryAdapter(it.filter { it.parent_id == 0 }, it)
        }

        viewModel.adsData.observe(this) {
            binding.recyclerAds.adapter = AdsAdapter(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.recyclerCategories.layoutManager =
            LinearLayoutManager(
                requireActivity(), LinearLayoutManager.HORIZONTAL, false
            )

        binding.recyclerAds.layoutManager = LinearLayoutManager(requireActivity())

        loadData()
    }

    fun loadData() {
        viewModel.getCategories()
        viewModel.getAds(AdsFilter())
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {

            }
    }
}