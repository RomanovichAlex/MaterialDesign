package by.romanovich.materialdesign.view.workToTextFragment

import android.content.Intent
import android.graphics.Typeface

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.*
import android.text.style.*
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
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
                                    binding.imageView.load(appState.serverResponse.url) {
                                        placeholder(R.drawable.ic_no_photo_vector)
                                    }
                                    binding.included.bottomSheetDescriptionHeader.text =
                                        appState.serverResponse.title
                                    binding.included.bottomSheetDescription.text =
                                        appState.serverResponse.explanation


                                    /* val it = binding.textView.text
                                    //ничего не позволяет делать со строкой
                                    val spannableStart = SpannableString(it)
                                    //NORMAL при SpannedString, SPANNABLE при SpannableString можно менять стили
                                    binding.textView.setText(spannableStart, TextView.BufferType.SPANNABLE)

                                    val spannable = binding.textView.text as SpannableString
                                    spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.colorAccent)),
                                        2,10,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)*/

                                    //SpannableStringBilder .insert(позиция 20, текст для вставки "кпк")

                                    /* 7 урок
                                    binding.textView.typeface = Typeface.createFromAsset(
                                        requireContext().assets, "font/Robus_BWqOd.otf")

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        binding.textView.typeface = resources.getFont(R.font.azeret)
                                    }

                                   /* val text = "My <h1> text </h1> <h2> text2 </h2> <ul><li>bullet one</li><li>bullet two</li></ul>"
                                    //FROM_HTML_MODE_COMPACT од на обработку трудных ситуаций универсальный
                                    binding.textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)*/



                                    val spannableMutable = SpannableStringBuilder("My \n text \n text \nbullet one\nbullet two") // если текст изменяется spannable.штыуке()

                                    //если текст неизменяется
                                    val spannableUnMutable = SpannableString("My text \nbullet one\nbullet two")

                                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P) {
                                        spannableMutable.setSpan(
                                            BulletSpan(
                                                20, ContextCompat.getColor(requireContext(),R.color.colorAccent),
                                                10
                                            ),
/* начало элемента списка */ 0, /* конец элемента списка */ 30,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )//вклячая оба
                                        spannableMutable.setSpan(
                                            BulletSpan(20, ContextCompat.getColor(requireContext(),R.color.colorAccent), 10),
/* начало элемента списка */ 4, /* конец элемента списка */ 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )//вклячая оба

                                        spannableMutable.setSpan(
                                            BulletSpan(20, resources.getColor(R.color.colorAccent), 10),
/* начало элемента списка */ 11, /* конец элемента списка */ 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )//вклячая оба
                                    }

                                        spannableMutable.setSpan(
                                            BulletSpan(20, resources.getColor(R.color.colorAccent)),
/* начало элемента списка */ 18, /* конец элемента списка */ spannableMutable.length,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                        )//вклячая оба
                                    spannableMutable.setSpan(
                                        BulletSpan(20, resources.getColor(R.color.colorAccent)),
/* начало элемента списка */ 29, /* конец элемента списка */ spannableMutable.length,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                    )//вклячая оба


                                    //проходим по всему тексту и проверяем == ли наша буква о
                                    for (i in spannableMutable.indices){
                                        if (spannableMutable[i]=='o'){
                                            //и если ровна меняем ее на иконку
                                            spannableMutable.setSpan(ImageSpan(requireContext(),R.drawable.ic_earth),i,i+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                        }
                                    }

                                    binding.textView.text = spannableMutable



                                    //binding.textView.typeface = Typeface.createFromAsset(requireContext().assets, "font/Robus_BWqOd.otf")
                                    // или "fonts/Niceyear.ttf", если шрифты в папке assets/fonts*/

                                    val it = binding.textView.text
                                    //ничего не позволяет делать со строкой
                                    val spannableStart = SpannableStringBuilder(it)
                                    //NORMAL при SpannedString, SPANNABLE при SpannableString можно менять стили
                                    binding.textView.setText(
                                        spannableStart,
                                        TextView.BufferType.EDITABLE)

                                    val spannable = binding.textView.text as SpannableStringBuilder
                                    initSpan(spannable)



                                    //факультатив
                                    //получили указатель на
                                    val handler = Handler(Looper.getMainLooper())
                                    val requestCollback = FontRequest("com.google.android.gms.fonts", "com.google.android.gms","Aguafina Script", R.array.com_google_android_gms_fonts_certs)
                                    val callback = object : FontsContractCompat.FontRequestCallback(){
                                        override fun onTypefaceRetrieved(typeface: Typeface?) {
                                          typeface?.let{
                                              handler.post{
                                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                                  spannable.setSpan(
                                                      TypefaceSpan(it),
                                                      0, 50, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                                  )
                                                  //spannable.insert(0,"1")
                                              }
                                              }
                                          }
                                        }
                                    }


                                    //получаем шрифт в вспомогательном потоке, и возращаем благодаря хэндлеру в главный
                                    FontsContractCompat.requestFont(requireContext(),requestCollback,callback,handler)
                                }

                            }
                        }

    private fun initSpan(spannable: SpannableStringBuilder) {
        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            ),
            2, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.insert(5, "jhj")
        spannable.insert(10, "\n")
        spannable.insert(20, "\n")


        val q: QuoteSpan
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            q = QuoteSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent,
                ), 10, 20
            )

        } else {
            q = QuoteSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent,
                )
            )
        }
        spannable.setSpan(
            q,
            1, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val qq = QuoteSpan(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent,
            )
        )
        spannable.setSpan(
            qq,
            10, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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