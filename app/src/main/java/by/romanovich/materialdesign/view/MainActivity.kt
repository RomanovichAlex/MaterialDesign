package by.romanovich.materialdesign.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.view.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(getPreferences(Activity.MODE_PRIVATE).getString("settingTheme","")){
            "chipDay"->{
                setTheme(R.style.MyDarkTheme)
            }
            "chipMars"->{
                setTheme(R.style.MensTheme)
            }
            "chipMoon"->{
                setTheme(R.style.GirlsTheme)
            }
        }


        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}
        //recreate() перезагружает активити и можно выбрать нужную тему
