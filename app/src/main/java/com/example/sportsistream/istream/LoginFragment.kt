package com.example.sportsistream.istream
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sportsistream.databinding.FragmentLoginBinding
import com.example.sportsistream.istream.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener { attemptLogin() }
        binding.tvSignUp.setOnClickListener {
            (parentFragment as IStreamHostFragment).navigateTo(SignUpFragment())
        }
    }
    private fun attemptLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            binding.tvError.text = "Please fill in all fields"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        binding.tvError.visibility = View.GONE
        binding.btnSignIn.isEnabled = false
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val user = withContext(Dispatchers.IO) {
                db.userDao().login(username, password)
            }
            if (user != null) {
                SessionManager.save(requireContext(), user.id, user.username, user.fullName)
                (parentFragment as IStreamHostFragment).navigateTo(
                    IStreamHomeFragment(), addToBackStack = false, clearStack = true
                )
            } else {
                binding.tvError.text = "Invalid username or password"
                binding.tvError.visibility = View.VISIBLE
                binding.btnSignIn.isEnabled = true
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
