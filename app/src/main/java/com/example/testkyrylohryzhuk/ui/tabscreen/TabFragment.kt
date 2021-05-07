package com.example.testkyrylohryzhuk.ui.tabscreen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testkyrylohryzhuk.R
import com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment.BottomFragment
import com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment.OnSelectionsListener
import com.example.testkyrylohryzhuk.ui.viewmodel.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    OnSelectionsListener {

    private var positionTab: Int? = null
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private val viewModel: SharedViewModel by activityViewModels()
    private val geocoder by lazy { Geocoder(context) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO //////////////////////////////////////////////////
        arguments?.takeIf { it.containsKey("object") }?.apply {
            positionTab = getInt("object")
        }


        initMapFragment()
        initListeners()
    }

    private fun initMapFragment(){
        mapFragment =
            (childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)
    }

    private fun initListeners() {
        searchTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                viewModel.searchAndAddToGlobal(item.toString(), geocoder)
                BottomFragment().show(childFragmentManager, "TAG")
            }

        searchButton.setOnClickListener {
            val searchText = searchTextView.text.toString()
            if (searchText != "" && searchText.isNotEmpty()) {
                val success = viewModel.searchAddress(searchText, geocoder)
                if (success) {
                    val adapter = ArrayAdapter(
                        context as Context,
                        android.R.layout.simple_dropdown_item_1line,
                        viewModel.listSearch.map { x -> x.getAddressLine(0) }
                    )
                    searchTextView.setAdapter(adapter)
                    searchTextView.showDropDown()
                } else {
                    Toast.makeText(requireContext(), "Not Found!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener { location ->
            run {
                val data =
                    viewModel.getAddress(location.latitude, location.longitude, requireContext())

                mMap.clear()
                mMap.addMarker(MarkerOptions().position(location).title(data.getAddressLine(0)))

                when (positionTab) {
                    1 -> {
                        viewModel.setMyOrigin(data)
                        searchTextView.setText(data.getAddressLine(0))
                    }
                    2 -> {
                        viewModel.setMyDestination(data)
                        searchTextView.setText(data.getAddressLine(0))
                    }
                }
            }
        }
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun addSelectionsMarker(data: Address) {
        when (positionTab) {
            1 -> {
                viewModel.setMyOrigin(data)
                mMap.clear()
                mMap.addMarker(
                    MarkerOptions().position(LatLng(data.latitude, data.longitude))
                        .title(data.getAddressLine(0))
                )
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            data.latitude,
                            data.longitude
                        ), latLngZoom
                    )
                )
            }
            2 -> {
                viewModel.setMyDestination(data)
                mMap.clear()
                mMap.addMarker(
                    MarkerOptions().position(LatLng(data.latitude, data.longitude))
                        .title(data.getAddressLine(0))
                )
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            data.latitude,
                            data.longitude
                        ), latLngZoom
                    )
                )
            }
        }
    }
    companion object{
        private val latLngZoom = 12f
    }
}

