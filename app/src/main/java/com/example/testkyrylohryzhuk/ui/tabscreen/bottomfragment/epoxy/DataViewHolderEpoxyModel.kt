package com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment.epoxy

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.testkyrylohryzhuk.R
import kotlinx.android.synthetic.main.item_name.view.*

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.item_name)
abstract class DataViewHolderEpoxyModel :
    EpoxyModelWithHolder<DataViewHolderEpoxyModel.EntryHolder>() {

    @EpoxyAttribute
    var name: CharSequence? = ""

    @EpoxyAttribute
    var description: CharSequence? = ""


    @EpoxyAttribute
    lateinit var onClicked: () -> Unit

    override fun bind(holder: EntryHolder) {
        holder.entryName.text = name
        holder.entryDesription.text = description
        holder.entryName.setOnClickListener { onClicked() }
    }

    class EntryHolder : EpoxyHolder() {
        lateinit var entryName: TextView
        lateinit var entryDesription: TextView


        override fun bindView(itemView: View) {
            entryName = itemView.tittle_name
            entryDesription = itemView.description_name
        }
    }
}