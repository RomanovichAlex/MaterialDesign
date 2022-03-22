package by.romanovich.materialdesign.view.animations



import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityAnimationsBonusStartBinding


class AnimationsActivity : AppCompatActivity() {
    private var flag = true
    private val duration = 1000L
    private val mTension = 1f
    lateinit var binding: ActivityAnimationsBonusStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backgroundImage.setOnClickListener{
            flag = !flag
            if (flag) {
                val constraintSet = ConstraintSet()

                //constraintSet клонирует в себя стартовый макет
                constraintSet.clone(this, R.layout.activity_animations_bonus_start)

                val changeBounds = ChangeBounds()
                changeBounds.duration = duration
                changeBounds.interpolator = AnticipateInterpolator(mTension)
                //Добавляем плавности
                TransitionManager.beginDelayedTransition(binding.constraintContainer, ChangeBounds())
// и применяем к стартовому экрану
                constraintSet.applyTo(binding.constraintContainer)
            }else{
                val constraintSet = ConstraintSet()


                constraintSet.clone(this, R.layout.activity_animations_bonus_end)
                /*//constraintSet клонирует в себя стартовый макет
                constraintSet.clone(this, R.layout.activity_animations_bonus_start)
                constraintSet.connect(R.id.title,ConstraintSet.END,R.id.constraint_container,ConstraintSet.END)
                constraintSet.connect(R.id.title,ConstraintSet.TOP,R.id.constraint_container,ConstraintSet.TOP)
                constraintSet.connect(R.id.title,ConstraintSet.START,R.id.constraint_container,ConstraintSet.START)
*/
                val changeBounds = ChangeBounds()
                changeBounds.duration = duration
                changeBounds.interpolator = AnticipateInterpolator(mTension)
                TransitionManager.beginDelayedTransition(binding.constraintContainer, ChangeBounds())
// и применяем к стартовому экрану
                constraintSet.applyTo(binding.constraintContainer)
            }
        }
    }
}