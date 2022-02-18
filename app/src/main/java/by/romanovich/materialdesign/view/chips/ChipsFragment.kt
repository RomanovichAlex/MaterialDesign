package by.romanovich.materialdesign.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.databinding.FragmentChipsBinding
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment() {


    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            //у чип гроуп находим по позиции, которую выбрали
            binding.chipGroup.findViewById<Chip>(checkedId)?.let{ it->
                //выводим тост по нажатию дефолта
                Toast.makeText(requireContext(), "${it.text} ${checkedId}", Toast.LENGTH_SHORT).show()
            }
        }
        //по нажатию на крестик в чипе
        binding.chipEntry.setOnCloseIconClickListener {
            Toast.makeText(requireContext(), "chipEntry close", Toast.LENGTH_SHORT).show()
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = ChipsFragment()
    }
}