package com.example.sportsistream.istream
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sportsistream.databinding.FragmentSignupBinding
import com.example.sportsistream.istream.db.AppDatabase
import com.example.sportsistream.istream.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            (parentFragment as IStreamHostFragment).onBack()
        }
        binding.btnCreate.setOnClickListener { attemptSignUp() }
        binding.tvSignIn.setOnClickListener {
            (parentFragment as IStreamHostFragment).onBack()
        }
    }
    private fun attemptSignUp() {
        val fullName = binding.etFullName.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirm = binding.etConfirmPassword.text.toString()
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showError("Please fill in all fields"); return
        }
        if (username.length < 3) {
            showError("Username must be at least 3 characters"); return
        }
        if (password.length < 6) {
            showError("Password must be at least 6 characters"); return
        }
        if (password != confirm) {
            showError("Passwords do not match"); return
        }
        binding.tvError.visibility = View.GONE
        binding.btnCreate.isEnabled = false
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val exists = withContext(Dispatchers.IO) { db.userDao().usernameExists(username) } > 0
            if (exists) {
                showError("Username already taken")
                binding.btnCreate.isEnabled = true
                return@launch
            }
            withContext(Dispatchers.IO) {
                db.userDao().insert(UserEntity(fullName = fullName, username = username, password = password))
            }
            withContext(Dispatchers.Main) {
                (parentFragment as IStreamHostFragment).onBack()
            }
        }
    }
    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
        binding.btnCreate.isEnabled = true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
