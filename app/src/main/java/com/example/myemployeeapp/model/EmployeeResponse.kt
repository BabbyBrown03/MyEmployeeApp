package com.example.myemployeeapp.model

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("data")
    val data: List<Employee>
)
