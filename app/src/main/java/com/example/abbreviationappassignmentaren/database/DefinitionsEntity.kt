package com.example.abbreviationappassignmentaren.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.abbreviationappassignmentaren.models.DefinitionsItemModel
import com.example.abbreviationappassignmentaren.models.DefinitionsModel

@Entity(tableName = "Definitions")
class DefinitionsEntity(val definitionsModel: DefinitionsModel) {
    @PrimaryKey
    var sf: String = definitionsModel[0].sf
    var lf: String = definitionsModel[0].lfs.toString()
}