package by.romanovich.materialdesign.view.main.photoEarthFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentPhotoEarthBinding

import by.romanovich.materialdesign.viewmodel.AppState
import by.romanovich.materialdesign.viewmodel.PhotoEarthViewModel
import coil.load
import com.google.android.material.snackbar.Snackbar


class PhotoEarthFragment : Fragment() {
    private var _binding: FragmentPhotoEarthBinding? = null
    private val binding: FragmentPhotoEarthBinding get() {
        return _binding!!
    }

    private val viewModel: PhotoEarthViewModel by lazy { ViewModelProvider(this).get(PhotoEarthViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoEarthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) { render(it) }
        binding.chipsGroup2.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipEarthToday -> {
                    binding.chipsGroup2.check(R.id.chipEarthToday)
                    viewModel.sendRequestEarth()
                }
                R.id.chipMarsToday -> {
                    binding.chipsGroup2.check(R.id.chipMarsToday)
                    viewModel.sendRequestMars()
                }
                else ->
                    viewModel.sendRequestMars()
            }
        }
    }

    private fun render(it: AppState) {
        when(it){
            is AppState.Error ->
                Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.customImageView2.load(R.drawable.progress_animation)
            }
            is AppState.SuccessEarthEpic -> {
                // немного магии датамайнинга
                val strDate = it.serverResponseEarthEpicData.last().date.split(" ").first()
                val image =it.serverResponseEarthEpicData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.customImageView2.load(url)
            }
            is AppState.SuccessMars -> {
                if(it.serverResponseMarsData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                }else{
                    val url = it.serverResponseMarsData.photos.first().imgSrc
                    binding.customImageView2.load(url)
                }
            }
        }
    }

    companion object {
        fun newInstance(): PhotoEarthFragment {
            return PhotoEarthFragment()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}