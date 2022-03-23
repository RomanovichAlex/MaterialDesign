package by.romanovich.materialdesign.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import by.romanovich.materialdesign.databinding.ActivityRecyclerItemEarthBinding
import by.romanovich.materialdesign.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(private val onListItemClickListener:OnListItemClickListener,
                              private val data: List<Data>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
    //создаем
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_EARTH) {
            val itemBinding: ActivityRecyclerItemEarthBinding =
                ActivityRecyclerItemEarthBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            return EarthViewHolder(itemBinding.root)
        } else {
            val itemBinding: ActivityRecyclerItemMarsBinding =
                ActivityRecyclerItemMarsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            return MarsViewHolder(itemBinding.root)

        }
    }

    //связываем созданый ViewHolder с данными
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_EARTH){
            (holder as EarthViewHolder).bind(data[position])
        }else{
            (holder as MarsViewHolder).bind(data[position])
        }
        //holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                earthImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onListItem(data)
                }
            }
        }
    }
}
