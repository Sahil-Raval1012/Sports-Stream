package com.example.sportsistream.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsistream.databinding.FragmentSportsBookmarksBinding

class SportsBookmarksFragment : Fragment() {

    private var _binding: FragmentSportsBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSportsBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            (parentFragment as SportsHostFragment).onBack()
        }

        loadBookmarks()
    }

    private fun loadBookmarks() {
        val bookmarks = BookmarkManager.getAll(requireContext())
        if (bookmarks.isEmpty()) {
            binding.rvBookmarks.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvBookmarks.visibility = View.VISIBLE
            binding.rvBookmarks.layoutManager = LinearLayoutManager(requireContext())
            binding.rvBookmarks.adapter = NewsAdapter(
                bookmarks,
                onClick = { item ->
                    val fragment = SportsDetailFragment().apply {
                        arguments = Bundle().apply { putParcelable("news_item", item) }
                    }
                    (parentFragment as SportsHostFragment).navigateTo(fragment)
                },
                onBookmark = { item ->
                    BookmarkManager.toggle(requireContext(), item)
                    loadBookmarks()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
