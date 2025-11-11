package com.example.myemployeeapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myemployeeapp.databinding.ActivityMainBinding
import com.example.myemployeeapp.model.EmployeeResponse
import com.example.myemployeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        with(binding) {
            getAllEmployee()
        }

    }

    private fun getAllEmployee(){
        val response = apiClient.getAllEmployee()

        response.enqueue(object : Callback<EmployeeResponse> {
            override fun onResponse(
                call: Call<EmployeeResponse>,
                response: Response<EmployeeResponse>
            ) {

                if (!response.isSuccessful){
                    Toast.makeText(
                        this@MainActivity,
                        "SUKSES",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val body = response.body()
                val employee = body?.data.orEmpty()

                if (employee.isEmpty()){
                    Toast.makeText(
                        this@MainActivity,
                        "Data Employee Kosong",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                val names = employee.map {it.name}

                val listAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    names
                )

                binding.lvNama.adapter = listAdapter

                binding.lvNama.onItemClickListener = AdapterView.OnItemClickListener{_, _, position, _ ->
                    val id = employee[position].id
                    val intent = Intent(this@MainActivity, DetailEmployeeActivity::class.java)
                    intent.putExtra("EXTRA_ID", id)
                    startActivity(intent)}

            }

            override fun onFailure(
                p0: Call<EmployeeResponse?>,
                p1: Throwable
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Koneksi error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}