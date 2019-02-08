package com.youngtard.localhostapitest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.youngtard.localhostapitest.model.Resp
import com.youngtard.localhostapitest.network.LocalHostAPIService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //I had to declare as top level variable in Activity or declare final so as to access in inner scope?
        val gson = GsonBuilder().setLenient().create()
        val baseURL = "http://10.0.2.2:8080"
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        val localHostService = retrofit.create(LocalHostAPIService::class.java)

        val indexCall = localHostService.returnIndex("/index/")
        val childCall = localHostService.returnChild()

        var responseBodyIndex : Resp? = null
        var responseBodyChild: Resp? = null

        var responseExceptionIndex: Throwable? = null
        var responseExceptionChild: Throwable? = null

        indexCall.enqueue(object: Callback<Resp> {
            override fun onResponse(call: Call<Resp>, response: Response<Resp>) {
//                Toast.makeText(applicationContext, response.body()?.Msg, Toast.LENGTH_SHORT).show()
                responseBodyIndex = response.body()
            }

            override fun onFailure(call: Call<Resp>, t: Throwable) {
//                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                responseExceptionIndex = t
            }
        })

        childCall.enqueue(object: Callback<Resp> {
            override fun onResponse(call: Call<Resp>, response: Response<Resp>) {
                responseBodyChild = response.body()
            }

            override fun onFailure(call: Call<Resp>, t: Throwable) {
                responseExceptionChild = t
            }
        })

        btn_index_handler.setOnClickListener {

//          TODO: or use !=
            if (responseExceptionIndex !== null) {
                Toast.makeText(applicationContext, responseExceptionIndex.toString(), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, responseBodyIndex?.Msg, Toast.LENGTH_SHORT).show()

            }

        }

        btn_child_handler.setOnClickListener {

//            TODO: Try use short if expression
            if (responseExceptionChild !== null) {
                Toast.makeText(applicationContext, responseExceptionChild.toString(), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, responseBodyChild?.Msg, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
