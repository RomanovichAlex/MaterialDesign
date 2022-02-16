package by.romanovich.materialdesign.view.main


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.FragmentMainBinding
import by.romanovich.materialdesign.view.MainActivity
import by.romanovich.materialdesign.view.chips.ChipsFragment
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayData
import by.romanovich.materialdesign.viewmodel.PictureOfTheDayViewModel
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainFragment : Fragment() {


    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
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

        //по клику на википедию открываем википедию, по введенному тексту
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
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
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                //меняем кнопку на по умолчанию
                binding.fab.setImageResource(R.drawable.ic_plus_fab)
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }
    }

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
                    .replace(R.id.container,ChipsFragment.newInstance()).commit()

            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"fl")

            }
        }
        return super.onOptionsItemSelected(item)
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }




    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}