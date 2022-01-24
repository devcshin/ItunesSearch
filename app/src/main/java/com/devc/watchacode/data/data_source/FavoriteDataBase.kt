package com.devc.watchacode.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devc.watchacode.domain.model.Favorite

@Database(
    entities = [Favorite::class],
    version = 1
)
abstract class FavoriteDataBase : RoomDatabase() {
    abstract val favoriteDao:FavoriteDao
}