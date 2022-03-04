package by.romanovich.materialdesign.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.BottomNavigationLayoutBinding
import by.romanovich.materialdesign.view.bottomnavigation.ApiBottomActivity
import by.romanovich.materialdesign.view.viewpager.ApiActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class
BottomNavigationDrawerFragment: BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menu->
            when(menu.itemId){
                R.id.navigation_one -> {
                    startActivity(Intent(requireContext(), ApiActivity::class.java))
                    //Toast.makeText(requireContext(), "navigation_one", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    startActivity(Intent(requireContext(), ApiBottomActivity::class.java))
                    //Toast.makeText(requireContext(), "navigation_two", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}