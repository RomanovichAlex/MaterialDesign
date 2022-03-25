package by.romanovich.materialdesign.view.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import by.romanovich.materialdesign.databinding.ActivityNotesRecyclerItemBinding


class NotesRecyclerActivityAdapter (private val onListItemClickListener: OnListItemClickListener,
                                    private val dataSet: MutableList<Pair<Int, Data>>,
                                    private val onStartDragListener : OnStartDragListener
):
    RecyclerView.Adapter<NotesRecyclerActivityAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].first//viewType
    }

    //создаем
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

            val itemBinding: ActivityNotesRecyclerItemBinding =
                ActivityNotesRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            return NoteViewHolder(itemBinding.root)
    }

    //связываем созданый ViewHolder с данными
    override fun onBindViewHolder(holder: NotesRecyclerActivityAdapter.BaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }


    override fun getItemCount() = dataSet.size
    fun addItem() {
        dataSet.add(generateNewItem())
        //сохраняет изменения
        notifyItemInserted(itemCount-1)
    }

    private fun generateNewItem() = Pair(ITEM_CLOSE, Data("Note"))

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Int, Data>)

    }



    inner class NoteViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewAdapter {
        override fun bind(data: Pair<Int, Data>) {
            ActivityNotesRecyclerItemBinding.bind(itemView).apply {
                noteImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data.second)
                }
                addItemImageView.setOnClickListener { addItemByPosition() }
                removeItemImageView.setOnClickListener { removeItem() }
                moveItemUp.setOnClickListener { moveUp() }
                moveItemDown.setOnClickListener { moveDown() }
                noteTextView.setOnClickListener{
                    //дерганно
                    /* marsDescriptionTextView.visibility = if (marsDescriptionTextView.visibility==View.VISIBLE) View.GONE else {
 View.VISIBLE
                     }*/
                    //более плавно
                    dataSet[layoutPosition] = dataSet[layoutPosition].let {
                        val currentState = if(it.first== ITEM_CLOSE) ITEM_OPEN else  ITEM_CLOSE
                        Pair(currentState,it.second)
                    }
                    //применяем изменения более плавно
                    notifyItemChanged(layoutPosition)
                    // дерганно notifyDataSetChanged(layoutPosition)
                }
                descriptionTextView.visibility = if(data.first== ITEM_CLOSE) View.GONE else View.VISIBLE

                dragHandleImageView.setOnTouchListener { v, event ->
                    //если произошло нажатие кнопки
                    if(MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN){
                        onStartDragListener.onStartDrag(this@NoteViewHolder)
                    }
                    false
                }

            }
        }

        private fun moveUp() {
            if (layoutPosition != 0) {
                dataSet.removeAt(layoutPosition).apply {
                    dataSet.add(layoutPosition - 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
        }

        private fun moveDown() {
            if (layoutPosition != dataSet.size-1) {
                dataSet.removeAt(layoutPosition).apply {
                    dataSet.add(layoutPosition+1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        fun removeItem(){
            // data.removeAt(adapterPosition)
            //удаляет то что видит пользователь
            dataSet.removeAt(layoutPosition)
            //обновить всё notifyDataSetChanged()
            //удаление обновляется по позиции + анимация
            notifyItemRemoved(layoutPosition)

        }
        fun addItemByPosition(){
            //добавляет по позиции снизу
            dataSet.add(layoutPosition+1, generateNewItem())
            //сохраняет изменения
            notifyItemInserted(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        //когда перетаскивание закончилось
        override fun onItemClear() {
            //цвет сбрасываем
            itemView.setBackgroundColor(0)
        }
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {

        if (fromPosition != 0) {

            dataSet.removeAt(fromPosition).apply {
                dataSet.add(toPosition, this)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }



}