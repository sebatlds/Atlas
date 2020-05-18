package com.sebastian.osorios.udea.atlas.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R

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
        if(country.latlng.size != 0){
            latitud = country.latlng[0]
            longitud = country.latlng[1]
            name = country.translations.espanish
            var mapView: MapView = root.findViewById(R.id.map)
            mapView.onCreate(savedInstanceState)
            mapView.onResume()
            mapView.getMapAsync(this)
        }else{
            val alert = AlertDialog.Builder(root.context)
            alert.setTitle("Informacion")
            alert.setMessage("No se encuentra disponible la localización del país.")
            alert.setPositiveButton("Aceptar",null)
            alert.show()
        }

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