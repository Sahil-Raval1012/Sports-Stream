package com.example.sportsistream.sports
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sportsistream.R
import com.example.sportsistream.databinding.FragmentSportsDetailBinding
class SportsDetailFragment : Fragment() {
    private var _binding: FragmentSportsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var item: NewsItem
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSportsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item = arguments?.getParcelable("news_item")!!

        Glide.with(this).load(item.imageUrl).centerCrop().into(binding.ivHero)

        binding.tvTitle.text = item.title
        binding.tvCategory.text = item.category
        binding.tvMeta.text = "${item.date} · ${item.source}"
        binding.tvDescription.text = item.description
        binding.tvFullContent.text = item.fullContent

        updateBookmarkButton()

        binding.btnBack.setOnClickListener {
            (parentFragment as SportsHostFragment).onBack()
        }
        binding.btnBookmarkHero.setOnClickListener {
            BookmarkManager.toggle(requireContext(), item)
            updateBookmarkButton()
        }
        binding.btnBookmark.setOnClickListener {
            BookmarkManager.toggle(requireContext(), item)
            updateBookmarkButton()
        }
        val related = NewsData.newsList
            .filter { it.id != item.id && it.category == item.category }
            .take(4)
        if (related.isEmpty()) {
            binding.tvRelatedTitle.visibility = View.GONE
            binding.rvRelated.visibility = View.GONE
        } else {
            binding.rvRelated.apply {
                layoutManager = LinearLayoutManager(requireContext())
                isNestedScrollingEnabled = false
                adapter = NewsAdapter(
                    related,
                    onClick = { relatedItem ->
                        val fragment = SportsDetailFragment().apply {
                            arguments = Bundle().apply { putParcelable("news_item", relatedItem) }
                        }
                        (parentFragment as SportsHostFragment).navigateTo(fragment)
                    },
                    onBookmark = { relatedItem ->
                        BookmarkManager.toggle(requireContext(), relatedItem)
                    }
                )
            }
        }
    }
    private fun updateBookmarkButton() {
        val bookmarked = BookmarkManager.isBookmarked(requireContext(), item.id)
        val label = if (bookmarked) "Bookmarked" else "Bookmark Story"
        val iconRes = if (bookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
        val bgColor = if (bookmarked) R.color.bookmarkActive else R.color.colorPrimary
        val textColor = if (bookmarked) R.color.colorPrimary else R.color.white

        binding.btnBookmark.text = label
        binding.btnBookmark.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgColor))
        binding.btnBookmark.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        binding.btnBookmarkHero.setImageResource(iconRes)
        binding.btnBookmarkHero.setColorFilter(
            ContextCompat.getColor(requireContext(), if (bookmarked) R.color.colorPrimary else R.color.gray)
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
