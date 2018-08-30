package tgo1014.yoyocinema.old.data.network

object RestClient {

//    val moviesService: MoviesService
//
//    init {
//        val okHttpBuilder = OkHttpClient.Builder()
//
//        //log requests if its a debug project
//        if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            okHttpBuilder.addInterceptor(loggingInterceptor)
//        }
//
//        interceptAndAddApiKey(okHttpBuilder)
//
//        val client = okHttpBuilder.build()
//
//        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//
//        moviesService = retrofit.create(MoviesService::class.java)
//    }
//
//    /**
//     * Ads the API needed for all requests
//     */
//    private fun interceptAndAddApiKey(client: OkHttpClient.Builder) {
//        client.addInterceptor { chain ->
//            val original = chain.request()
//            val originalHttpUrl = original.url()
//
//            val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("api_key", BuildConfig.API_KEY)
//                    .build()
//
//            // Request customization: add request headers
//            val requestBuilder = original.newBuilder()
//                    .url(url)
//
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }
//    }

}