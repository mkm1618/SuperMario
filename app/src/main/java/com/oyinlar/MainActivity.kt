package com.oyinlar

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.oyinlar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityMainBinding
    val url:String = "https://poki.com/"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (isNetworkAvailable()) {
            binding.webview.loadUrl(url)
        } else {
            showConnectionError()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showConnectionError() {
        binding.webview.visibility = View.GONE
        binding.retryButton.visibility = View.VISIBLE
        binding.retryButton.setOnClickListener {
            if (isNetworkAvailable()) {
                binding.webview.loadUrl(url)
                binding.webview.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
            } else {
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}