package com.ahmad.githubuserapplication.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "github_users")
@Parcelize
data class UserEntity(

    @PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int?,

    @field:ColumnInfo(name = "login")
    var login: String?,

    @field:ColumnInfo(name = "name")
    var name: String? = null,

    @field:ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,

    @field:ColumnInfo(name = "email")
    var email: String? = null,

    @field:ColumnInfo(name = "location")
    var location: String? = null,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean
): Parcelable