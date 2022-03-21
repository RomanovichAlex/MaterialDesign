package by.romanovich.materialdesign.view.animations


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.View
import by.romanovich.materialdesign.R
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
            val fade = Fade()
            val changeBounds = ChangeBounds()
            fade.duration = 2000
            changeBounds.duration = 5000
            transitionSet.ordering = TransitionSet.ORDERING_SEQUENTIAL
            transitionSet.addTransition(fade)
            transitionSet.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,transitionSet)
            textIsVisible = !textIsVisible
            binding.text.visibility = if(textIsVisible) View.VISIBLE else View.GONE
        }
    }
}