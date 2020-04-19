package com.sebastian.osorios.udea.atlas.Activitys

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.ui.main.SectionsPagerAdapter

class CountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        //val country: Countries? = intent?.getSerializableExtra("country") as Countries
        //val title : TextView = findViewById(R.id.title)
        //title.text = country!!.name.toUpperCase() + " (" + country!!.cioc + ")"
    }
}