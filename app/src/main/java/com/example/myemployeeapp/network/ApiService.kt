package com.example.myemployeeapp.network
import com.example.myemployeeapp.model.EmployeeDetailResponse
import com.example.myemployeeapp.model.EmployeeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("employees")
    fun getAllEmployee(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
        @Path("id") id: Int
    ): Call<EmployeeDetailResponse>
}