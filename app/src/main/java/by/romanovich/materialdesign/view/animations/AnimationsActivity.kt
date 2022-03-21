package by.romanovich.materialdesign.view.animations

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityAnimationsBinding


class AnimationsActivity : AppCompatActivity() {

    private var textIsVisible = false
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = MyAdapter()

    }

    //огда не видит binding, добавляем к классу inner и он видит элементы родительского класса
    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        }

        //создаем холдер
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            //возращаем MyViewHolder который принимает сгенерированный макет
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_animations_recycler_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val transitionSet = TransitionSet()

                //взрыв из центра
                val explode = Explode()
                val fade = Fade()
                explode.duration = 3000
                fade.duration = 99999000

                //взрыв из места нажатия
                val rect1 = Rect()
                explode.excludeTarget(it,true)


                val rect2 = Rect(it.x.toInt(), it.y.toInt(),it.x.toInt() + it.width, it.y.toInt()+ it.height)

                it.getGlobalVisibleRect(rect1)
                explode.epicenterCallback = object : Transition.EpicenterCallback(){
                    override fun onGetEpicenter(transition: Transition): Rect {
                       return rect1
                    }

                }
                transitionSet.addTransition(explode)
                transitionSet.addTransition(fade)

                TransitionManager.beginDelayedTransition(binding.transitionsContainer, transitionSet)

                //при нажатии на кнопку будет пустой макет
                binding.recyclerView.adapter = null

            }
        }

            //кол-во элементов
            override fun getItemCount(): Int {
                return 28
            }
        }
    }
