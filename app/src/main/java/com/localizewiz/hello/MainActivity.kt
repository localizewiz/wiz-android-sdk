package com.localizewiz.hello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.localizewiz.localizewiz.Wiz
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var wiz = Wiz.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingActionButton.setOnClickListener {
            this.showChangeLanguage()
        }
    }

    override fun onResume() {
        super.onResume()
        wiz?.refresh {
            mainMessage.text = wiz?.getString(R.string.how_do_you_say) ?: ""
        }
        mainMessage.text = wiz?.getString(R.string.how_do_you_say) ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.changeLanguageMenu) {
            Log.d("MainActivity", "Menu selected")
            this.showChangeLanguage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showChangeLanguage() {
        Log.d("*****CHANGE", "**************************")
        val intent = Intent(this, ChangeLanguageActivity::class.java)
        startActivity(intent)
    }
}
