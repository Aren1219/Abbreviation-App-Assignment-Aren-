package com.example.abbreviationappassignmentaren.api

import com.example.abbreviationappassignmentaren.api.ApiReferences.END_POINT
import com.example.abbreviationappassignmentaren.models.DefinitionsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

    @GET(END_POINT)
    suspend fun searchWithSf(
        @Query("sf") searchTerm: String
    ) : Response<DefinitionsModel>

//    @GET("/dictionary.py?")
//    fun searchWithLf(
//        @Query("lf") searchTerm: String
//    ) : Response<Definitions>

}