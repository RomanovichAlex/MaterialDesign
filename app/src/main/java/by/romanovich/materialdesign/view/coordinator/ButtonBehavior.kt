package by.romanovich.materialdesign.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior (context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<View>(context, attributeSet) {
    //ловим зависимость от аппБара
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        //наша вью должна быть аппбар лайаутом
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        var bar = dependency as AppBarLayout
        //должны изменить высоту в соответствии с аппБаром
        var barY = abs(bar.y)
        var barHeight = bar.height/2

        if (barY>barHeight){
            child.visibility = View.GONE
        }else{
           val alpha = (barHeight - barY)/(barHeight)
            child.alpha = alpha
            child.visibility = View.VISIBLE

        }

        return super.onDependentViewChanged(parent, child, dependency)
    }
}