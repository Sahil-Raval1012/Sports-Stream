package com.example.sportsistream
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sportsistream.databinding.ActivityMainBinding
import com.example.sportsistream.istream.IStreamHostFragment
import com.example.sportsistream.sports.SportsHostFragment
import com.google.android.material.tabs.TabLayoutMediator
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> SportsHostFragment()
                else -> IStreamHostFragment()
            }
        }
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = when (pos) {
                0 -> "Sports News"
                else -> "iStream"
            }
        }.attach()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val pos = binding.viewPager.currentItem
        val tag = "f$pos"
        val host = supportFragmentManager.findFragmentByTag(tag)
        val handled = when (host) {
            is SportsHostFragment -> host.onBack()
            is IStreamHostFragment -> host.onBack()
            else -> false
        }
        if (!handled) super.onBackPressed()
    }
}
