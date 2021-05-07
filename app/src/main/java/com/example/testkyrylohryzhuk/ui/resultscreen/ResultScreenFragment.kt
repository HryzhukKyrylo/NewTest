@file:Suppress("DEPRECATION")

package com.example.testkyrylohryzhuk.ui.resultscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.testkyrylohryzhuk.R
import com.example.testkyrylohryzhuk.ui.viewmodel.MainViewModel
import com.example.testkyrylohryzhuk.ui.viewmodel.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_result_screen.*

@AndroidEntryPoint
class ResultScreenFragment : Fragment(), OnMapReadyCallback {

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapFragment()

        toolbarNavigation()

    }

    private fun toolbarNavigation() {
        toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapResultFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (viewModel.getSetOrigin() && viewModel.getSetDestination()) {
            var location1 = ""
            var location2 = ""
            viewModel.origin.observe(this, Observer {
                location1 = "" + it.latitude + "," + it.longitude
                mMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title(it.getAddressLine(0))
                )
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.latitude,
                            it.longitude
                        ), 5f
                    )
                )
            })
            viewModel.destination.observe(this, Observer {
                location2 = "" + it.latitude + "," + it.longitude
                mMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title(it.getAddressLine(0))
                )
            })

            mainViewModel.getPoints(location1, location2)
            mainViewModel.res.observe(this, Observer {
                mMap.addPolyline(it)
            })

        }
    }

}
