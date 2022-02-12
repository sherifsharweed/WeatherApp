package com.shekoo.iweather.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.iweather.databinding.FragmentFavoriteBinding
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.ui.TAG


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteList: List<Favorite>

    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val favoriteViewModelFactory = FavoriteViewModelFactory(context?.applicationContext!!)
        favoriteViewModel = ViewModelProvider(this,favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManagerForFavorite = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView = binding.favoriteRecyclerview
        recyclerView.layoutManager = layoutManagerForFavorite

        binding.fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner, Observer {
            favoriteList = it
            favoriteAdapter = FavoriteAdapter(favoriteList, requireContext())
            recyclerView.adapter = favoriteAdapter

            Log.i(TAG, "observeViewModel: "+it.size)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}