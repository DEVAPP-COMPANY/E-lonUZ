package uz.devapp.elonuz.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.databinding.CategoryItemLayoutBinding
import uz.devapp.elonuz.databinding.GridCategoryItemLayoutBinding
import uz.devapp.elonuz.screen.main.ads.AdsActivity
import uz.devapp.elonuz.screen.main.categories.CategoriesActivity
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.loadImage

class GridCategoryAdapter(val items: List<CategoryModel>, val fullItems: List<CategoryModel>): RecyclerView.Adapter<GridCategoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: GridCategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(GridCategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, if(fullItems.filter { it.parent_id == item.id }.isEmpty()) AdsActivity::class.java else CategoriesActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }

        holder.binding.tvCategoryTitle.text = item.title
        holder.binding.imgCategory.loadImage(item.image)


    }
}