package by.romanovich.materialdesign.view.UX

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentUxButtonBinding
import by.romanovich.materialdesign.databinding.FragmentUxTextBinding


class ButtonFragment : ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {


    companion object {
        @JvmStatic
        fun newInstance() = ButtonFragment()
    }
}