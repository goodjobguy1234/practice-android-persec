package com.example.persecdemo.usecase

import com.example.persecdemo.usecase.model.ApodModel

interface NasaRepo {
    suspend fun fetchPictureOfDate(
        startDate: String,
        endDate: String
    ): Result<List<ApodModel>>
}
