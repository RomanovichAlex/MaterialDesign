package by.romanovich.materialdesign.view.main.photoEarthFragment


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.BuildConfig
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentPhotoEarthBinding
import by.romanovich.materialdesign.view.notes.NotesActivity
import by.romanovich.materialdesign.viewmodel.AppState
import by.romanovich.materialdesign.viewmodel.PhotoEarthViewModel
import coil.load
import com.google.android.material.snackbar.Snackbar


class PhotoEarthFragment : Fragment() {
    private var _binding: FragmentPhotoEarthBinding? = null
    private val binding: FragmentPhotoEarthBinding get() {
        return _binding!!
    }

    private var flag = false
    private var duration = 1000L

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

        setFAB()

        viewModel.getData().observe(viewLifecycleOwner) { render(it) }

        binding.buttonNotes.setOnClickListener {
            startActivity(Intent(requireContext(), NotesActivity::class.java))
        }

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
                    viewModel.sendRequestEarth()
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


    private fun setFAB() {
        setInitialState()
        binding.fab.setOnClickListener {
            if (flag) {
                collapseFab()


            } else {
                expandFAB()
            }
        }
    }

    private fun expandFAB() {
        flag = true
        ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 405f).setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f, -130f).
        setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f, -260f).
        setDuration(duration).start()

        binding.optionTwoContainer.animate().alpha(1f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionTwoContainer.isClickable= true
                    super.onAnimationEnd(animation)
                        binding.optionTwoContainer.setOnClickListener {
                            viewModel.sendRequestEarth()
                            Toast.makeText(requireContext(), "Earth", Toast.LENGTH_SHORT)
                                .show()
                            collapseFab()
                        }
                    super.onAnimationEnd(animation)
                }
            })
        binding.optionOneContainer.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionOneContainer.isClickable = true
                    binding.optionOneContainer.isClickable= true
                    binding.optionOneContainer.setOnClickListener {
                        viewModel.sendRequestMars()
                        Toast.makeText(requireContext(), "Mars", Toast.LENGTH_SHORT)
                            .show()
                        collapseFab()
                    }
                    super.onAnimationEnd(animation)
                }
            })
        binding.transparentBackground.animate()
            .alpha(0.8f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.transparentBackground.isClickable = true
                }
            })
    }


    private fun collapseFab() {
        flag = !flag
        ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 405f, 0f).setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -130f, 0f).
        setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -260f, 0f).
        setDuration(duration).start()

        binding.optionTwoContainer.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionTwoContainer.isClickable = false
                    super.onAnimationEnd(animation)
                    binding.optionOneContainer.setOnClickListener(null)
                }
            })
        binding.optionOneContainer.animate().alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionOneContainer.isClickable= false
                    super.onAnimationEnd(animation)
                }
            })
        binding.transparentBackground.animate().alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.transparentBackground.isClickable= false
                    super.onAnimationEnd(animation)
                }
            })
    }


    private fun setInitialState() {
        binding.transparentBackground.apply {
            alpha = 0f
        }
        binding.optionTwoContainer.apply {
            alpha = 0f
            isClickable = false
        }
        binding.optionOneContainer.apply {
            alpha = 0f
            isClickable = false
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