package com.dicoding.mystory.di

import android.content.Context
import com.dicoding.mystory.api.ApiConfig
import com.dicoding.mystory.database.StoryDatabase
import com.dicoding.mystory.model.UserPreference
import com.dicoding.mystory.repository.StoryRepository
import com.dicoding.mystory.util.PreferenceProvider
import kotlinx.coroutines.flow.first
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module

object Injection {

     fun provideRepository(context: Context): StoryRepository {
         var token:String=""
        val preferenceProvider = PreferenceProvider(get())
         if (preferenceProvider.getToken() != null) {
             token = preferenceProvider.getToken().toString()

         }



        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiServiceBearer(token)
        return StoryRepository(database, apiService)
    }
}


