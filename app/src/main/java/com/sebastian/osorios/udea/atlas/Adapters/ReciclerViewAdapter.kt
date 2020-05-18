package com.sebastian.osorios.udea.atlas.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.sebastian.osorios.udea.atlas.Activitys.CountryActivity
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import kotlinx.android.synthetic.main.list_view_item.view.*


class ReciclerViewAdapter(
    context: Context, listCountries: List<Countries>
) : RecyclerView.Adapter<ReciclerViewAdapter.CountriesViewHolder>() {

    var listCountries = emptyList<Countries>()
    var context: Context
    private val requestBuilder: RequestBuilder<PictureDrawable>? = null

    init {
        this.listCountries = listCountries
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReciclerViewAdapter.CountriesViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)
        return CountriesViewHolder(itemView, context)
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
        itemView: View,
        context: Context
    ) : RecyclerView.ViewHolder(itemView) {
        val commonFunctions = CommonFunctions()
        private val context: Context
        private var countries: Countries? = null

        init {
            this.context = context
        }

        fun bindCountry(country: Countries) {
            var name: String? = country.name
            if (name != null && name.length >= 28) {
                name = changeName(name)
            }

            itemView.text_view.text = name
            itemView.text_view_capital.text = country.capital
            itemView.imageViewArrow.setImageResource(R.drawable.drawable_grupo1_arrow_right_white)
            if (country.altSpellings.size != 0) {
                itemView.imageViewFlag.setImageResource(commonFunctions.getFlagsOfCountry(country.altSpellings[0].toLowerCase()))
            } else {
                itemView.imageViewFlag.setImageResource(R.drawable.naciones_unidas)
            }

            itemView.setOnClickListener {
                val intent: Intent = Intent(itemView.context, CountryActivity::class.java)
                intent.putExtra("country", country)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                itemView.context.startActivity(intent)
            }

        }

        fun changeName(name: String): String {
            var count: Int = 0
            var caract: CharArray = name.toCharArray()
            var aux: String? = null
            for (item in 0..name!!.length - 1) {
                val spaceCaracter: CharArray = " ".toCharArray()
                if (caract[item].equals(spaceCaracter[0])) {
                    count++
                    if (count == 2 || count == 5) {
                        aux = aux + "\n"
                    }else {
                        aux = aux + caract[item]
                    }
                } else {
                    if (aux != null) {
                        aux = aux + caract[item]
                    } else {
                        aux = caract[item].toString()
                    }
                }
            }
            return aux!!
        }

    }


}