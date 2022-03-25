package by.romanovich.materialdesign.view.home


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentHomeBinding
import by.romanovich.materialdesign.viewmodel.AppState
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayViewModel
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    var flag = false

    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    //зануление банинга из за утечек памяти в он дестрой, чтоб в фоне не висел
    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
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
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
       return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        //верни дату вешаем лисенир
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(takeDate(0))

        tabLayoutInit()




        //по клику на википедию открываем википедию, по введенному тексту
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        /*старый боттом шит
        //получаем боттом... из контейнера ...

        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        //открыть шторку на половину
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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
            //когда слайд движется
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("mylogs", "slideOffset $slideOffset")
            }
        })
//связываем setSupportActionBar с MainActivity
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener{
            if(isMain){
                //по нажатию прячем бургер
                binding.bottomAppBar.navigationIcon = null
                //по центру если на главной странице
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
               //меняем кнопку на назад
                binding.fab.setImageResource(R.drawable.ic_back_fab)
                //скрываем иконки
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                //открываем шторку
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                //меняем кнопку на по умолчанию
                binding.fab.setImageResource(R.drawable.ic_plus_fab)
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                //закрываем шторку
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            isMain = !isMain
        }*/
    }


    private fun tabLayoutInit() {
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        viewModel.sendRequest(takeDate(0))
                    }
                    1 -> {
                        viewModel.sendRequest(takeDate(-1))
                    }
                    2 -> {
                        viewModel.sendRequest(takeDate(-2))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    /*переделка
    var isMain = true

    //обработчик нажатий
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //если
            R.id.app_bar_fav -> {
                Toast.makeText(requireContext(), "app_bar_fav", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,SettingsFragment.newInstance()).addToBackStack("").commit()

            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"fl")

            }
        }
        return super.onOptionsItemSelected(item)
    }*/



        //обработка лисенера
        private fun renderData(appState: AppState) {
            when (appState) {
                is AppState.Error -> {
                    loadingFailed(appState.error,appState.code)
                }
                is AppState.Loading -> {

                }
                is AppState.SuccessPOD -> {
                    //в наш байнинг загружаем урл
                    binding.imageView.load(appState.serverResponse.url){
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    binding.included.bottomSheetDescriptionHeader.text = appState.serverResponse.title
                    binding.included.bottomSheetDescription.text = appState.serverResponse.explanation
                }
            }
        }

    private fun loadingFailed(textId: Int, code: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater? = LayoutInflater.from(requireContext())
        val exitView: View = inflater!!.inflate(R.layout.dialog_error, null)
        dialog.setView(exitView)
        val dialog1: Dialog? = dialog.create()
        val ok: Button = exitView.findViewById(R.id.ok)
        val codeTextView = exitView.findViewById<TextView>(R.id.textError)

        codeTextView.text = when(textId) {
            R.string.serverError -> getString(R.string.serverError)
            R.string.codeError -> getString(R.string.codeError) + " " + code
            else -> ""
        }
        dialog1?.setCancelable(false)
        ok.setOnClickListener {
            dialog1?.dismiss()
            requireActivity().onBackPressed()
        }
        dialog1?.show()
    }


   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }*/


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}