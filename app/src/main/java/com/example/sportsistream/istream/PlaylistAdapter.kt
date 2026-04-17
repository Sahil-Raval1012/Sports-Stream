package com.example.sportsistream.istream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsistream.databinding.ItemPlaylistBinding
import com.example.sportsistream.istream.db.PlaylistEntity

class PlaylistAdapter(
    private val onPlay: (PlaylistEntity) -> Unit,
    private val onDelete: (PlaylistEntity) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.VH>() {

    private var items: List<PlaylistEntity> = emptyList()

    inner class VH(val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    fun submitList(newItems: List<PlaylistEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvNumber.text = "${position + 1}"
            tvUrl.text = item.url
            tvDate.text = item.addedAt
            btnPlay.setOnClickListener { onPlay(item) }
            btnDelete.setOnClickListener { onDelete(item) }
            root.setOnClickListener { onPlay(item) }
        }
    }
}
