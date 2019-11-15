package me.s32xlevel.xsollaweather

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import me.s32xlevel.xsollaweather.util.DbUtils

class App : Application() {
    private lateinit var database: AppDatabase

    companion object {
        private var instance: Application? = null
        fun getInstance() = instance as App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    DbUtils.populateDb(db)
                }
            })
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase() = database
}