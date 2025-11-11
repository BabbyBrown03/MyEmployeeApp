package com.example.myemployeeapp.model

import com.google.gson.annotations.SerializedName

data class EmployeeDetailResponse(
    @SerializedName("data")
    val data: Employee
)
