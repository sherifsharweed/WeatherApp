package com.shekoo.iweather.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shekoo.iweather.databinding.FragmentAlertBinding
import com.shekoo.iweather.databinding.FragmentSettingBinding
import com.shekoo.iweather.ui.alert.AlertViewModel

class AlertFragment : Fragment() {

    private var _binding: FragmentAlertBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(AlertViewModel::class.java)

        _binding = FragmentAlertBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}