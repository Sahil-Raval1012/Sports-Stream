package com.example.sportsistream.sports
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsistream.databinding.ItemFeaturedMatchBinding
class FeaturedMatchAdapter(
    private val items: List<NewsItem>,
    private val onClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<FeaturedMatchAdapter.VH>() {
    inner class VH(val binding: ItemFeaturedMatchBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemFeaturedMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.title
            tvDate.text = item.date
            tvCategory.text = item.category
            Glide.with(ivImage).load(item.imageUrl).centerCrop().into(ivImage)
            root.setOnClickListener { onClick(item) }
        }
    }
}
