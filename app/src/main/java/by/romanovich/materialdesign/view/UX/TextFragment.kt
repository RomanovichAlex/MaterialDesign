package by.romanovich.materialdesign.view.UX

import by.romanovich.materialdesign.databinding.FragmentUxTextBinding


class TextFragment: ViewBindingFragment<FragmentUxTextBinding>(FragmentUxTextBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = TextFragment()
    }
}