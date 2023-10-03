package com.example.mygithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mygithub.ui.favorites.FavoriteUserAct

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(user: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun getUserById(id: Int): LiveData<FavoriteUser>

    @Query("SELECT * FROM FavoriteUser WHERE login = :username")
    fun getUserByUsername(username: String): FavoriteUser?

    @Query("SELECT * FROM FavoriteUser WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?>

    @Query("SELECT EXISTS (SELECT 1 FROM FavoriteUser WHERE login = :username LIMIT 1)")
    fun isUserFavorite(username: String): LiveData<Boolean>



}