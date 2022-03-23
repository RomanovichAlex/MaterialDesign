package by.romanovich.materialdesign.view.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = arrayListOf(
            //Data("",type = TYPE_HEADER),
            Data("Earth",type = TYPE_EARTH),
            Data("Earth",type = TYPE_EARTH),
            Data("Mars", "",type = TYPE_MARS),
            Data("Earth",type = TYPE_EARTH),
            Data("Earth",type = TYPE_EARTH),
            Data("Earth",type = TYPE_EARTH),
            Data("Mars", "",type = TYPE_MARS)
        )
        data.add(0, Data("Заголовок",type = TYPE_HEADER))

        binding.recyclerView.adapter = RecyclerActivityAdapter (OnListItemClickListener{
            Toast.makeText(this@RecyclerActivity,it.someText, Toast.LENGTH_SHORT).show()
        },data)
    }
}