package com.devc.watchacode.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val trackId: Int,
    @ColumnInfo(name = "trackName") val trackName: String,
    @ColumnInfo(name = "collectionName") val collectionName: String,
    @ColumnInfo(name = "artistName") val artistName: String,
    @ColumnInfo(name = "ImageUrl") val ImageUrl: String
)