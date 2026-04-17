package com.example.sportsistream.istream

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sportsistream.databinding.FragmentIStreamHomeBinding
import com.example.sportsistream.istream.db.AppDatabase
import com.example.sportsistream.istream.db.PlaylistEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IStreamHomeFragment : Fragment() {

    private var _binding: FragmentIStreamHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIStreamHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()

        val fullName = SessionManager.getFullName(requireContext()) ?: ""
        binding.tvGreeting.text = "Hi, ${fullName.split(" ").firstOrNull() ?: fullName}"

        // Pre-fill URL if passed from playlist
        arguments?.getString("url")?.let { url ->
            binding.etUrl.setText(url)
            playUrl(url)
        }

        binding.btnPlay.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (url.isEmpty()) {
                showError("Please enter a YouTube URL")
                return@setOnClickListener
            }
            val id = extractYoutubeId(url)
            if (id == null) {
                showError("Invalid YouTube URL — please enter a valid link")
            } else {
                hideMessages()
                playUrl(url)
            }
        }

        binding.btnAddPlaylist.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (url.isEmpty()) { showError("Please enter a YouTube URL first"); return@setOnClickListener }
            if (extractYoutubeId(url) == null) { showError("Invalid YouTube URL"); return@setOnClickListener }
            addToPlaylist(url)
        }

        binding.btnMyPlaylist.setOnClickListener {
            (parentFragment as IStreamHostFragment).navigateTo(PlaylistFragment())
        }

        binding.btnLogout.setOnClickListener {
            SessionManager.clear(requireContext())
            (parentFragment as IStreamHostFragment).navigateTo(
                LoginFragment(), addToBackStack = false, clearStack = true
            )
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                mediaPlaybackRequiresUserGesture = false
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }
    }

    private fun playUrl(url: String) {
        val id = extractYoutubeId(url) ?: return
        val embedHtml = """
            <!DOCTYPE html><html>
            <head><meta name="viewport" content="width=device-width,initial-scale=1">
            <style>body{margin:0;background:#000}iframe{width:100%;height:100vh;border:0}</style>
            </head>
            <body>
            <iframe src="https://www.youtube.com/embed/$id?autoplay=1&playsinline=1"
                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen></iframe>
            </body></html>
        """.trimIndent()
        binding.webView.loadDataWithBaseURL("https://www.youtube.com", embedHtml, "text/html", "utf-8", null)
        binding.webView.visibility = View.VISIBLE
        binding.ivPlaceholder.visibility = View.GONE
    }

    private fun addToPlaylist(url: String) {
        val userId = SessionManager.getUserId(requireContext()) ?: return
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val exists = withContext(Dispatchers.IO) {
                db.playlistDao().countByUserAndUrl(userId, url) > 0
            }
            if (exists) {
                showError("Video already in your playlist")
                return@launch
            }
            val now = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date())
            withContext(Dispatchers.IO) {
                db.playlistDao().insert(PlaylistEntity(userId = userId, url = url, addedAt = now))
            }
            showSuccess("Added to playlist!")
        }
    }

    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
        binding.tvSuccess.visibility = View.GONE
    }

    private fun showSuccess(msg: String) {
        binding.tvSuccess.text = msg
        binding.tvSuccess.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
    }

    private fun hideMessages() {
        binding.tvError.visibility = View.GONE
        binding.tvSuccess.visibility = View.GONE
    }

    private fun extractYoutubeId(url: String): String? {
        val regex = Regex(
            "(?:youtube\\.com/watch\\?v=|youtu\\.be/|youtube\\.com/embed/|youtube\\.com/shorts/)([a-zA-Z0-9_-]{11})"
        )
        return regex.find(url)?.groupValues?.getOrNull(1)
    }

    override fun onDestroyView() {
        binding.webView.destroy()
        super.onDestroyView()
        _binding = null
    }
}
