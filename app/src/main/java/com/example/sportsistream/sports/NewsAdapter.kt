package com.example.sportsistream.sports
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsistream.R
import com.example.sportsistream.databinding.ItemNewsBinding
class NewsAdapter(
    private var items: List<NewsItem>,
    private val onClick: (NewsItem) -> Unit,
    private val onBookmark: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.VH>() {
    inner class VH(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val ctx: Context = holder.binding.root.context
        with(holder.binding) {
            tvTitle.text = item.title
            tvCategory.text = item.category
            tvDate.text = "${item.date} · ${item.source}"
            Glide.with(ivImage).load(item.imageUrl).centerCrop().into(ivImage)

            val bookmarked = BookmarkManager.isBookmarked(ctx, item.id)
            ivBookmark.setImageResource(
                if (bookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            )
            ivBookmark.setColorFilter(
                ContextCompat.getColor(ctx, if (bookmarked) R.color.colorPrimary else R.color.gray)
            )

            ivBookmark.setOnClickListener {
                onBookmark(item)
                notifyItemChanged(position)
            }
            root.setOnClickListener { onClick(item) }
        }
    }
    fun updateList(newItems: List<NewsItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
