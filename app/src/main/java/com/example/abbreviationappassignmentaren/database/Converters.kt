package com.example.abbreviationappassignmentaren.database

import androidx.room.TypeConverter
import com.example.abbreviationappassignmentaren.models.LfModel
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromLfs(lfs: List<LfModel>): String{
        var str = ""
        for (i in lfs.indices){
            str += "${lfs[i].lf};"
        }
        return str
    }

    @TypeConverter
    fun toLfModel(lfs: String): List<LfModel> {
        var list = mutableListOf<LfModel>()
        var str = ""
        for (i in lfs.indices){
            if (!lfs[i].equals(";")){
                str += lfs[i]
            } else {
                list.add(LfModel(str))
                str = ""
            }
        }
        return list
    }

//    var gson = Gson()
//
//    @TypeConverter
//    fun abbreviationsToString(abbreviations: Abbreviations): String = gson.toJson(abbreviations)
//
//    @TypeConverter
//    fun stringToAbbreviations(data: String): Abbreviations {
//        val listType = object : TypeToken<Abbreviations>() {}.type
//        return gson.fromJson(data, listType)
//    }
}