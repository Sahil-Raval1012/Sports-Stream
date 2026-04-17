package com.example.sportsistream.istream
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsistream.databinding.FragmentPlaylistBinding
import com.example.sportsistream.istream.db.AppDatabase
import com.example.sportsistream.istream.db.PlaylistEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaylistAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullName = SessionManager.getFullName(requireContext()) ?: ""
        binding.tvSubtitle.text = fullName
        binding.btnBack.setOnClickListener {
            (parentFragment as IStreamHostFragment).onBack()
        }
        binding.btnLogout.setOnClickListener {
            SessionManager.clear(requireContext())
            (parentFragment as IStreamHostFragment).navigateTo(
                LoginFragment(), addToBackStack = false, clearStack = true
            )
        }
        adapter = PlaylistAdapter(
            onPlay = { item ->
                val fragment = IStreamHomeFragment().apply {
                    arguments = Bundle().apply { putString("url", item.url) }
                }
                (parentFragment as IStreamHostFragment).navigateTo(
                    fragment, addToBackStack = false, clearStack = true
                )
            },
            onDelete = { item -> deleteItem(item) }
        )
        binding.rvPlaylist.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlaylist.adapter = adapter

        loadPlaylist()
    }
    private fun loadPlaylist() {
        val userId = SessionManager.getUserId(requireContext()) ?: return
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                AppDatabase.getInstance(requireContext()).playlistDao().getByUserId(userId)
            }
            if (items.isEmpty()) {
                binding.rvPlaylist.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvPlaylist.visibility = View.VISIBLE
                adapter.submitList(items)
            }
        }
    }
    private fun deleteItem(item: PlaylistEntity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                AppDatabase.getInstance(requireContext()).playlistDao().delete(item)
            }
            loadPlaylist()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
