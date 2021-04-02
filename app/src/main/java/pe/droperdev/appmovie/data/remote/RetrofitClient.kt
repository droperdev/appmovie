package pe.droperdev.appmovie.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.droperdev.appmovie.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if(BuildConfig.TEST) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}