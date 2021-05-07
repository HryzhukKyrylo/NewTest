package com.example.testkyrylohryzhuk.ui.viewmodel


import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var origin = MutableLiveData<Address>()
    var destination = MutableLiveData<Address>()
    private var setOrigin = false
    private var setDestination = false

    var listGlobal = mutableListOf<Address>()
    var listSearch = mutableListOf<Address>()

    fun searchAddress(str: String, geocoder: Geocoder): Boolean {
        val list = geocoder.getFromLocationName(str, 5)
        if (list.isNotEmpty()) {
            listSearch.clear()
            list.forEach { element ->
                addInSearch(element)
            }
        }
        return list.isNotEmpty()
    }

    fun searchAndAddToGlobal(str: String, geocoder: Geocoder) {
        geocoder.getFromLocationName(str, 1).apply {
            addInGlobalList(this.first())
        }
    }

    fun getAddress(lat: Double, lng: Double, geocoder: Geocoder): Address =
         geocoder.getFromLocation(lat,lng,1).first()


    private fun addInGlobalList(adr: Address) = listGlobal.add(adr)

    private fun addInSearch(adr: Address) = listSearch.add(adr)


    fun setMyOrigin(data: Address) {
        origin.postValue(data)
        setOrigin = true
    }

    fun setMyDestination(data: Address) {
        destination.postValue(data)
        setDestination = true
    }


    fun getSetOrigin() = setOrigin

    fun getSetDestination() = setDestination
}