package tgo1014.yoyocinema.data.network

interface ResultListener <T>{
    fun onSuccess(data: T)
    fun onFailure(message: String)
}