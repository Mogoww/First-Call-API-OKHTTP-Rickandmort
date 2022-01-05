package com.example.firstcallapiokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firstcallapiokhttp.databinding.ActivityMainBinding
import okhttp3.*
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val requestFiles = registerForActivityResult(ActivityResultContracts.RequestPermission(),{
            if(it){
                Toast.makeText(applicationContext,"Permission granted", Toast.LENGTH_SHORT).show()
                run()
            }else{
                Toast.makeText(applicationContext,"Permission no granted", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnFilesPermission.setOnClickListener({
            requestFiles.launch(android.Manifest.permission.INTERNET)
        })



    }

    private val client = OkHttpClient()

    fun run() {
        val request = Request.Builder()
            //.url("https://rickandmortyapi.com/api/character/2")
            //.url("https://rickandmortyapi.com/api/character/?name=rick&status=alive")
            .url("https://rickandmortyapi.com/api/character")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")


                    val oui = response.body!!.string()
                    try {
                        val jsonObjects = JSONObject(oui)
                        Log.i("JSON_ALL", jsonObjects.toString() )



                        /*                       val jsonArray = jsonObjects.getJSONArray("results")

                                             for (position in 0 until jsonArray.length()) {
                                                  val row = jsonArray.getJSONObject(position)

                                                  val name = row.getString("name")
                                                  val status = row.getString("status")
                                                  val id = row.getInt("id")
                                                  Log.i("JSON_ONE", row.toString() )

                                              }*/



                    } catch (err: JSONException) {
                        Log.d("Error", err.toString())
                    }


                }
            }
        })
    }


}