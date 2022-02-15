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


class FavoriteAdapter (private val favouriteList : List<Favorite>, private val context : Context, private val favoriteViewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favoriteName.text = returnLocation(favouriteList.get(position))

        holder.favoriteDelete.setOnClickListener(object : View.OnClickListener{
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
                    favoriteViewModel.deleteFavoriteItem(favouriteList[holder.adapterPosition])
                }
                alertdialog.create()
                alertdialog.show()
            }
        })

        holder.favoriteConstrainLayout.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                openFavorite(favouriteList[holder.adapterPosition])
            }
        })


    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val favoriteName : TextView
            get() = item.findViewById(R.id.favortie_name_tv)

        val favoriteDelete : ImageView
        get() = item.findViewById(R.id.favorite_delete)

        val favoriteConstrainLayout : ConstraintLayout
        get() = item.findViewById(R.id.favorite_constrainl_layout)


    }

    private fun returnLocation(favorite: Favorite) : String{
        var city : String = ""
        var country :String = ""
        val gcd = Geocoder(context, Locale.getDefault())
        val address: List<Address>?
        try {
            address = gcd.getFromLocation(favorite.favourite_latitude , favorite.favourite_longitude ,2)
            city = address[0].locality
            country = address[0].countryName
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return city+", "+country
    }

    private fun openFavorite(favorite: Favorite){
        val app : AppCompatActivity = context as AppCompatActivity
        val home = ShowFavoriteFragment(favorite)
        app.supportFragmentManager.beginTransaction().add(R.id.container,home).addToBackStack("null").commit()
    }


}