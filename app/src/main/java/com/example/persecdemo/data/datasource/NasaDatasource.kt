package com.example.persecdemo.data.datasource

import android.util.Log
import com.example.persecdemo.BuildConfig
import com.example.persecdemo.data.NasaApiService
import com.example.persecdemo.data.model.ApodResponse
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class NasaDatasource @Inject constructor(
    private val service: NasaApiService,
) {
    suspend fun getPictureOfDays(
        startDate: String,
        endDate: String
    ): List<ApodResponse> {
        return service.getPictureOfDays(
            apiKey = BuildConfig.API_KEY,
            startDate = startDate,
            endDate = endDate
        ).handleResponse()
    }

    private inline fun <reified T> Response<T>.handleResponse(): T {
        if (this.isSuccessful) {
            return this.body() ?: throw IllegalStateException("Response body was empty")
        } else {
            val errorMessage = when (this.code()) {
                403 -> "Invalid API Key. Check your local.properties."
                400 -> "Bad Request. Check your date format (YYYY-MM-DD)."
                404 -> "No APOD found for this date."
                500 -> "NASA Server Error. Try again later."
                else -> "Unknown Error: ${this.code()}"
            }

            val rawError = this.errorBody()?.string()
            Log.e("api error", "NASA API Error: $errorMessage | Raw: $rawError")

            throw HttpException(this)
        }
    }
}