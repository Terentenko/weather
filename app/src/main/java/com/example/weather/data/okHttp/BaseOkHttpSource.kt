package com.example.weather.data.okHttp

import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume


open class BaseOkHttpSource(config: OkHttpConfig) {
    val gson: Gson = config.gson
    val client: OkHttpClient = config.client
    suspend fun Call.suspendEnqueue(): Response {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { cancel() }

            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(Exception(e.message))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        continuation.resume(response)
                    } else {
                        verificationBody(response.body)
                        try {
                            val map = gson.fromJson(response.body!!.string(), Map::class.java)
                            continuation.resumeWithException(
                                Exception(map["error"].toString())
                            )
                        } catch (e: Exception) {
                            continuation.resumeWithException(
                                Exception(
                                    response.message
                                )
                            )
                        }
                    }
                }
            })
        }
    }


    inline fun <reified T> Response.parseJsonResponse(): T {
        verificationBody(this.body)
        try {
            val body = this.body!!.string()
            return gson.fromJson(body, T::class.java)

        } catch (e: Exception) {
            throw  Exception(e.message)
        }
    }

    fun verificationBody(body: ResponseBody?) {
        if (body == null) throw Exception("ERROR:ResponseBody=null")
    }
}