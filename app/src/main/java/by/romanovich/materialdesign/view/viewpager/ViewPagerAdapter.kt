package by.romanovich.materialdesign.view.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


const val EARTH = 0
const val MARS= 1
const val SYSTEM= 2


//передаем фрагмент менеджер
class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    //массив фррагментов
    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    //возращаем размер массива
    override fun getCount(): Int {
        return fragments.size
    }

    //возрощаем фрагмент по позиции
    override fun getItem(position: Int) = fragments[position]

    //свайпы по таб
    override fun getPageTitle(position: Int): CharSequence? {
        //будут только картинки
        return null
        //будут картинки и описание
        /*return when (position) {
            EARTH -> "Earth"
            MARS -> "Mars"
            SYSTEM -> "System"
            else ->"null"
        }*/
    }

}