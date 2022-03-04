package by.romanovich.materialdesign.view.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityApiBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ApiActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //в байнинге сидит вью пейджер и адаптеру присваиваем наш адаптер и на вход передаем сupportFragmentManager
        binding.viewPager.adapter = ViewPager2Adapter(this)  //было с 1 адаптером (supportFragmentManager) стало зис // TODO передать child
        //таб лайаут впитал заголовки
       // было binding.tabLayout.setupWithViewPager(binding.viewPager)
        //стало TabLayoutMediator связывает вью пейджер с адаптером и нужен коллбэк
        val tabTitles = arrayOf("Earth", "Mars", "System")
        TabLayoutMediator(binding.tabLayout,binding.viewPager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabTitles[position]
            }
        }).attach()


        //добавили иконки табллайауту
        //binding.tabLayout.getTabAt(EARTH)?.setIcon(R.drawable.ic_earth)
        //binding.tabLayout.getTabAt(MARS)?.setIcon(R.drawable.ic_mars)
        //binding.tabLayout.getTabAt(SYSTEM)?.setIcon(R.drawable.ic_system)

        //добавляем кастомные вьюшки
        binding.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_earth,null)
        binding.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_mars,null)
        binding.tabLayout.getTabAt(SYSTEM)?.customView = layoutInflater.inflate(R.layout.activity_api_tablayout_item_system,null)
    }
}