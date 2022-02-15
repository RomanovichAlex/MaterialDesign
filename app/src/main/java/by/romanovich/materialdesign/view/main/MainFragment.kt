package by.romanovich.materialdesign.view.main


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentMainBinding
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayData
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayViewModel
import coil.load



class MainFragment : Fragment() {

    //зануление банинга из за утечек памяти в он дестрой, чтоб в фоне не висел
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
    get() = _binding!!


    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container,false)
       return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //верни дату вешаем лисенир
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest()
    }


        //обработка лисенера
        private fun renderData(pictureOfTheDayData: PictureOfTheDayData) {
            when (pictureOfTheDayData) {
                is PictureOfTheDayData.Error -> {

                }
                is PictureOfTheDayData.Loading -> {

                }
                is PictureOfTheDayData.Success -> {

                    //в наш байнинг загружаем урл
                    binding.imageView.load(pictureOfTheDayData.serverResponse.url){
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
        }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}