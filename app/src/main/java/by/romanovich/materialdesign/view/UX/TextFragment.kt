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
import by.romanovich.materialdesign.databinding.FragmentSettingsBinding
import by.romanovich.materialdesign.databinding.FragmentUxTextBinding
import by.romanovich.materialdesign.view.workToTextFragment.WorkToTextFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout

class TextFragment : Fragment() {

    private var _binding: FragmentUxTextBinding? = null
    val binding: FragmentUxTextBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUxTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = TextFragment()
    }
}