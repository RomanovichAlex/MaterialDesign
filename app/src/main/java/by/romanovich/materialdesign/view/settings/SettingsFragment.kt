package by.romanovich.materialdesign.view.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentSettingsBinding
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout

class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initChip()
        clickChip()

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
            "chipDay"->{
                binding.chipDark.isChecked = true
            }
            "chipMars"->{
                binding.chipMens.isChecked = true
            }
            "chipMoon"->{
                binding.chipGirls.isChecked = true
            }
        }
    }

    private fun clickChip() {
        binding.chipGroup1.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.chipDark->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipDay").apply()
                    requireActivity().setTheme(R.style.MyDarkTheme)
                    recreate(requireActivity())
                }
                R.id.chipMens->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMars").apply()
                    requireActivity().setTheme(R.style.MensTheme)
                    recreate(requireActivity())
                }
                R.id.chipGirls->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMoon").apply()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}