package tgo1014.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class MoviesAuthInterceptor(private val auth: MoviesAuth) : Interceptor {

    /**
     * Ads the API needed for all requests
     */
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, auth.getAccessToken())
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "api_key"
    }
}