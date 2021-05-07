package com.example.testkyrylohryzhuk.ui.viewmodel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkyrylohryzhuk.decodePolyline
import com.example.testkyrylohryzhuk.repository.MapRepository
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
    val res =_res

    fun getPoints(from: String, to: String) = viewModelScope.launch {
        mapRepository.getPoints(from, to).let { response ->
            if (response.isSuccessful) {

                val result = response.body()?.let {
                    it.routes[0].legs[0].steps.map { element ->
                        element.polyline.points.decodePolyline()
                    }
                }
                val lineOption = PolylineOptions().apply {
                    result?.indices?.forEach { index ->
                        addAll(result[index])
                        width(widthPolyline)
                        color(Color.BLACK)
                        geodesic(true)
                    }
                }
                withContext(Dispatchers.Main) {
                    _res.postValue(lineOption)
                }
            }
        }
    }

    companion object {
        private const val widthPolyline = 10f
    }
}