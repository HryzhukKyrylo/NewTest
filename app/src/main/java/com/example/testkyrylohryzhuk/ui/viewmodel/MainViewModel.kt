package com.example.testkyrylohryzhuk.ui.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkyrylohryzhuk.repository.MapRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _res = MutableLiveData<PolylineOptions>()
    val res: LiveData<PolylineOptions>
        get() = _res

    fun getPoints(from: String, to: String) = viewModelScope.launch {
        mapRepository.getPoints(from, to).let {
            if (it.isSuccessful) {
                val responseObject = it.body()
                val result = ArrayList<List<LatLng>>()
                val path = ArrayList<LatLng>()

                //TODO ///////////////////////////////////////////////////////////////

                if (responseObject != null) {
                    for (element in responseObject.routes[0].legs[0].steps) {
                        path.addAll(element.polyline.points.decodePolyline())
                    }
                }

                result.add(path)

                val lineOption = PolylineOptions()
                for (i in result.indices) {
                    lineOption.addAll(result[i])
                    lineOption.width(widthPolyline)
                    lineOption.color(Color.BLACK)
                    lineOption.geodesic(true)
                }
                withContext(Dispatchers.Main) {
                    _res.postValue(lineOption)
                }
            }
        }
    }

    private fun String.decodePolyline(): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = this.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = this[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = this[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    companion object {
        private const val widthPolyline = 10f
    }
}