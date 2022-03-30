package by.romanovich.materialdesign.view.workToTextFragment

import android.content.Intent
import android.graphics.Typeface

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentWorkToTextBinding
import by.romanovich.materialdesign.view.MainActivity
import by.romanovich.materialdesign.view.main.BottomNavigationDrawerFragment
import by.romanovich.materialdesign.view.settings.SettingsFragment
import by.romanovich.materialdesign.viewmodel.AppState
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayViewModel
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class WorkToTextFragment : Fragment() {


    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: FragmentWorkToTextBinding? = null
    val binding: FragmentWorkToTextBinding
        get() = _binding!!
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkToTextBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })






        viewModel.sendRequest()
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    /*BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")*/
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("mylogs", "slideOffset $slideOffset")
            }
        })
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

                        binding.fab.setOnClickListener {
                            with(binding) {
                                if (isMain) {
                                    with(bottomAppBar) {
                                        navigationIcon = null
                                        fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                                        replaceMenu(R.menu.menu_bottom_bar_other_screen)
                                    }
                                    fab.setImageResource(R.drawable.ic_back_fab)
                                    } else {
                                        with(bottomAppBar) {
                                            navigationIcon = ContextCompat.getDrawable(
                                                requireContext(),
                                                R.drawable.ic_hamburger_menu_bottom_bar
                                            )
                                            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                                            replaceMenu(R.menu.menu_bottom_bar)
                                        }
                                        fab.setImageResource(R.drawable.ic_plus_fab)
                                    }
                                }
                                isMain = !isMain
                            }
                        }


    var isMain = true

                        private fun renderData(appState: AppState) {
                            when (appState) {
                                is AppState.Error -> {
                                }
                                is AppState.Loading -> {
                                }
                                is AppState.SuccessPOD -> {
                                    binding.imageView.load(appState.serverResponse.url){
                                        placeholder(R.drawable.ic_no_photo_vector)
                                    }
                                    binding.included.bottomSheetDescriptionHeader.text = appState.serverResponse.title
                                    binding.included.bottomSheetDescription.text = appState.serverResponse.explanation

                                    binding.textView.typeface = Typeface.createFromAsset(
                                        requireContext().assets, "font/Robus_BWqOd.otf")

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        binding.textView.typeface = resources.getFont(R.font.azeret)
                                    }


                                    //binding.textView.typeface = Typeface.createFromAsset(requireContext().assets, "font/Robus_BWqOd.otf")
                                    // или "fonts/Niceyear.ttf", если шрифты в папке assets/fonts

                                }
                            }
                        }
                        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
                            super.onCreateOptionsMenu(menu, inflater)
                            inflater.inflate(R.menu.menu_bottom_bar, menu)
                        }
                        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                            when (item.itemId) {
                                R.id.app_bar_fav -> {
                                    Toast.makeText(requireContext(), "app_bar_fav", Toast.LENGTH_SHORT).show()
                                }
                                R.id.app_bar_settings -> {
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("")
                                        .commit()
                                }
                                android.R.id.home -> {
                                    BottomNavigationDrawerFragment().show(
                                        requireActivity().supportFragmentManager,
                                        "ff"
                                    )
                                }
                            }
                            return super.onOptionsItemSelected(item)
                        }
                        companion object {
                        @JvmStatic
                        fun newInstance() = WorkToTextFragment()
                    }
                    }