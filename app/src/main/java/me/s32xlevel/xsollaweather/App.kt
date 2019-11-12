package me.s32xlevel.xsollaweather

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class App : Application() {
    private lateinit var database: AppDatabase

    companion object {
        private var instance: Application? = null
        fun getInstance() = instance as App
    }

    // TODO onCreate Refactor
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
                        put("id", 524901)
                        put("name", "Москва")
                        put("is_saved", true)
                    })
                    db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
                        put("id", 511180)
                        put("name", "Пермь")
                        put("is_saved", true)
                    })
                }
            })
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase() = database
}