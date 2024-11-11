package com.example.videomessengerproject

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "information_about_user")
data class UsersAccounts(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    @ColumnInfo(name = "user_name")
    var userName: String = "",
    @ColumnInfo(name = "user_second_name")
    var userSecondName: String = "",
    @ColumnInfo(name = "user_third_name")
    var userThirdName: String = "",
    @ColumnInfo(name = "user_date_of_birthday")
    var userDateOfBirthday: String = "",
    @ColumnInfo(name = "user_city_of_born")
    var userCityOfBorn: String = "",
    @ColumnInfo(name = "user_school")
    var userSchool: String = "",
    @ColumnInfo(name = "user_sex")
    var userSex: String = "",
    @ColumnInfo(name = "user_login")
    var userLogin: String = "",
    @ColumnInfo(name = "user_password")
    var userPassword: String = "",
    @ColumnInfo(name = "user_avatar")
    var userAvatar: ByteArray? = null
)
