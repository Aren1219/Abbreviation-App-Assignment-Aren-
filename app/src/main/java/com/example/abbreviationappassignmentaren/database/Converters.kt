package com.example.abbreviationappassignmentaren.database

import androidx.room.TypeConverter
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import com.example.abbreviationappassignmentaren.models.LfModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    var gson = Gson()

    @TypeConverter
    fun abbreviationsToString(abbreviations: DefinitionsModel): String = gson.toJson(abbreviations)

    @TypeConverter
    fun stringToAbbreviations(data: String): DefinitionsModel {
        val listType = object : TypeToken<DefinitionsModel>() {}.type
        return gson.fromJson(data, listType)
    }
}