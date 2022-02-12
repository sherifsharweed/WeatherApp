package com.shekoo.iweather.ui.favorite

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.iweather.R
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.repo.FavoriteRepo
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*


class FavoriteAdapter (private val favouriteList : List<Favorite>, private val context : Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favorite_name_tv.text = returnLocation(favouriteList.get(position))

        holder.favorite_delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val alertdialog = AlertDialog.Builder(context)
                alertdialog.setCancelable(false) // that make the dialog cant cancelled until u click inside the dialog itself
                alertdialog.setTitle("Delete")
                alertdialog.setMessage("Do you want to delete this place to favorites?")
                alertdialog.setPositiveButton("Cancel") { dialogInterface, i ->
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                    dialogInterface.cancel()
                }
                alertdialog.setNegativeButton("Yes") { dialogInterface, i ->
                    Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show()
                    deleteFavourite(favouriteList.get(holder.adapterPosition))
                }
                alertdialog.create()
                alertdialog.show()
            }
        })

        holder.favorite_constrain_layout.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                openFavortie(favouriteList.get(holder.adapterPosition))
            }
        })


    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val favorite_name_tv : TextView
            get() = item.findViewById(R.id.favortie_name_tv)

        val favorite_delete : ImageView
        get() = item.findViewById(R.id.favorite_delete)

        val favorite_constrain_layout : ConstraintLayout
        get() = item.findViewById(R.id.favorite_constrainl_layout)


    }

    fun returnLocation(favorite: Favorite) : String{
        var city : String = ""
        var country :String = ""
        val gcd = Geocoder(context, Locale.getDefault())
        var address: List<Address>?
        try {
            address = gcd.getFromLocation(favorite.favourite_latitude , favorite.favourite_longitude ,2)
            city = address[0].locality
            country = address[0].countryName
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return city+", "+country
    }

    fun deleteFavourite(favorite: Favorite) {
         lateinit var repo: FavoriteRepo

         GlobalScope.launch {
             repo = FavoriteRepo(WeatherDataBase.getInstance(context).getWeatherDao())
             repo.deleteFavoriteItem(favorite)
         }
     }

    fun openFavortie(favorite: Favorite){
        var app : AppCompatActivity = context as AppCompatActivity
        var home = ShowFavoriteFragment(favorite)
        app.supportFragmentManager.beginTransaction().add(R.id.container,home).addToBackStack(null).commit()

    }


}