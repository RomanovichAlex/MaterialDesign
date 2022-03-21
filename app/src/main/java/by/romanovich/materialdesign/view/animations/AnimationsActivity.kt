package by.romanovich.materialdesign.view.animations


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.transition.*
import android.view.Gravity
import android.view.View
import by.romanovich.materialdesign.databinding.ActivityAnimationsBinding


class AnimationsActivity : AppCompatActivity() {

    private var textIsVisible = false
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{

            val transitionSet = TransitionSet()
            val slide = Slide(Gravity.END)
            val explode = Explode()
            slide.duration = 2000
            explode.duration = 1000
            transitionSet.ordering = TransitionSet.ORDERING_SEQUENTIAL
            transitionSet.addTransition(slide)
            //transitionSet.addTransition(explode)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,transitionSet)
            textIsVisible = !textIsVisible
            binding.text.visibility = if(textIsVisible) View.VISIBLE else View.GONE
        }
    }
}