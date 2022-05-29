package kz.reself.resod.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.reself.resod.entity.BuildingCardEntity
import kz.reself.resod.entity.UserEntity
import kz.reself.resod.dao.BuildingCardDao
import kz.reself.resod.dao.UserDao

@Database(
    version = 1,
    entities = [
        UserEntity::class,
        BuildingCardEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getBuildingCardDao(): BuildingCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "resod_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}