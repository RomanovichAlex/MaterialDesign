package by.romanovich.materialdesign.view.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.romanovich.materialdesign.databinding.ActivityNotesRecyclerBinding


class NotesActivity : AppCompatActivity() {
    lateinit var adapter: NotesRecyclerActivityAdapter
    lateinit var binding: ActivityNotesRecyclerBinding
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = arrayListOf(
            //Data("",type = TYPE_HEADER),
            Pair(ITEM_CLOSE, Data("Earth"))

        )

        val lat = 23
        val lon = 21

        val pair1 = Pair(lat, lon)
        val pair2 = lat to lon


        pair1.first
        pair1.second

        pair2.first
        pair2.second


        adapter = NotesRecyclerActivityAdapter(OnListItemClickListener {
            Toast.makeText(this@NotesActivity, it.someText, Toast.LENGTH_SHORT).show()
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


    class ItemTouchHelperCallback(private val adapter: NotesRecyclerActivityAdapter): ItemTouchHelper.Callback(){

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
            if (viewHolder !is NotesRecyclerActivityAdapter.NoteViewHolder){
                return super.onSelectedChanged(viewHolder, actionState)
            }
            //если в состоянии работы
            if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
//если марс то работаем, если земля то нет
            if (viewHolder !is NotesRecyclerActivityAdapter.NoteViewHolder){
                return super.clearView(recyclerView, viewHolder)
            }
            //подкрашиваем
            (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
            super.clearView(recyclerView, viewHolder)
        }
    }
}