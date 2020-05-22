package com.localizewiz.hello

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.localizewiz.wiz.Wiz
import com.localizewiz.wiz.models.Language
import com.squareup.picasso.Picasso

class LanguageListAdapter (private var languages: List<Language>, private var languageSelector: LanguageSelector) :
    RecyclerView.Adapter<LanguageListAdapter.MyViewHolder>() {

    private var selectedPosition = -1
    private var wiz = Wiz.instance!!

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                this@LanguageListAdapter.itemSelected(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageListAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_language, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val textView = holder.view.findViewById<TextView>(R.id.textView)
        val flagImageView = holder.view.findViewById<ImageView>(R.id.imageViewFlag)
        val checkMarkView = holder.view.findViewById<ImageView>(R.id.imageViewCheckmark)

        if (position < languages.size) {
            val language = languages[position]
            textView.text = wiz.getString(language.englishName.toLowerCase().replace(" ", "_"))
            Picasso.get().load(language.flagUrl).into(flagImageView)
            checkMarkView.visibility = if (selectedPosition == position) View.VISIBLE else View.INVISIBLE
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    override fun getItemCount() = languages.size

    fun refresh() {
        this.notifyDataSetChanged()
    }

    private fun itemSelected(position: Int) {
        val oldPosition = selectedPosition
        this.selectedPosition = position
        if (oldPosition >= 0) {
            this.notifyItemChanged(oldPosition)
        }
        if (position < languages.size) {
            this.languageSelector.languageSelected(languages[position])
        }
        this.notifyItemChanged(position)
    }

}