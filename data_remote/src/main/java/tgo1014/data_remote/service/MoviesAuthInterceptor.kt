package tgo1014.data_remote.service

import okhttp3.Interceptor
import okhttp3.Response

class MoviesAuthInterceptor(private val auth: MoviesAuth) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .header("api_key", auth.getAccessToken())
                .build()
        return chain.proceed(request)
    }

}