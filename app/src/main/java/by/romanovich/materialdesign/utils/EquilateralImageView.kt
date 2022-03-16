package by.romanovich.materialdesign.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import by.romanovich.materialdesign.view.coordinator.ButtonBehavior

//Этот класс мы будем использовать для отображения равносторонних
//фотографий на всю ширину экрана:
//@JvmOverloads анотация которая для джава генерирует три конструктора
class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?=null,
    defStyleAttr: Int = 0
): AppCompatImageView(context,attributeSet,defStyleAttr), CoordinatorLayout.AttachedBehavior {

    //переопредиляем и говорим что высота ровна ширине, картинка будет всегда квадратная
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun getBehavior(): ButtonBehavior {
        return ButtonBehavior(context)
    }
}
