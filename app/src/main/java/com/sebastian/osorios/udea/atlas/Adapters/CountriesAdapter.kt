package com.sebastian.osorios.udea.atlas.Adapters

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import com.sebastian.osorios.udea.atlas.R

class CountriesAdapter(activity: FragmentActivity?, countries: List<Countries>, fragment: FragmentManager ) : BaseAdapter() {

    var activity : Activity? = activity
    var countries: List<Countries>? = countries
    var fragment : FragmentManager? = fragment
    var layoutInflater : LayoutInflater? = activity?.layoutInflater
    private var loadedImage: Bitmap? = null
    private val imageView: ImageView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder : ViewHolder =
            ViewHolder()
        var view: View? = null
        if(convertView == null){
            view = layoutInflater!!.inflate(R.layout.list_view_item,parent,false)
            viewHolder.textView = view.findViewById(R.id.text_view)
            viewHolder.textCapital = view.findViewById(R.id.text_view_capital)
            viewHolder.imageViewArrow = view.findViewById(R.id.imageViewArrow)
            viewHolder.imageViewFlag = view.findViewById(R.id.imageViewFlag)
            view.setTag(viewHolder)
            return view
        }else{
            viewHolder = convertView.getTag() as ViewHolder
        }
        var country : Countries? = countries?.get(position)
        var name : String? = country?.translations?.espanish
        if(!name.equals(null)){
            name = country?.translations?.espanish
        }else {
            name = country?.name
        }
        if (name != null && name.length > 30) {
            name = changeCaracter(name)
        }

        viewHolder.textView?.setText(name)
        viewHolder.textCapital?.setText(country?.capital)
        viewHolder.imageViewArrow?.setImageResource(R.drawable.sharp_keyboard_arrow_right_white_18)
        Glide.with(this.activity).load(R.drawable.images).into(viewHolder.imageViewFlag)

        return convertView
    }

    override fun getItem(position: Int): Countries? {
        return countries?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return countries?.size!!
    }

    class ViewHolder{
        var textView : TextView? = null
        var imageViewArrow : ImageView? = null
        var imageViewFlag : ImageView? = null
        var textCapital : TextView? = null
    }

    fun downloadFile(imageHttpAddress: String?): Bitmap? {
        var imageUrl: URL? = null
        try {
            imageUrl = URL(imageHttpAddress)
            val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
            conn.connect()
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream())
            imageView!!.setImageBitmap(loadedImage)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return loadedImage
    }

    private fun changeCaracter(name : String) : String {
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
