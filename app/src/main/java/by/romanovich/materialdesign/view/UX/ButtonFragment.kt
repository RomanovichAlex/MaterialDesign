package by.romanovich.materialdesign.view.UX

import by.romanovich.materialdesign.databinding.FragmentUxButtonBinding


class ButtonFragment : ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {


    companion object {
        @JvmStatic
        fun newInstance() = ButtonFragment()
    }
}