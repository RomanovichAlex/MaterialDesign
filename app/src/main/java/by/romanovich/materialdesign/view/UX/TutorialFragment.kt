package by.romanovich.materialdesign.view.UX

import android.os.Bundle
import android.view.View
import by.romanovich.materialdesign.databinding.FragmentTutorialBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity


class TutorialFragment : ViewBindingFragment<FragmentTutorialBinding>(FragmentTutorialBinding::inflate){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    showBad()

}

    private fun showBad() {

            val builder = GuideView.Builder(requireContext())
                .setTitle("Это не правильно")
                .setContentText("Мы не работаем с цветами и размерами")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(binding.layoutBad)
                .setGuideListener {
                    //запустим вторую подсказуку
                    showGood()
                }
            builder.build().show()
        }

    private fun showGood() {

            val builder = GuideView.Builder(requireContext())
                .setTitle("Это правильно")
                .setContentText("Мы работаем с прозрачностью")
                .setGravity(Gravity.auto)
                .setDismissType(DismissType.anywhere)
                .setTargetView(binding.layoutGood)
            builder.build().show()
        }
    

    companion object {
        @JvmStatic
        fun newInstance() = TutorialFragment()
    }
}