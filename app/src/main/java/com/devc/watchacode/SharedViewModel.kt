package com.devc.watchacode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.devc.watchacode.common.Resource
import com.devc.watchacode.data.data_source.FavoriteDataBase
import com.devc.watchacode.data.remote.dto.ItunesDto
import com.devc.watchacode.domain.model.Favorite
import com.devc.watchacode.domain.use_case.GetItunesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getItunesUseCase: GetItunesUseCase
) : ViewModel()  {


    //viewbinding하여 초기 없을때 메시지나 화면에 에러를 띄우기 위한변수
    val emptyMessage = MutableLiveData("없어요")
    val searchResult = MutableLiveData<ItunesDto>()
    val favoriteResult = MutableLiveData<List<Favorite>>()
    val favoriteIds = MutableLiveData<List<Int>>()

    //DB연결 생성자
    val db = Room.databaseBuilder(
        Application.ApplicationContext(),
        FavoriteDataBase::class.java, "favorite"
    ).build()

   fun getList() {
        getItunesUseCase().onEach { result->
            when(result){
                is Resource.Success-> {
                    searchResult.value = result.data!!
                }
                is Resource.Error-> {
                    emptyMessage.value = result.message
                }
                is Resource.Loading->{
                    emptyMessage.value = "불러오는 중이에유"
                }
            }
        }.launchIn(viewModelScope)
    }



    fun insertFavorite(favorite: Favorite){
        viewModelScope.launch(Dispatchers.Default) {
            db.favoriteDao.insertFavorite(favorite)
        }
    }
    fun deleteFavorite(id: Int){
        viewModelScope.launch(Dispatchers.Default) {
            db.favoriteDao.deleteFavorite(id)
        }
    }
    fun getFavorites(){
        viewModelScope.launch(Dispatchers.Default) {
            val favoritesList : List<Favorite> = db.favoriteDao.getFavorite()
            //background에서는 직접할당안됨
            favoriteResult.postValue(favoritesList)
        }
    }
    fun getFavoriteIds(){
        viewModelScope.launch(Dispatchers.Default) {
            val ids : List<Int> = db.favoriteDao.getFavoriteIds()
            //background에서는 직접할당안됨
            favoriteIds.postValue(ids)
        }
    }
}