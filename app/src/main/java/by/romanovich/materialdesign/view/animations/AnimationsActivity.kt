package by.romanovich.materialdesign.view.animations

import android.graphics.ImageDecoder
import android.graphics.Rect
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
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

        val titles: MutableList<String> = ArrayList()
        repeat(5){
            titles.add("Item${it}")
        }

        binding.button.setOnClickListener {
            flag = !flag
            //перемешиваем
            titles.shuffle()
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,ChangeBounds())
            binding.transitionsContainer.removeAllViews()

            titles.forEach{
                binding.transitionsContainer.addView(TextView(this).apply {
                    text = it
                    textSize = 20f
                    //присваеваем удаленным элементам те же псевдонимы
                    transitionName = it
                    gravity = Gravity.CENTER_HORIZONTAL
                })
            }

        }


    }
}
