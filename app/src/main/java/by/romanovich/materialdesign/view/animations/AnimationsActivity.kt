package by.romanovich.materialdesign.view.animations



import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.databinding.ActivityAnimationsBinding



class AnimationsActivity : AppCompatActivity() {
    private var flag = false
    private val duration = 1000L
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //если изменяется ScrollListener
        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //появляется тень у хэдера
            binding.header.isSelected = binding.scrollView.canScrollVertically(- 1)
        }

    }
}