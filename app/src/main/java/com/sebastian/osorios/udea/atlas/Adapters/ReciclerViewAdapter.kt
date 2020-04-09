package com.sebastian.osorios.udea.atlas.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sebastian.osorios.udea.atlas.R
import kotlinx.android.synthetic.main.list_view_item.view.*

class ReciclerViewAdapter(context : Context, listCountries : List<Countries>) : RecyclerView.Adapter<ReciclerViewAdapter.CountriesViewHolder>() {

    var listCountries = emptyList<Countries>()
    var context : Context
    private var onItemClickListener: OnItemClickListener
    private var onLongClickListener: OnLongClickListener

    init{
        this.listCountries = listCountries
        this.context = context
        this.onItemClickListener = onItemClickListener
        this.onLongClickListener = onLongClickListener
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

    override fun onBindViewHolder(holder: ReciclerViewAdapter.CountriesViewHolder, position: Int) {
        val countries = listCountries[position]
        holder.bindCountry(countries)
    }

    class CountriesViewHolder(itemView : View,context: Context): RecyclerView.ViewHolder(itemView){
        fun bindCountry(country : Countries){
            itemView.text_view.text = country.name
            itemView.text_view_capital.text = country.capital
            itemView.imageViewArrow.setImageResource(R.drawable.sharp_keyboard_arrow_right_white_18)
            itemView.imageViewFlag.setImageResource(R.drawable.images)
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    interface OnItemClickListener {
        fun onItemClick(countries: Countries)
    }

    interface OnLongClickListener {
        fun onLongClick(country: Countries)
    }
}