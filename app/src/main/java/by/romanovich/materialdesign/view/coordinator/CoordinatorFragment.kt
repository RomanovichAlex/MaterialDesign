package by.romanovich.materialdesign.view.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentCoordinatorBinding


class CoordinatorFragment : Fragment() {

    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() {
        return _binding!!
    }

    private val duration = 1000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coordinator, container, false)


}


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