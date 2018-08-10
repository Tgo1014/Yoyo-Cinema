package tgo1014.yoyocinema.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tgo1014.yoyocinema.BuildConfig
import tgo1014.yoyocinema.data.network.services.MoviesService

object RestClient {

    val moviesService: MoviesService

    init {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor)
        }

        interceptAndAddApiKey(okHttpBuilder)

        val client = okHttpBuilder.build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        moviesService = retrofit.create(MoviesService::class.java)
    }

    private fun interceptAndAddApiKey(client: OkHttpClient.Builder) {
        client.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

}