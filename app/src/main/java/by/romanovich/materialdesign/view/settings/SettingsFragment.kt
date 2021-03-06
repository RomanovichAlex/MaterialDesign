package by.romanovich.materialdesign.view.settings

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentSettingsBinding
import by.romanovich.materialdesign.view.UX.ViewBindingFragment
import by.romanovich.materialdesign.view.workToTextFragment.WorkToTextFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout

class SettingsFragment : ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initChip()
        clickChip()

        binding.workToTextButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,WorkToTextFragment.newInstance()).addToBackStack("").commit()
        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            //у чип гроуп находим по позиции, которую выбрали
            binding.chipGroup.findViewById<Chip>(checkedId)?.let{ it->
                //выводим тост по нажатию дефолта
                Toast.makeText(requireContext(), "${it.text} ${checkedId}", Toast.LENGTH_SHORT).show()
            }
        }
        //по нажатию на крестик в чипе
        binding.chipEntry.setOnCloseIconClickListener {
            Toast.makeText(requireContext(), "chipEntry close", Toast.LENGTH_SHORT).show()
        }





//верни мне вкладку по индексу, задаем текст и иконки
    binding.tabs.getTabAt(0)!!.text = "Сегодня"
    binding.tabs.getTabAt(0)!!.icon = resources.getDrawable(R.drawable.ic_search)
    binding.tabs.getTabAt(1)!!.text = "Вчера"
    binding.tabs.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.ic_favourite_menu)
    binding.tabs.getTabAt(2)!!.text = "Позавчера"
    binding.tabs.getTabAt(2)!!.icon = resources.getDrawable(R.drawable.ic_plus_fab)
    binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

//таб по которому мы кликнули и узнали где находимся
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when(tab!!.position){}
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}



    private fun initChip() {
        when(requireActivity().getPreferences(Activity.MODE_PRIVATE).getString("settingTheme","")){
            "chipDark"->{
                binding.chipDark.isChecked = true
            }
            "chipMens"->{
                binding.chipMens.isChecked = true
            }
            "chipGirls"->{
                binding.chipGirls.isChecked = true
            }
        }
    }

    private fun clickChip() {
        binding.chipGroup1.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.chipDark->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipDark").apply()
                    requireActivity().setTheme(R.style.MyDarkTheme)
                    recreate(requireActivity())
                }
                R.id.chipMens->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMens").apply()
                    requireActivity().setTheme(R.style.MensTheme)
                    recreate(requireActivity())
                }
                R.id.chipGirls->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipGirls").apply()
                    requireActivity().setTheme(R.style.GirlsTheme)
                    recreate(requireActivity())
                }
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}