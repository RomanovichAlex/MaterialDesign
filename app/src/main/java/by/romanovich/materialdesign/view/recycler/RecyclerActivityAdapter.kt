package by.romanovich.materialdesign.view.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import by.romanovich.materialdesign.databinding.ActivityRecyclerItemEarthBinding
import by.romanovich.materialdesign.databinding.ActivityRecyclerItemHeaderBinding
import by.romanovich.materialdesign.databinding.ActivityRecyclerItemMarsBinding


class RecyclerActivityAdapter(private val onListItemClickListener:OnListItemClickListener,
                              private val dataSet: MutableList<Pair<Int,Data>>):
    RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].second.type
    }
    //создаем
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == TYPE_EARTH) {
            val itemBinding: ActivityRecyclerItemEarthBinding =
                ActivityRecyclerItemEarthBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            return EarthViewHolder(itemBinding.root)
        }else if (viewType == TYPE_MARS) {
            val itemBinding: ActivityRecyclerItemMarsBinding =
                ActivityRecyclerItemMarsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            return MarsViewHolder(itemBinding.root)
            
        } else {
            val itemBinding: ActivityRecyclerItemHeaderBinding =
                ActivityRecyclerItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(itemBinding.root)
        }
    }

    //связываем созданый ViewHolder с данными
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
    fun addItem() {
        dataSet.add(generateNewItem())
        //сохраняет изменения
        notifyItemInserted(itemCount-1)
    }

    private fun generateNewItem() = Pair(ITEM_CLOSE,Data("new Mars", type = TYPE_MARS))

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Int,Data>)

    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Int,Data>) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                earthImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data.second)
                }
                addItemImageView.setOnClickListener {
                    addItemByPosition()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }

            }

        }fun removeItem(){
           // data.removeAt(adapterPosition)
            //удаляет то что видит пользователь
            dataSet.removeAt(layoutPosition)
            //обновить всё
            notifyDataSetChanged()
        }
        fun addItemByPosition(){
            //добавляет по позиции снизу
            dataSet.add(layoutPosition+1, generateNewItem())
            //сохраняет изменения notifyItemInserted(layoutPosition)
            //удаление обновляется по позиции + анимация
            notifyItemRemoved(layoutPosition)
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view),ItemTouchHelperViewAdapter {
        override fun bind(data: Pair<Int,Data>) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data.second)
                }
                addItemImageView.setOnClickListener { addItemByPosition() }
                removeItemImageView.setOnClickListener { removeItem() }
                moveItemUp.setOnClickListener { moveUp() }
                moveItemDown.setOnClickListener { moveDown() }
                marsTextView.setOnClickListener{
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
                marsDescriptionTextView.visibility = if(data.first== ITEM_CLOSE) View.GONE else View.VISIBLE
            }
        }

        private fun moveUp() {
            if (layoutPosition != 1) {
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

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Int,Data>) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                header.setOnClickListener {
                    onListItemClickListener.onListItem(data.second)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {


        //TODO поднимать элементы выше заголовка hw


        dataSet.removeAt(fromPosition).apply {
            dataSet.add(fromPosition , this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}
