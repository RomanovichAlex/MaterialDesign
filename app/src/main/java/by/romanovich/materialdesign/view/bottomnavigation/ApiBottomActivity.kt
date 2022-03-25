package by.romanovich.materialdesign.view.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.databinding.ActivityApiBottomBinding


class ApiBottomActivity : AppCompatActivity() {

    lateinit var binding: ActivityApiBottomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_view_earth->{
                    navigateTo(EarthFragment())
                    //supportFragmentManager.beginTransaction().replace(R.id.container,EarthFragment()).commit()
                    //true - кликабельная, фалсе не кликабелная
                    true
                }
                R.id.bottom_view_mars->{
                    navigateTo(MarsFragment())
                    true
                }
                R.id.bottom_view_system->{
                    navigateTo(SystemFragment())
                    true
                }
                R.id.bottom_view_system->{
                    navigateTo(SystemFragment())
                    true
                }
                else -> {true}
            }
        }
        //изначально будет открыта вкладка систем
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_system

        //вешаем бэйдж на марс, красная точка
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)
        //задаем номер макс 4 цифры
        badge.number = 10000
        //макс 2 цифры
        badge.maxCharacterCount = 2
        //меняем расположение на слева
        badge.badgeGravity = BadgeDrawable.TOP_START
        //удаление бэйджа
        //binding.bottomNavigationView.removeBadge(R.id.bottom_view_mars)
    }
    fun navigateTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }*/
    }
}