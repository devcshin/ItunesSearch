package com.devc.watchacode.domain

import com.devc.watchacode.data.remote.dto.ItunesDto

//통신상태값을 mutableStateOf로 받고 조회하기 위한 객체였으나, 19에선 미동작
data class ItunesState(
    val isLoading:Boolean = false,
    val results:ItunesDto? = null,
    val error:String = ""
)