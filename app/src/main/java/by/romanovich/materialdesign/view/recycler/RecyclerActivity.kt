package by.romanovich.materialdesign.view.recycler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    lateinit var adapter : RecyclerActivityAdapter
    lateinit var binding: ActivityRecyclerBinding
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = arrayListOf(
            //Data("",type = TYPE_HEADER),
            Pair(ITEM_CLOSE, Data("Earth", type = TYPE_EARTH)),
            Pair(ITEM_CLOSE, Data("Earth", type = TYPE_EARTH)),
            Pair(ITEM_CLOSE, Data("Mars", "", type = TYPE_MARS)),
            Pair(ITEM_CLOSE, Data("Earth", type = TYPE_EARTH)),
            Pair(ITEM_CLOSE, Data("Earth", type = TYPE_EARTH)),
            Pair(ITEM_CLOSE, Data("Earth", type = TYPE_EARTH)),
            Pair(ITEM_CLOSE, Data("Mars", "", type = TYPE_MARS))
        )
        data.add(0, Pair(ITEM_CLOSE, Data("Заголовок", type = TYPE_HEADER)))

        /*
    подсказка по пункту
    * Добавьте назначение приоритета заметкам.
    data.filter {
       it.second.someText.equals("swefg")
       //it.second.someText.contains("swefg")
       //it.second.weight==1000
    }
         */

/*
подсказка по пункту
* Добавьте назначение приоритета заметкам.
data.get(2).second.weight = 1000
data.sortWith{l,r->
   if(l.second.weight>r.second.weight){
       -1
   }else{
       1
   }
}*/


        val lat = 23
        val lon = 21

        val pair1 = Pair(lat, lon)
        val pair2 = lat to lon
        val pair3 = Triple(lon, lon, lon)

        pair1.first
        pair1.second

        pair2.first
        pair2.second

        pair3.first
        pair3.second
        pair3.third


        adapter = RecyclerActivityAdapter(OnListItemClickListener {
            Toast.makeText(this@RecyclerActivity, it.someText, Toast.LENGTH_SHORT).show()
        }, data,{
            itemTouchHelper.startDrag(it)
        })

        binding.recyclerView.adapter = adapter
        //передаем коллбэк и запускаем прикрепив к рецайкл вью
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.addItem()

            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }


    }


    class ItemTouchHelperCallback(private val adapter: RecyclerActivityAdapter): ItemTouchHelper.Callback(){

        //сработает перетаскивание
        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            //разрешаем перетаскивать в верх и в низ
            val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            //разрешаем свайпать влево и вправо
            val swipe = ItemTouchHelper.START or ItemTouchHelper.END
            //свайпы и тащить
            return makeMovementFlags(drag,swipe)
        }

        //срабатывает движение
        override fun onMove(
            recyclerView: RecyclerView,
            //куда тащим
            viewHolder: RecyclerView.ViewHolder,
            //куда перетащили
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
            return true
        }

        //закончился свайп и удаляем элемент по позиции свайпа
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            //если марс то работаем, если земля то нет
            if (viewHolder !is RecyclerActivityAdapter.MarsViewHolder){
                return super.onSelectedChanged(viewHolder, actionState)
            }
            //если в состоянии работы
            if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
            }

        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
//если марс то работаем, если земля то нет
            if (viewHolder !is RecyclerActivityAdapter.MarsViewHolder){
                return super.clearView(recyclerView, viewHolder)
            }
            //подкрашиваем
            (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
            super.clearView(recyclerView, viewHolder)
        }
    }
}