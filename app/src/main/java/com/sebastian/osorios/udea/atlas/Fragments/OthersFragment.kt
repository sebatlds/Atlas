package com.sebastian.osorios.udea.atlas.Fragments


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.R


class OthersFragment : Fragment() {


    var textView : TextView ? = null
    var textBorders : TextView ? = null
    var textCurrencies : TextView ? = null
    var linearRegionalBloc : LinearLayout ? = null
    var linearCallingCodes : LinearLayout ? = null
    var textDomain : TextView ? = null
    var textCodeCountry : TextView ? = null
    var textTitleBorders : TextView ? = null
    var textTitleRegionalBloc : TextView ? = null
    var textTopLevel : TextView ? = null
    var textTitleTopLevel : TextView ? = null
    var textTitleCalling : TextView ? = null
    var textTitleCountryCodes : TextView ? = null
    var textCountryCode : TextView ? = null
    lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_others, container, false)
        val intent = activity?.intent
        val country: Countries? = intent?.getSerializableExtra("country") as Countries
        textBorders = root.findViewById(R.id.limites)
        textView = root.findViewById(R.id.idiomas)
        textCurrencies = root.findViewById(R.id.currency)
        linearRegionalBloc = root.findViewById(R.id.layout_regional_bloc)
        linearCallingCodes = root.findViewById(R.id.linear_calling)
        textDomain = root.findViewById(R.id.top_level)
        textCodeCountry = root.findViewById(R.id.code_country)
        textTitleBorders = root.findViewById(R.id.title_borders)
        textTitleRegionalBloc = root.findViewById(R.id.title_regional_bloc)
        textTopLevel = root.findViewById(R.id.top_level)
        textTitleTopLevel = root.findViewById(R.id.title_top_level)
        textTitleCalling = root.findViewById(R.id.title_calling)
        textTitleCountryCodes = root.findViewById(R.id.title_country_code)
        textCountryCode = root.findViewById(R.id.code_country)
        setBordersData(country)
        setLanguajeData(country)
        setCurrencies(country)
        setRegionalBloc(country)
        setCallingCodes(country)
        setOthers(country)
        return root
    }

    private fun setBordersData(
        country: Countries?
    ){
        if(country!!.borders.size != 0) {
            var borders = ""
            for (item in 0..country.borders.size - 1) {
                if(item != country.borders.size - 1){
                    borders = borders + " " + country.borders[item]+","
                }else{
                    borders = borders + " " +  country.borders[item]
                }
            }
            textBorders!!.text = borders
        }else{
            textTitleBorders!!.isVisible = false
        }
    }

    private fun setLanguajeData(country : Countries?){
        if(country!!.languages.size > 1) {
            var languajes  = ""
            for (item in 0..country.languages.size - 1) {
                if(item != country.languages.size - 1){
                    languajes = languajes + " " + country.languages[item].name+"("+country.languages[item].iso6392+"), "

                }else{
                    languajes = languajes + " " + country.languages[item].name+"("+country.languages[item].iso6392+")"
                }

            }
            textView!!.text = languajes
        }else{
            textView!!.text = country.languages[0].name+"("+country.languages[0].iso6392+")"

        }
    }

    private fun setCurrencies(country: Countries?){
        if(country!!.currencies.size != 0) {
            var currencies = ""
            for (item in 0..country.currencies.size - 1) {
                currencies = currencies + " " + country.currencies[item].name+"("+country.currencies[item].symbol+")"
            }
            textCurrencies!!.text = currencies
        }else{
            root.findViewById<TextView>(R.id.currencies).isVisible = false
        }
    }

    private fun setRegionalBloc(country: Countries?){
        if(country!!.regionalBlocs.size != 0){
            for (item in 0..country.regionalBlocs.size - 1) {
                val textView = TextView(this.context)
                textView.text = country.regionalBlocs[item].name+"("+country.regionalBlocs[item].acronym+")"
                textView.setTextColor(Color.WHITE)
                textView.width = textView.maxWidth
                textView.gravity = Gravity.CENTER
                textView.textSize = 20F
                textView.setPadding(5, 5, 5, 5)
                linearRegionalBloc!!.addView(textView)
            }
        }else{
            textTitleRegionalBloc!!.isVisible = false
            linearRegionalBloc!!.isVisible = false
        }
    }

    private fun setCallingCodes(country: Countries?){
        if(country!!.callingCodes.size != 0){
            for (item in 0..country.callingCodes.size - 1) {
                val textView = TextView(this.context)
                if(item != country.callingCodes.size - 1){
                    textView.text = country.callingCodes[item]+", "
                }else{
                    textView.text = country.callingCodes[item]
                }
                textView.setTextColor(Color.WHITE)
                textView.width = textView.maxWidth
                textView.gravity = Gravity.CENTER
                textView.textSize = 20F
                textView.setPadding(5, 5, 5, 5)
                linearCallingCodes!!.addView(textView)
            }
        }else{
            textTitleCalling!!.isVisible = false
            linearCallingCodes!!.isVisible = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOthers(country: Countries?){
        if (country!!.topLevelDomain.size != 0 ){
            textTopLevel!!.text = country.topLevelDomain[0]
        }else{
            textTitleTopLevel!!.isVisible = false
            textTopLevel!!.isVisible = false
        }


        if(!(country.alpha2Code.equals("") && country.alpha3Code.equals(""))){
            if(country.alpha2Code.equals("")){
                textCountryCode!!.text = country.alpha3Code
            }else if(country.alpha3Code.equals("")){
                textCountryCode!!.text = country.alpha2Code
            }else{
                textCountryCode!!.text = country.alpha2Code + ", " + country.alpha3Code
            }
        }else{
            textCodeCountry!!.isVisible = false
            textTitleCountryCodes!!.isVisible = false
        }

    }
}