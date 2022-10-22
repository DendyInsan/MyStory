package com.dicoding.mystory.data.fordb
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "story")
data class StoryResponseDB(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",


    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "lat")
    val lat: Double



):Parcelable