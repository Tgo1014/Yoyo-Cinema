/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tgo1014.yoyocinema.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
                return@addSource
            }
            result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }

        createCall().enqueue(object : Callback<RequestType> {

            override fun onResponse(call: Call<RequestType>, response: Response<RequestType>) {
                result.removeSource(dbSource)
                saveResultAndReInit(response.body())
            }

            override fun onFailure(call: Call<RequestType>, t: Throwable) {
                onFetchFailed()
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData -> result.setValue(Resource.error(t.message, newData)) }
            }

        })
    }

    private fun saveResultAndReInit(response: RequestType?) = runBlocking {
        async { response?.let { saveCallResult(it) } }.await()
        result.addSource(loadFromDb()) { newData -> result.setValue(Resource.success(newData)) }
    }

    protected abstract fun saveCallResult(item: RequestType)

    open fun shouldFetch(data: ResultType?) = true

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): Call<RequestType>

    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}