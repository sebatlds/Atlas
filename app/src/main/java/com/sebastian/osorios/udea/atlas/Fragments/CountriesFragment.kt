package com.sebastian.osorios.udea.atlas.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sebastian.osorios.udea.atlas.Adapters.ReciclerViewAdapter
import com.sebastian.osorios.udea.atlas.Interfaces.ApiServices
import com.sebastian.osorios.udea.atlas.Models.Countries.Countries
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sebastian.osorios.udea.atlas.R


class CountriesFragment : Fragment(){

    var constants : Constants = Constants()
    var commonFunctions : CommonFunctions = CommonFunctions()
    var mcontext : Context? =null
    var listCountries : List<Countries>? = null
    lateinit var progressDialog : ProgressDialog
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var listReciclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countries, container, false)
        onProgres()
        listReciclerView = root.findViewById<RecyclerView>(R.id.list_coutries)
        getCountries(this.context, root)
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener (object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                listReciclerView.adapter = null
                onProgres()
                getCountries(activity!!.applicationContext, root)
                swipeRefreshLayout.isRefreshing = false
            }

        })
        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
       mcontext = context
    }

    fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    private fun getCountries(context: Context?, root: View) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiServices::class.java)
        service.getCountries().enqueue(object: Callback<List<Countries>>{
            override fun onResponse(call: Call<List<Countries>>, response: Response<List<Countries>>) {
                listCountries = response.body()
                if (listCountries != null) {
                    progressDialog.dismiss()
                    listReciclerView.setHasFixedSize(true)
                    listReciclerView.layoutManager = LinearLayoutManager(
                        activity?.applicationContext,RecyclerView.VERTICAL,false
                    )

                    val countriesAdapter = ReciclerViewAdapter(
                        activity!!.applicationContext, listCountries!!)

                    listReciclerView.adapter = countriesAdapter
                }
            }
            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                progressDialog.dismiss()
                val alert = AlertDialog.Builder(context)
                alert.setTitle(constants.ERROR_TITLE)
                alert.setMessage(commonFunctions.getErrorMessage("501"))
                alert.setPositiveButton(
                    "Confirmar", null
                )
                alert.show()
            }
        })

    }

    private fun onProgres(){
        progressDialog  = ProgressDialog(context)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

}



