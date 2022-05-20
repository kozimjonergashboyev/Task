package uz.kozimjon.taskapp.api

import retrofit2.http.*
import uz.kozimjon.taskapp.model.RickAndMortyList

interface Api {

    @GET("character")
    suspend fun getDataFromAPI(@Query("page") query: Int): RickAndMortyList

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("name") characterName: String,
        @Query("page") pageIndex: Int
    ): RickAndMortyList
}