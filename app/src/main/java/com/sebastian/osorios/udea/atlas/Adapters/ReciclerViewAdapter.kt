package com.sebastian.osorios.udea.atlas.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.larvalabs.svgandroid.SVG
import com.larvalabs.svgandroid.SVGParser
import com.sebastian.osorios.udea.atlas.Activitys.CountryActivity
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_view_item.view.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ReciclerViewAdapter(
    context: Context, listCountries: List<Countries>
) : RecyclerView.Adapter<ReciclerViewAdapter.CountriesViewHolder>() {

    var listCountries = emptyList<Countries>()
    var context : Context

    init{
        this.listCountries = listCountries
        this.context = context
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReciclerViewAdapter.CountriesViewHolder {
        var itemView : View = LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false)
        return CountriesViewHolder(itemView,context)
    }

    override fun getItemCount(): Int {
       return listCountries.size
    }

    override fun onBindViewHolder(
        holder: ReciclerViewAdapter.CountriesViewHolder,
        position: Int
    ) {
        val countries = listCountries[position]
        holder.bindCountry(countries)
    }

    class CountriesViewHolder(
        itemView : View,
        context: Context
    ): RecyclerView.ViewHolder(itemView){

        private val context : Context
        private var countries : Countries? = null

        init {
            this.context = context
        }

        fun bindCountry(country : Countries){
            var name : String? = country.translations?.espanish
            if(!name.equals(null)){
                name = country?.translations?.espanish
            }else {
                name = country?.name
            }
            if (name != null && name.length > 30) {
                name = chengeName(name)
            }
            itemView.text_view.text = name
            itemView.text_view_capital.text = country.capital
            itemView.imageViewArrow.setImageResource(R.drawable.sharp_keyboard_arrow_right_white_18)
            itemView.imageViewFlag.setImageResource(R.drawable.images)
            itemView.setOnClickListener{
                val intent = Intent(context, CountryActivity::class.java)
                intent.putExtra("country",country)
                context.startActivity(intent)
            }
        }

        fun chengeName(name : String) : String {
            var count : Int =0
            var caract : CharArray = name.toCharArray()
            var aux : String? = null
            for(item  in 0..name!!.length-1){
                val spaceCaracter : CharArray = " ".toCharArray()
                if(caract[item].equals(spaceCaracter[0])){
                    count ++
                    if(count == 3){
                        aux = aux + "\n"
                    }else{
                        aux = aux + caract[item]
                    }
                }else{
                    if(aux != null){
                        aux = aux + caract[item]
                    }else{
                        aux = caract[item].toString()
                    }
                }
            }
            return aux!!
        }


    }


}