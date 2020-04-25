package com.sebastian.osorios.udea.atlas.Fragments

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R
import kotlinx.android.synthetic.main.fragment_geolocation.*

class GeolocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitud : Double ? = null
    private var longitud : Double ? = null
    private var name : String ? = null


    companion object{
        private const val LOCATION_PERMIOSSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_geolocation, container, false)
        val intent = activity?.intent
        val country : Countries = intent!!.getSerializableExtra("country") as Countries
        latitud = country.latlng[0]
        longitud = country.latlng[1]
        name = country.translations.espanish
        var mapView: MapView = root.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        return root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        val marker = LatLng(latitud!!.toDouble(), longitud!!.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(marker)
                .title(name)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,4F))
    }



}