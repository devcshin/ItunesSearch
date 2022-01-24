package com.devc.watchacode.domain.repository

import com.devc.watchacode.data.remote.dto.ItunesDto

interface ItunesRepository {
    suspend fun getList(): ItunesDto
}