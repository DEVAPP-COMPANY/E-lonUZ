package uz.devapp.elonuz.screen.main.add_ad.region

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import uz.devapp.elonuz.R
import uz.devapp.elonuz.data.model.DistrictModel
import uz.devapp.elonuz.data.model.EventModel
import uz.devapp.elonuz.databinding.ActivitySelectRegionBinding
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.PrefUtils
import uz.devapp.elonuz.view.RegionAdapter
import uz.devapp.elonuz.view.RegionAdapterCallback

class SelectRegionActivity : AppCompatActivity(), RegionAdapterCallback {
    lateinit var binding: ActivitySelectRegionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = RegionAdapter(PrefUtils.getRegions(), this)
    }

    override fun onSelectDistrict(item: DistrictModel) {
        EventBus.getDefault().post(EventModel(Constants.EVENT_SELECT_REGION, item))
        finish()
    }
}