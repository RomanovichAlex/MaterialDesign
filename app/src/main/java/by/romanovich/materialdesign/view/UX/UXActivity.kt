package by.romanovich.materialdesign.view.UX

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityUxBinding

class UXActivity : AppCompatActivity() {
    lateinit var binding: ActivityUxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationViewUX.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fragment_ux_text->{
                    navigateTo(TextFragment())
                    true
                }
                else -> true
            }
        }
        binding.bottomNavigationViewUX.selectedItemId = R.id.fragment_ux_text
    }

    fun navigateTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

}