package com.sebastian.osorios.udea.atlas.Activitys

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.sebastian.osorios.udea.atlas.Adapters.CountryPageAdapter
import com.sebastian.osorios.udea.atlas.Fragments.CharacteristFragment
import com.sebastian.osorios.udea.atlas.Fragments.GeolocationFragment
import com.sebastian.osorios.udea.atlas.Fragments.OthersFragment
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R
import kotlinx.android.synthetic.main.app_bar_main.*

class CountryActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)
        setSupportActionBar(toolbar)
        val adapter = CountryPageAdapter(supportFragmentManager)
        val viewPager : ViewPager = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        adapter.addFragment(CharacteristFragment(),"Caracteristicas")
        adapter.addFragment(OthersFragment(),"Otras Caracteristicas")
        adapter.addFragment(GeolocationFragment(),"Geolocalizacion")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        val country: Countries? = intent?.getSerializableExtra("country") as Countries
        setName(country)
        setNativeName(country)

    }
    private fun setName(country: Countries?){
        val title : TextView = findViewById(R.id.title)
        if(country!!.cioc.equals(""))
            title.text = country.name.toUpperCase()
        else{
            title.text = country.name.toUpperCase() + " (" + country!!.cioc + ")"
        }

    }

    private fun setNativeName(country : Countries?){
        val nativeName : TextView = findViewById(R.id.native_name)
        if(!country!!.nativeName.equals("")){
            nativeName.text = country.nativeName
        }else{
            nativeName.isVisible = false
        }

    }

}