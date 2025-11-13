package com.example.myemployeeapp.model

import com.google.gson.annotations.SerializedName

data class EmployeeRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("salary")
    val salary: Int,

    @SerializedName("age")
    val age: Int
)