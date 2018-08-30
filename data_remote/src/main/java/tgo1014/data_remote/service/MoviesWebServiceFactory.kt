package tgo1014.data_remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MoviesWebServiceFactory {

    fun makeMovieWebService(auth: MoviesAuth, isDebug: Boolean): MoviesService {
        val okHttpClient = makeOkHttpClient(auth, makeLogginInterceptor(isDebug))
        return makeMovieWebService(okHttpClient)
    }

    private fun makeMovieWebService(okHttpClient: OkHttpClient): MoviesService {
        val retrofit = Retrofit.Builder()
                .baseUrl(MoviesService.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(MoviesService::class.java)
    }

    private fun makeOkHttpClient(auth: MoviesAuth, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(MoviesAuthInterceptor(auth))
                .build()
    }

    private fun makeLogginInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = when {
            isDebug -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
}