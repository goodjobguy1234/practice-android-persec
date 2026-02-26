package com.example.persecdemo.data.repo

import com.example.persecdemo.data.datasource.NasaDatasource
import com.example.persecdemo.data.model.ApodResponse
import com.example.persecdemo.usecase.NasaRepo
import com.example.persecdemo.usecase.model.ApodModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NasaRepoImpl @Inject constructor(
    private val datasoruce: NasaDatasource
): NasaRepo {
    override suspend fun fetchPictureOfDate(
        startDate: String,
        endDate: String
    ): Result<List<ApodModel>> {
        return with(Dispatchers.IO) {
            runCatching {
                datasoruce.getPictureOfDays(
                    startDate = startDate,
                    endDate = endDate
                ).toDomainModel()
            }
        }
    }

    private fun List<ApodResponse>.toDomainModel(): List<ApodModel> {
        return map {
            ApodModel(
                date = it.date ?: "",
                photoUrl = it.url ?: "",
                title = it.title ?: "",
                desc = it.explanation ?: ""
            )
        }
    }
}
