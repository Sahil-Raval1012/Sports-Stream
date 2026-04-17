package com.example.sportsistream.istream
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sportsistream.databinding.FragmentIStreamHostBinding
class IStreamHostFragment : Fragment() {
    private var _binding: FragmentIStreamHostBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIStreamHostBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(binding.istreamContainer.id) == null) {
            val start = if (SessionManager.isLoggedIn(requireContext())) {
                IStreamHomeFragment()
            } else {
                LoginFragment()
            }
            childFragmentManager.beginTransaction()
                .replace(binding.istreamContainer.id, start)
                .commit()
        }
    }
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true, clearStack: Boolean = false) {
        if (clearStack) {
            childFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        val tx = childFragmentManager.beginTransaction()
            .replace(binding.istreamContainer.id, fragment)
        if (!clearStack && addToBackStack) {
            tx.addToBackStack(null)
        }
        tx.commit()
    }
    fun onBack(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            true
        } else false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
