package com.localizewiz.hello

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.localizewiz.wiz.Wiz
import com.localizewiz.wiz.models.Language

import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : AppCompatActivity(), LanguageSelector {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: LanguageListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var selectedLanguage: Language? = null
    private var wiz = Wiz.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)
        setSupportActionBar(toolbar)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        this.updateStrings()
    }

    private fun setupRecyclerView() {
        val project = wiz.project
        viewManager = LinearLayoutManager(this)

        // TODO: project can be null
        val languages = project!!.languages?.toMutableList()
        project.language?.let {
            languages?.add(0, it)
        }
        viewAdapter = LanguageListAdapter(languages ?: listOf(), this)

        recyclerView = findViewById<RecyclerView>(R.id.languageRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            setDivider(R.drawable.recycler_view_divider)
        }
    }

    private fun updateStrings() {
        toolbar.title = wiz?.getString(R.string.change_language)
    }

    private fun changeSelectedLanguage() {
       selectedLanguage?.let {
           wiz.changeLanguage(it.isoCode) {
               this.updateStrings()
               viewAdapter.refresh()
           }
       }
    }

    override fun languageSelected(language: Language) {
        if (language != this.selectedLanguage) {
            this.selectedLanguage = language
            this.changeSelectedLanguage()
        }
    }
}
