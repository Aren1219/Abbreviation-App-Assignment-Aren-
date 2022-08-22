package com.example.abbreviationappassignmentaren.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.abbreviationappassignmentaren.api.ApiDetails
import com.example.abbreviationappassignmentaren.api.ApiReferences.BASE_URL
import com.example.abbreviationappassignmentaren.database.DefinitionsDao
import com.example.abbreviationappassignmentaren.database.DefinitionsDatabase
import com.example.abbreviationappassignmentaren.repositories.Repository
import com.example.abbreviationappassignmentaren.repositories.RepositoryImp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApi() : ApiDetails = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiDetails::class.java)

    @Provides
    @Singleton
    fun provideDao(database: DefinitionsDatabase): DefinitionsDao = database.getDefinitionsDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context):DefinitionsDatabase =
        Room.databaseBuilder(
            context,
            DefinitionsDatabase::class.java,
            "Definition.db.db"
        ).build()

    @Provides
    @Singleton
    fun provideRepository(
        apiDetails: ApiDetails,
        database: DefinitionsDatabase
    ):Repository = RepositoryImp(apiDetails,database)
}