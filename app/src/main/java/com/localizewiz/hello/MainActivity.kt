package com.localizewiz.hello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        this.updateText()
        this.loadJapanese()
        wiz?.listeners?.add {
            this.updateText()
        }
    }

    private fun updateText() {
        // This will show the text in the device locale language
        mainMessage.text = wiz?.getString(R.string.how_do_you_say) ?: ""
        this.title = wiz?.getString(R.string.app_name)

        // You can display text in a specific language that is not the device locale language.
        // Just remember to load that language explicitly first
        catTextView.text = wiz?.getString(R.string.cat, "ja")
    }

    private fun loadJapanese() {
        wiz?.refreshLanguage("ja")
    }

    private fun showChangeLanguage() {
        val intent = Intent(this, ChangeLanguageActivity::class.java)
        startActivity(intent)
    }
}
