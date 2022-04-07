package by.romanovich.materialdesign.view.coordinator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentCoordinatorBinding
import by.romanovich.materialdesign.view.UX.ViewBindingFragment


class CoordinatorFragment : ViewBindingFragment<FragmentCoordinatorBinding>(FragmentCoordinatorBinding::inflate) {


    private val duration = 1000L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //создали бехайвор
        val buttonBehavior = ButtonBehavior(requireContext())
        //получили элемент на который вешаем
        val button = view.findViewById<Button>(R.id.fab)
        //получили  параметры контейнера(кординатор лайаут) которые вешаем на кнопку
        (button.layoutParams as CoordinatorLayout.LayoutParams).behavior = buttonBehavior

    }

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()

    }
}