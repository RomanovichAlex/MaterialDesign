package by.romanovich.materialdesign.view.recycler

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {
    //передвинули
    fun onItemMove(fromPosition: Int,toPosition: Int)
    //удалили
    fun onItemDismiss(position: Int)

}

//обновили состояние
interface ItemTouchHelperViewAdapter {
    //выбран
    fun onItemSelected()
    //не выбран
    fun onItemClear()
}

//перетаскивание
fun interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}