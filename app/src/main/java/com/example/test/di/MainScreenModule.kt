package com.example.test.di

import com.example.test.data.CharacterRepositoryImpl
import com.example.test.data.network.CharacterNetworkDataSource
import com.example.test.data.network.CharacterNetworkDataSourceImpl
import com.example.test.data.network.api.CharacterApi
import com.example.test.data.network.mappers.CharacterEntityMapper
import com.example.test.data.network.mappers.CharacterEntityMapperImpl
import com.example.test.domain.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
interface MainScreenModule {

    @Binds
    fun bindsCharacterEntityMapper(implementation: CharacterEntityMapperImpl): CharacterEntityMapper

    @Binds
    fun bindsCharacterNetworkDataSource(implementation: CharacterNetworkDataSourceImpl): CharacterNetworkDataSource

    @Binds
    fun bindsCharacterRepository(implementation: CharacterRepositoryImpl): CharacterRepository

    companion object {
        @Provides
        fun providesJson(): Json = Json { ignoreUnknownKeys = true }

        @Provides
        fun retrofit(json: Json): Retrofit = Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()

        @Provides
        fun providesCharacterApi(retrofit: Retrofit): CharacterApi = retrofit
            .create(CharacterApi::class.java)
    }
}