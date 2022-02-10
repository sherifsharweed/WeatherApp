package com.shekoo.iweather.ui.favorite

import android.content.Intent
import android.net.DnsResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.shekoo.iweather.MapsActivity
import com.shekoo.iweather.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            //val uri = "http://maps.google.com/maps?saddr=" + "30.4646308" + "," + "30.9349903"
            /*val uri = "geo:"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            //intent.setPackage("com.google.android.apps.maps");
            startActivity(intent)*/
            val intent = Intent(context,MapsActivity::class.java)
            startActivity(intent)

        }

        val textView: TextView = binding.textGallery
        favoriteViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}