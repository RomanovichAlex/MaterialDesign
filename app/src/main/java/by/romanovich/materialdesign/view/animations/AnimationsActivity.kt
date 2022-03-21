package by.romanovich.materialdesign.view.animations

import android.graphics.ImageDecoder
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityAnimationsBinding
import kotlin.random.Random


class AnimationsActivity : AppCompatActivity() {

    private var flag = false
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            flag = !flag
            val changeBounds = ChangeBounds()
            //кнопка едет по кривой
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = 3000

            TransitionManager.beginDelayedTransition(binding.transitionsContainer,changeBounds)
            //меняем параметры кнопки по отношению к своему фрайму
            val params = binding.button.layoutParams as FrameLayout.LayoutParams
            params.gravity = if(flag){
               Gravity.BOTTOM or Gravity.END
            }else{
                Gravity.TOP or Gravity.START
            }
            binding.button.layoutParams = params
        }


    }
}
