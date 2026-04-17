package com.example.sportsistream.sports

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsistream.R
import com.example.sportsistream.databinding.FragmentSportsHomeBinding
import com.google.android.material.chip.Chip

class SportsHomeFragment : Fragment() {

    private var _binding: FragmentSportsHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var featuredAdapter: FeaturedMatchAdapter
    private lateinit var newsAdapter: NewsAdapter
    private var selectedCategory = "All"
    private var searchQuery = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSportsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFeaturedRecyclerView()
        setupNewsRecyclerView()
        setupCategoryChips()
        setupSearch()

        binding.btnBookmarks.setOnClickListener {
            (parentFragment as SportsHostFragment).navigateTo(SportsBookmarksFragment())
        }
    }

    private fun setupFeaturedRecyclerView() {
        featuredAdapter = FeaturedMatchAdapter(NewsData.featuredList) { item ->
            openDetail(item)
        }
        binding.rvFeatured.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = featuredAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupNewsRecyclerView() {
        newsAdapter = NewsAdapter(
            NewsData.newsList,
            onClick = { item -> openDetail(item) },
            onBookmark = { item ->
                BookmarkManager.toggle(requireContext(), item)
            }
        )
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupCategoryChips() {
        binding.chipGroup.removeAllViews()
        NewsData.categories.forEach { cat ->
            val chip = Chip(requireContext()).apply {
                text = cat
                isCheckable = true
                isChecked = cat == "All"
                checkedIconTint = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
            }
            chip.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    selectedCategory = cat
                    filterNews()
                }
            }
            binding.chipGroup.addView(chip)
        }
    }
    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchQuery = s?.toString() ?: ""
                filterNews()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun filterNews() {
        val filtered = NewsData.newsList.filter { item ->
            val matchCat = selectedCategory == "All" || item.category == selectedCategory
            val matchSearch = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.category.contains(searchQuery, ignoreCase = true)
            matchCat && matchSearch
        }
        newsAdapter.updateList(filtered)
        binding.tvNoResults.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun openDetail(item: NewsItem) {
        val fragment = SportsDetailFragment().apply {
            arguments = Bundle().apply { putParcelable("news_item", item) }
        }
        (parentFragment as SportsHostFragment).navigateTo(fragment)
    }
    override fun onResume() {
        super.onResume()
        newsAdapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
