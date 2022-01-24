package com.devc.watchacode.domain.use_case

import com.devc.watchacode.common.Resource
import com.devc.watchacode.data.remote.dto.ItunesDto
import com.devc.watchacode.domain.repository.ItunesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItunesUseCase @Inject constructor(private val repository: ItunesRepository) {
    operator fun invoke(): kotlinx.coroutines.flow.Flow<Resource<ItunesDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getList()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "UNKNOWN_ERROR"))
        }
    }
}