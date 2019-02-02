package `in`.nishachar.mypasscode.shared

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        private var sInstance: AppDatabase? = null
        private const val DATABASE_NAME = "AppDatabase.db"

        fun getDatabase(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                    .build()
            }
            return sInstance as AppDatabase
        }

        fun getMemoryDatabase(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    sInstance = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                        .build()
                }
            }
            return sInstance as AppDatabase
        }

        fun destroyInstance() {
            sInstance?.close()
            sInstance = null
        }
    }
}