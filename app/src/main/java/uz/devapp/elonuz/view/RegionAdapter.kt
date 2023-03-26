package uz.devapp.elonuz.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import uz.devapp.elonuz.data.model.AdsModel
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.DistrictModel
import uz.devapp.elonuz.data.model.RegionModel
import uz.devapp.elonuz.databinding.AdsItemLayoutBinding
import uz.devapp.elonuz.databinding.CategoryItemLayoutBinding
import uz.devapp.elonuz.databinding.RegionItemLayoutBinding
import uz.devapp.elonuz.utils.loadImage

interface RegionAdapterCallback{
    fun onSelectDistrict(item: DistrictModel)
}

class RegionAdapter(val items: List<RegionModel>, val callback: RegionAdapterCallback): RecyclerView.Adapter<RegionAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: RegionItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(RegionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.lyRegion.setOnClickListener {
            items.forEach { it.active = false }
            item.active = true
            notifyDataSetChanged()
        }

        holder.binding.tvName.text = item.name_uz
        holder.binding.recyclerDistricts.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.recyclerDistricts.adapter = DistrictAdapter(item.districts, callback)
        holder.binding.recyclerDistricts.visibility = if (item.active) View.VISIBLE else View.GONE

    }
}