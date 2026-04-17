package com.example.sportsistream.sports
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportsistream.databinding.FragmentSportsHostBinding
class SportsHostFragment : Fragment() {
    private var _binding: FragmentSportsHostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSportsHostBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(binding.sportsContainer.id) == null) {
            showHome()
        }
    }
    private fun showHome() {
        childFragmentManager.beginTransaction()
            .replace(binding.sportsContainer.id, SportsHomeFragment())
            .commit()
    }
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        childFragmentManager.beginTransaction()
            .replace(binding.sportsContainer.id, fragment)
            .apply { if (addToBackStack) addToBackStack(null) }
            .commit()
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
