package by.romanovich.materialdesign.view.UX

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityUxBinding

class UXActivity : AppCompatActivity() {
    lateinit var binding: ActivityUxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MensTheme)
        binding = ActivityUxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationViewUX.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fragment_ux_text->{
                    navigateTo(TextFragment())
                    true
                }
                R.id.fragment_ux_button->{
                    navigateTo(ButtonFragment())
                    true
                }
                R.id.fragment_tutorial->{
                    navigateTo(TutorialFragment())
                    true
                }

                else -> true
            }
        }
        binding.bottomNavigationViewUX.selectedItemId = R.id.fragment_ux_text
    }


    //транзакции всегда проходят с кастомной анимацией
    fun navigateTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        ).replace(R.id.container,fragment).commit()
    }

}
