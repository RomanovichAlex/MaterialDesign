package by.romanovich.materialdesign.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class NestedBehavior(context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<View>(context, attributeSet) {
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
        child.y = bar.height + bar.y

        return super.onDependentViewChanged(parent, child, dependency)
    }


}