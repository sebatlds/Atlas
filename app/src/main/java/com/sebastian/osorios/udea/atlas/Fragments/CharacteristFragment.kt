package com.sebastian.osorios.udea.atlas.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R

class CharacteristFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_characterist, container, false)
        /*val intent = activity!!.intent
        val country: Countries? = intent?.getSerializableExtra("country") as Countries

        val capital : TextView = root.findViewById(R.id.capital)
        val nombre : TextView = root.findViewById(R.id.nombreReal)
        val region : TextView = root.findViewById(R.id.region)
        val subRegion : TextView = root.findViewById(R.id.sub_region)
        val domain : TextView = root.findViewById(R.id.domain)
        val area : TextView = root.findViewById(R.id.area)
        val poblacion : TextView = root.findViewById(R.id.poblacion)
        val zona : TextView = root.findViewById(R.id.zona_horaria)
        val gini : TextView = root.findViewById(R.id.gini)

        capital.text = country!!.capital
        nombre.text = country.altSpellings.get(country.altSpellings.size-1)
        region.text = country.region
        subRegion.text = country.subregion
        domain.text = country.demonym
        area.text = country.area.toString()
        poblacion.text = country.population.toString()
        zona.text = country.timezones.toString()
        gini.text = country.gini.toString()*/

        return root
    }




}