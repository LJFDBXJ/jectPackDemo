package com.example.jetpacktest.udp

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jetpacktest.R
import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.databinding.ActivityRecieyBinding
import java.util.*


class ReceiveActivity(override val layoutId: Int = R.layout.activity_reciey) :
    LDBaseActivity<ActivityRecieyBinding>() {

    private fun initListener() {
        binding.recive = this
    }

    fun onClickView(view: View) {
        when (view) {
            binding.btnClear -> {
                binding.tvReceive.text = ""
            }
            binding.btnConnect -> {
                val ip = binding.etIp.text.toString()
                val port = binding.etPort.text.toString()
                if (TextUtils.isEmpty(ip)) {
                    Toast.makeText(this@ReceiveActivity, "IP地址为空", Toast.LENGTH_SHORT).show()
                }
                if (TextUtils.isEmpty(port)) {
                    Toast.makeText(this@ReceiveActivity, "端口号为空", Toast.LENGTH_SHORT).show()
                }
                connect(ip, port.toInt())
            }
            binding.btnDisconnect -> {
                //socket disconnect
                disconnect()
            }
            binding.btnSend -> {
                if (UdpClient.instance!!.isConnect) {
                    val data: ByteArray = binding.etSend.text.toString().toByteArray()
                    send(data)
                } else {
                    Toast.makeText(this, "尚未连接，请连接Socket", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**
     * socket connect
     */
    private fun connect(ip: String, port: Int) {
        UdpClient.instance?.connect(ip, port)
    }

    /**
     * socket disconnect
     */
    private fun disconnect() {
        UdpClient.instance?.disconnect()
        binding.tvState.text = "未连接"
    }

    /**
     * socket send
     */
    private fun send(data: ByteArray) {
        val ip: String = binding.etIp.text.toString()
        val port: String = binding.etPort.text.toString()
        UdpClient.instance?.sendByteCmd(data, 1001)
    }

    val TAG_log = "analyze"

    /**
     * socket data receive
     * data(byte[]) analyze
     */
    private val dataReceiveListener: UdpClient.OnDataReceiveListener = object :
        UdpClient.OnDataReceiveListener {
        override fun onConnectSuccess() {
            Log.i(TAG_log, "onDataReceive connect success")
            binding.tvState.text = "已连接"
        }

        override fun onConnectFail() {
            Log.e(TAG_log, "onDataReceive connect fail")
            binding.tvState.text = "未连接"
        }

        override fun onDataReceive(buffer: ByteArray?, size: Int, requestCode: Int) {
            //获取有效长度的数据
            buffer?.let {
                val data = ByteArray(size)
                System.arraycopy(buffer, 0, data, 0, size)
                val oxValue = String(data)
                Log.i(TAG_log, "onDataReceive requestCode = $requestCode, content = $oxValue")
                binding.tvReceive.text = binding.tvReceive.text.toString() + oxValue + "\n"
            }
        }
    }


    override fun onDestroy() {
        UdpClient.instance?.disconnect()
        super.onDestroy()
    }

    override fun initView() {
        /**
         * socket data receive
         */
        UdpClient.instance?.onDataReceiveListener = dataReceiveListener
        initListener()
    }

}