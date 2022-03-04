package by.romanovich.materialdesign.view.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import by.romanovich.materialdesign.view.home.HomeFragment
import by.romanovich.materialdesign.view.main.asteroid.AsteroidFragment
import by.romanovich.materialdesign.view.settings.SettingsFragment


//передаем фрагмент менеджер
class MainViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    //массив фррагментов
    private val fragments = arrayOf(HomeFragment.newInstance(), AsteroidFragment.newInstance(), SettingsFragment.newInstance())

    //возращаем размер массива
    override fun getCount(): Int {
        return fragments.size
    }

    //возрощаем фрагмент по позиции
    override fun getItem(position: Int) = fragments[position]



}