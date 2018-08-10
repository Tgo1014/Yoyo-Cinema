package tgo1014.yoyocinema.data.network

interface ResultListener <T>{
    fun onSucess(data: T)
    fun onFailure(message: String)
}