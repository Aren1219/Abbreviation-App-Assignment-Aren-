package com.example.abbreviationappassignmentaren.models


import com.google.gson.annotations.SerializedName

data class LfModel(
    @SerializedName("freq")
    val freq: Int,
    @SerializedName("lf")
    val lf: String,
    @SerializedName("since")
    val since: Int,
    @SerializedName("vars")
    val vars: List<VarModel>
)