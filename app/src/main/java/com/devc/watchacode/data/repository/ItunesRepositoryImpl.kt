package com.devc.watchacode.data.repository

import com.devc.watchacode.data.remote.ItunesApi
import com.devc.watchacode.data.remote.dto.ItunesDto
import com.devc.watchacode.domain.repository.ItunesRepository
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val api : ItunesApi
) : ItunesRepository {

    override suspend fun getList(): ItunesDto {
        return api.getList()
    }


}