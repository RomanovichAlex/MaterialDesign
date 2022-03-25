package by.romanovich.materialdesign.view.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Size
import androidx.recyclerview.widget.RecyclerView

import by.romanovich.materialdesign.databinding.ActivityRecyclerItemEarthBinding
import by.romanovich.materialdesign.databinding.ActivityRecyclerItemHeaderBinding
import by.romanovich.materialdesign.databinding.ActivityRecyclerItemMarsBinding


class RecyclerActivityAdapter(private val onListItemClickListener:OnListItemClickListener,
                              private val dataSet: MutableList<Data>):
    RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
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

    private fun generateNewItem() = Data("new Mars", type = TYPE_MARS)

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Data)

    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                earthImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data)
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

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data)
                }
                addItemImageView.setOnClickListener {
                    addItemByPosition()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
                moveItemUp.setOnClickListener {
                    //
                    moveUp()
                }
                moveItemDown.setOnClickListener {
//
                    moveDown()
                }
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
        }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Data) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                header.setOnClickListener {
                    onListItemClickListener.onListItem(data)
                }
            }
        }
    }
}
