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
    private var duration = 1000L
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.transparentBackground.alpha = 0f
        binding.optionOneContainer.alpha = 0f
        binding.optionOneContainer.isClickable= false
        binding.optionTwoContainer.alpha = 0f
        binding.optionTwoContainer.isClickable= false

        binding.fab.setOnClickListener {
            flag = ! flag
            if (flag) {
                //крутим +
                //ObjectAnimator.ofFloat(binding.plusImageview,"rotation",0f,405f).start()
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 405f).
                setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f, -130f).
                setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f, -260f).
                setDuration(duration).start()

                //анимируем выезджание
                binding.optionTwoContainer.animate().alpha(1f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionTwoContainer.isClickable= true
                            super.onAnimationEnd(animation)
                        }
                    })
                //анимируем выезджание
                binding.optionOneContainer.animate().alpha(1f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionOneContainer.isClickable= true
                            super.onAnimationEnd(animation)
                        }
                    })

                //анимируем затемнение фона
                binding.transparentBackground.animate().alpha(0.8f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.transparentBackground.isClickable= true
                            super.onAnimationEnd(animation)
                        }
                    })
            }else{
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 405f, 0f).
                setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -130f, 0f).
                setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -260f, 0f).
                setDuration(duration).start()

                binding.optionTwoContainer.animate().alpha(0f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionTwoContainer.isClickable= false
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionOneContainer.animate().alpha(0f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionOneContainer.isClickable= false
                            super.onAnimationEnd(animation)
                        }
                    })

                binding.transparentBackground.animate().alpha(0f)
                    .setDuration(duration)
                    .setListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.transparentBackground.isClickable= false
                            super.onAnimationEnd(animation)
                        }
                    })

            }
        }
    }
}
