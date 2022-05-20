package uz.kozimjon.taskapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.kozimjon.taskapp.utils.Constants

object RetrofitClient {

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}