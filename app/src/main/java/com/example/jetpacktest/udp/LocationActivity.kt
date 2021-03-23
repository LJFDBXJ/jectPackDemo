package com.example.jetpacktest.udp

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.jetpacktest.R
import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.databinding.LocationLayoutBinding

class LocationActivity(override val layoutId: Int = R.layout.location_layout) :
    LDBaseActivity<LocationLayoutBinding>() {

    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null

    override fun initView() {
        //获取显示地理位置信息的TextView
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        )

        //获取地理位置管理器
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        }
        //获取所有可用的位置提供器
        val providers = locationManager!!.getProviders(true)
        locationProvider = when {
            providers.contains(LocationManager.GPS_PROVIDER) -> {
                //如果是GPS
                LocationManager.GPS_PROVIDER
            }
            providers.contains(LocationManager.NETWORK_PROVIDER) -> {
                //如果是Network
                LocationManager.NETWORK_PROVIDER
            }
            else -> {
                Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show()
                return
            }
        }


        //监视地理位置变化
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //获取Location
            val location = locationManager!!.getLastKnownLocation(
                locationProvider!!
            )
            location?.let { showLocation(it) }
            locationManager!!.requestLocationUpdates(locationProvider!!, 3000, 1f, locationListener)
        }
    }


    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private fun showLocation(location: Location) {
        val locationStr = """
             维度：${location.latitude}
             经度：${location.longitude}
             """.trimIndent()
        binding.positionView.text = locationStr
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    var locationListener: LocationListener = object : LocationListener {
        override fun onStatusChanged(provider: String, status: Int, arg2: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
        override fun onLocationChanged(location: Location) {
            //如果位置发生变化,重新显示
            showLocation(location)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //移除监听器
        locationManager?.removeUpdates(locationListener)
    }


}