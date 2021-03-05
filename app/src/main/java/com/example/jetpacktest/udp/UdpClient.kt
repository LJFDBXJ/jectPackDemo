package com.example.jetpacktest.udp

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*


class UdpClient private constructor() {
    var TAG_log = "Socket"
    private var mSocket: DatagramSocket? = null
    private var sendPacket: DatagramPacket? = null//发送
    private var receivePacket: DatagramPacket? = null//接受

    //  private OutputStream mOutputStream;
    //  private InputStream mInputStream;
    private var mSocketThread: SocketThread? = null
    private var isStop = false //thread flag

    /**
     * 128 - 数据按照最长接收，一次性
     */
    private inner class SocketThread(private val ip: String, private val port: Int) : Thread() {
        override fun run() {
            Log.d(TAG_log, "SocketThread start ")
            super.run()

            //connect ...
            try {
                mSocket?.close()
                val ipAddress = InetAddress.getByName(ip)
                mSocket = DatagramSocket()
                mSocket?.connect(ipAddress, port) //连接

                //设置timeout
                //mSocket.setSoTimeout(3000);
                Log.d(TAG_log, "udp connect = $isConnect")
                if (isConnect) {
                    isStop = false
                    uiHandler.sendEmptyMessage(1)
                } else {
                    uiHandler.sendEmptyMessage(-1)
                    Log.e(TAG_log, "SocketThread connect fail")
                    return
                }
            } catch (e: IOException) {
                uiHandler.sendEmptyMessage(-1)
                Log.e(TAG_log, "SocketThread connect io exception = " + e.message)
                e.printStackTrace()
                return
            }
            Log.d(TAG_log, "SocketThread connect over ")

            //发送一次，否则不发送则收不到，不知道为啥。。。
            sendByteCmd(byteArrayOf(0), -1) //send once

            //read ...
            while (isConnect && !isStop && !isInterrupted) {
                var size: Int
                try {
                    val preBuffer = ByteArray(4 * 1024) //预存buffer
                    receivePacket = DatagramPacket(preBuffer, preBuffer.size)
                    mSocket!!.receive(receivePacket)
                    if (receivePacket!!.data == null) return
                    size = receivePacket!!.length //此为获取后的有效长度，一次最多读64k，预存小的话可能分包
                    Log.d(
                        TAG_log,
                        "pre data size = " + receivePacket!!.data.size + ", value data size = " + size
                    )
                    val dataBuffer = preBuffer.copyOf(size)
                    if (size > 0) {
                        val msg = Message()
                        msg.what = 100
                        val bundle = Bundle()
                        bundle.putByteArray("data", dataBuffer)
                        bundle.putInt("size", size)
                        bundle.putInt("requestCode", requestCode)
                        msg.data = bundle
                        uiHandler.sendMessage(msg)
                    }
                    Log.i(TAG_log, "SocketThread read listening")
                    //Thread.sleep(100);//log eof
                } catch (e: IOException) {
                    uiHandler.sendEmptyMessage(-1)
                    Log.e(TAG_log, "SocketThread read io exception = " + e.message)
                    e.printStackTrace()
                    return
                }
            }
        }
    }

    //==============================socket connect============================
    private var ip: String? = null
    private var port = 0

    /**
     * connect socket in thread
     * Exception : android.os.NetworkOnMainThreadException
     */
    fun connect(ip: String, port: Int) {
        this.ip = ip
        this.port = port
        mSocketThread = SocketThread(ip, port)
        mSocketThread!!.start()
    }

    /**
     * socket is connect
     */
    val isConnect: Boolean
        get() {
            var flag = false
            if (mSocket != null) {
                flag = mSocket!!.isConnected
            }
            return flag
        }

    /**
     * socket disconnect
     */
    fun disconnect() {
        isStop = true
        mSocket?.close()
        mSocket = null
        mSocketThread?.interrupt() //not intime destory thread,so need a flag
    }

    /**
     * send byte[] cmd
     * Exception : android.os.NetworkOnMainThreadException
     */
    fun sendByteCmd(mBuffer: ByteArray, requestCode: Int) {
        this.requestCode = requestCode
        Thread {
            try {
                val ipAddress = InetAddress.getByName(ip)
                sendPacket = DatagramPacket(mBuffer, mBuffer.size, ipAddress, port)
                mSocket!!.send(sendPacket)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    var uiHandler: Handler = Handler { msg ->
        when (msg.what) {
            -1 -> if (null != onDataReceiveListener) {
                onDataReceiveListener!!.onConnectFail()
                disconnect()
            }
            1 -> if (null != onDataReceiveListener) {
                onDataReceiveListener!!.onConnectSuccess()
            }
            100 -> {
                val bundle = msg.data
                val buffer = bundle.getByteArray("data")
                val size = bundle.getInt("size")
                val mequestCode = bundle.getInt("requestCode")
                if (null != onDataReceiveListener) {
                    onDataReceiveListener!!.onDataReceive(buffer, size, mequestCode)
                }
            }
        }
        return@Handler true
    }

    /**
     * socket response data listener
     */
    var onDataReceiveListener: OnDataReceiveListener? = null
    private var requestCode = -1

    interface OnDataReceiveListener {
        fun onConnectSuccess()
        fun onConnectFail()
        fun onDataReceive(buffer: ByteArray?, size: Int, requestCode: Int)
    }


    companion object {
        /**
         * single instance UdpClient
         */
        private var mSocketClient: UdpClient? = null
        val instance: UdpClient?
            get() {
                if (mSocketClient == null) {
                    synchronized(UdpClient::class.java) { mSocketClient = UdpClient() }
                }
                return mSocketClient
            }
    }
}