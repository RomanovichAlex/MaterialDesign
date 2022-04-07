package by.romanovich.materialdesign.view.main
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentMainBinding
import by.romanovich.materialdesign.view.UX.ViewBindingFragment
import by.romanovich.materialdesign.view.viewpager.MainViewPagerAdapter


class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MainViewPagerAdapter(requireActivity().supportFragmentManager)
        initNavigation()
    }

        private fun initNavigation() {
            binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> binding.myBottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
                        1 -> binding.myBottomNavigationView.menu.findItem(R.id.navigation_asteroid).isChecked = true
                        2 -> binding.myBottomNavigationView.menu.findItem(R.id.navigation_photo_earth).isChecked = true
                        3 -> binding.myBottomNavigationView.menu.findItem(R.id.navigation_weather).isChecked = true
                        4 -> binding.myBottomNavigationView.menu.findItem(R.id.navigation_settings).isChecked = true
                    }
                }
                override fun onPageScrollStateChanged(state: Int) {}
            })
            binding.myBottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        binding.viewPager.currentItem = 0
                        true
                    }
                    R.id.navigation_asteroid -> {
                        binding.viewPager.currentItem = 1
                        true
                    }
                    R.id.navigation_photo_earth -> {
                        binding.viewPager.currentItem = 2
                        true
                    }
                    R.id.navigation_weather -> {
                        binding.viewPager.currentItem = 3
                        true
                    }

                    R.id.navigation_settings -> {
                        binding.viewPager.currentItem = 4
                        true
                    }
                    else -> true
                }

            }
        }


        companion object {
            @JvmStatic
            fun newInstance() = MainFragment()
        }
    }