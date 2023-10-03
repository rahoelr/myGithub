package com.example.mygithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithub.database.FavoriteDao
import com.example.mygithub.database.FavoriteRoomDatabase
import com.example.mygithub.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUser()

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insertFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteUserDao.removeFavorite(id) }
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteUser?> {
        return mFavoriteUserDao.getFavoriteUserByUsername(username)
    }

    fun isUserFavorite(username: String): LiveData<Boolean> {
        return mFavoriteUserDao.isUserFavorite(username)
    }

    fun getAllFavoritesUser(): LiveData<List<FavoriteUser>> {
        return mFavoriteUserDao.getAllUser()
    }

    fun setFavoriteUser(favoriteUser: FavoriteUser, isFavorite: Boolean) {
        if (isFavorite) {
            // Jika isFavorite adalah true, tambahkan ke daftar favorit
            insert(favoriteUser)
        } else {
            // Jika isFavorite adalah false, hapus dari daftar favorit
            delete(favoriteUser.id)
        }
    }
}
