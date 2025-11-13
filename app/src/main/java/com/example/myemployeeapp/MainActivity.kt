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
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding.btnCreate.setOnClickListener {
            createEmployee()
        }
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

                binding.lvUsers.adapter = listAdapter

                binding.lvUsers.onItemClickListener = AdapterView.OnItemClickListener{_, _, position, _ ->
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

    private fun createEmployee() {
        val newEmployee = com.example.myemployeeapp.model.EmployeeRequest(
            name = "Karyawan Baru",
            salary = 5000000,
            age = 25
        )

        apiClient.createEmployee(newEmployee).enqueue(object :
            Callback<com.example.myemployeeapp.model.EmployeeDetailResponse> {
            override fun onResponse(
                call: Call<com.example.myemployeeapp.model.EmployeeDetailResponse>,
                response: Response<com.example.myemployeeapp.model.EmployeeDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    Toast.makeText(
                        this@MainActivity,
                        "Employee ${data?.name} berhasil dibuat",
                        Toast.LENGTH_SHORT
                    ).show()
                    getAllEmployee() // refresh list
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal menambahkan data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<com.example.myemployeeapp.model.EmployeeDetailResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}