package com.example.abbreviationappassignmentaren.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "Definitions",
)
data class DefinitionsItemModel(

    @SerializedName("lfs")
    val lfs: List<LfModel>,

    @PrimaryKey
    @SerializedName("sf")
    val sf: String

)