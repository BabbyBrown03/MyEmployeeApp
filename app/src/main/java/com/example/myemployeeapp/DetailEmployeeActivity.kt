package com.example.myemployeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myemployeeapp.databinding.ActivityDataEmployeeBinding
import com.example.myemployeeapp.databinding.ActivityMainBinding
import com.example.myemployeeapp.model.EmployeeDetailResponse
import com.example.myemployeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataEmployeeBinding
    val client = ApiClient.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

        }

        val employeeId = intent.getIntExtra("EXTRA_ID", -1)
        if(employeeId == -1){
            Toast.makeText(
                this,
                "ID tidak valid",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        getEmployeeDetail(employeeId)

    }

    fun getEmployeeDetail(id: Int) {
        val response = client.getEmployeeDetail(id)

        response.enqueue(object : Callback<EmployeeDetailResponse> {
            override fun onResponse(
                p0: Call<EmployeeDetailResponse?>,
                response: Response<EmployeeDetailResponse?>
            ) {
                if(!response.isSuccessful){
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val body = response.body()
                val employee = body?.data

                binding.txtName.text = employee?.name.toString()
                binding.txtAge.text = employee?.age.toString()
                binding.txtSalary.text = employee?.salary.toString()
            }

            override fun onFailure(
                p0: Call<EmployeeDetailResponse?>,
                p1: Throwable
            ) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}