package com.shekoo.iweather.ui.alert

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.iweather.databinding.FragmentAlertBinding
import com.shekoo.iweather.databinding.FragmentSettingBinding
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.ui.alert.AlertViewModel
import com.shekoo.iweather.ui.favorite.FavoriteAdapter
import com.shekoo.iweather.ui.favorite.MapsActivity

class AlertFragment : Fragment() {

    private var _binding: FragmentAlertBinding? = null
    private lateinit var alertViewModel: AlertViewModel

    private lateinit var alertAdapter: AlertAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var alertList: List<MyAlert>


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentAlertBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val alertViewModelFactory = AlertViewModelFactory(requireContext())
        alertViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)

        val layoutManagerForFavorite = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView = binding.alertRecyclerView
        recyclerView.layoutManager = layoutManagerForFavorite

        binding.fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            val intent = Intent(context, AddAlertActivity::class.java)
            startActivity(intent)
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        alertViewModel.getAllAlert().observe(viewLifecycleOwner, Observer {
            alertList = it
            alertAdapter = AlertAdapter(alertList, requireContext())
            recyclerView.adapter = alertAdapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}