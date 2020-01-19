package com.example.myapplication

import ClientSocket
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.system.Os.socket
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    val STATUS_PHRASE = 200
    lateinit var clientSocket: ClientSocket
    lateinit var handler: Handler
    lateinit var responseHeaders: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSend.setOnClickListener { makeRequest() }


        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    STATUS_PHRASE -> {
                        txtViewHeader.setText(responseHeaders)

//                        val imageByteArray = Base64.decode(byteArray, Base64.DEFAULT)
//                        Glide.with(this@MainActivity).load(imageByteArray).into(image)

                    }
                }
            }
        }

    }

    fun makeRequest() {
        val host = "allelectronics.am"
        val resourceLoc =
            "/media/image/fc/16/d2/4e413123730b19d1bd9b20e388ef570f_200NY6dgJKiO7yET_600x600.jpg"

        val thread = Thread(Runnable {
            try {
                clientSocket = ClientSocket(host, resourceLoc)
                clientSocket.makeRequest()
                responseHeaders = clientSocket.getData()
                handler.sendEmptyMessage(200)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()
    }
}








