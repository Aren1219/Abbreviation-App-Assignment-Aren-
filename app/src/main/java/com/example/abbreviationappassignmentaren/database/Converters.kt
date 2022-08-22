package com.example.abbreviationappassignmentaren.database

import androidx.room.TypeConverter
import com.example.abbreviationappassignmentaren.models.LfModel

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
}