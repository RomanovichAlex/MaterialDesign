package by.romanovich.materialdesign.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}