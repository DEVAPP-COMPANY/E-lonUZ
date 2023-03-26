package uz.devapp.elonuz.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.databinding.SelectCategoryItemLayoutBinding

interface SelectCategoryAdapterCallback {
    fun onSelectCategory(item: CategoryModel)
}

class SelectCategoryAdapter(
    val items: List<CategoryModel>,
    val callback: SelectCategoryAdapterCallback
) : RecyclerView.Adapter<SelectCategoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: SelectCategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            SelectCategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            callback.onSelectCategory(item)
        }
        holder.binding.tvName.text = item.title

    }
}