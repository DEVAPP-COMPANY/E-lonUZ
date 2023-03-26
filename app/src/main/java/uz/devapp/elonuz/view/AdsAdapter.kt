package uz.devapp.elonuz.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import uz.devapp.elonuz.data.model.AdsModel
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.databinding.AdsItemLayoutBinding
import uz.devapp.elonuz.databinding.CategoryItemLayoutBinding
import uz.devapp.elonuz.utils.loadImage

class AdsAdapter(val items: List<AdsModel>): RecyclerView.Adapter<AdsAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: AdsItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(AdsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvAddress.text = item.address
        holder.binding.tvPrice.text = "${item.price} SUM"
        holder.binding.imgAds.loadImage(item.main_image)

    }
}