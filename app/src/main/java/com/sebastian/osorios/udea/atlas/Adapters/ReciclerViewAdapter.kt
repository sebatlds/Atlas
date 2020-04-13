package com.sebastian.osorios.udea.atlas.Adapters

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.sebastian.osorios.udea.atlas.Activitys.CountryActivity
import com.sebastian.osorios.udea.atlas.R
import kotlinx.android.synthetic.main.list_view_item.view.*
import retrofit2.Callback
import java.io.Serializable

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
            itemView.text_view.text = country.name
            itemView.text_view_capital.text = country.capital
            itemView.imageViewArrow.setImageResource(R.drawable.sharp_keyboard_arrow_right_white_18)
            itemView.imageViewFlag.setImageResource(R.drawable.images)
            itemView.setOnClickListener{
                Toast.makeText(context,country.name,Toast.LENGTH_SHORT).show()
                val intent = Intent(context, CountryActivity::class.java)
                intent.putExtra("country",country)
                context.startActivity(intent)
            }
            itemView.setOnLongClickListener{
                Toast.makeText(context,countries!!.name+"LONG",Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }
        }


    }

}