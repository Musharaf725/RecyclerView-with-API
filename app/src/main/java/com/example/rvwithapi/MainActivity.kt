package com.example.rvwithapi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView= findViewById(R.id.Rv)
        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData= retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                // if your api call is success, then do whatever you want to do with your data
                var responseBody= p1.body()
                val productList= responseBody?.products

                if(productList != null) {
                    myAdapter = MyAdapter(this@MainActivity, productList)
                    recyclerView.adapter = myAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }

            }

            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                // if api call fails, then display a message why it fails
                Log.d("Main Activity", "onFaliure: " + p1.message)
            }
        })
    }
}