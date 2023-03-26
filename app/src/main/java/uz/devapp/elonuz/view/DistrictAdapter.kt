package uz.devapp.elonuz.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import org.greenrobot.eventbus.EventBus
import uz.devapp.elonuz.data.model.*
import uz.devapp.elonuz.databinding.AdsItemLayoutBinding
import uz.devapp.elonuz.databinding.CategoryItemLayoutBinding
import uz.devapp.elonuz.databinding.DistrictItemLayoutBinding
import uz.devapp.elonuz.databinding.RegionItemLayoutBinding
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.loadImage

class DistrictAdapter(val items: List<DistrictModel>, val callback: RegionAdapterCallback): RecyclerView.Adapter<DistrictAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: DistrictItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(DistrictItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            callback.onSelectDistrict(item)
        }
        holder.binding.tvName.text = item.name_uz
    }
}