package com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment.epoxy

import android.location.Address
import com.airbnb.epoxy.EpoxyController
class DataController : EpoxyController() {

    private var foodItems: List<Address>? = null
    private var listener: OnItemClickedListener? = null

    override fun buildModels() {
        foodItems?.forEach { entry ->
            dataViewHolder {
                id(hashCode())
                name(entry.getAddressLine(0))
                description("latitude = " + entry.latitude + ", " + "longitude = " + entry.longitude)
                onClicked { listener?.onEntryClicked(entry) }
            }
        }
    }

    fun setData(list: List<Address>) {
        foodItems = list.distinct()
        requestModelBuild()
    }

    fun setListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    interface OnItemClickedListener {
        fun onEntryClicked(data: Address)
    }

}