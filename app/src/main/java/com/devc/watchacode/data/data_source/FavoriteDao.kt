package com.devc.watchacode.data.data_source

import androidx.room.*
import com.devc.watchacode.domain.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(data: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavorite(): List<Favorite>

    //@Delete로 객체를 받아서 해도되는데, 조건을 다양화 할수 있어서 이렇게
    @Query("DELETE FROM favorite WHERE trackId = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT trackId FROM favorite")
    fun getFavoriteIds(): List<Int>

}