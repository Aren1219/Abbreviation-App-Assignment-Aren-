package com.example.abbreviationappassignmentaren.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class DefinitionsItemModel(

    @SerializedName("lfs")
    val lfs: List<LfModel>,

    @SerializedName("sf")
    val sf: String

)