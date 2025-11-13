package com.example.myemployeeapp.network
import com.example.myemployeeapp.model.EmployeeDetailResponse
import com.example.myemployeeapp.model.EmployeeRequest
import com.example.myemployeeapp.model.EmployeeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("employees")
    fun getAllEmployee(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
        @Path("id") id: Int
    ): Call<EmployeeDetailResponse>

    @POST("create")
    fun createEmployee(
        @Body employee: EmployeeRequest
    ): Call<EmployeeDetailResponse>

    @PATCH("update/{id}")
    fun updateEmployee(
        @Path("id") id: Int,
        @Body employee: EmployeeRequest
    ): Call<EmployeeDetailResponse>

    @DELETE("delete/{id}")
    fun deleteEmployee(
        @Path("id") id: Int
    ): Call<EmployeeDetailResponse>
}