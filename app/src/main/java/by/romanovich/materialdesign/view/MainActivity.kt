package by.romanovich.materialdesign.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.view.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.MensTheme)

        when(getPreferences(Activity.MODE_PRIVATE).getString("settingTheme","")){
            "chipDark"->{
                setTheme(R.style.MyDarkTheme)
            }
            "chipMens"->{
                setTheme(R.style.MensTheme)
            }
            "chipGirls"->{
                setTheme(R.style.GirlsTheme)
            }
        }

        setContentView(R.layout.activity_main)


        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
            //supportFragmentManager.beginTransaction().replace(R.id.container, CoordinatorFragment.newInstance()).commit()

        }

        /*findViewById<Button>(R.id.button1).visibility = View.GONE
        findViewById<Button>(R.id.button2).visibility = View.GONE*/
        //findViewById<Group>(R.id.group1).visibility = View.GONE
    }
}
        //recreate() перезагружает активити и можно выбрать нужную тему
