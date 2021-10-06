package com.piconemarc.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.piconemarc.core.data.account.AccountDao
import com.piconemarc.core.data.category.CategoryDao
import com.piconemarc.core.data.operation.OperationDao
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO

@Database(
    entities = [AccountDTO::class, CategoryDTO::class, OperationDTO::class],
    version = 1,
    exportSchema = false
)

abstract class PAMDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun operationDao(): OperationDao

    companion object {

        @Volatile
        private var instance: PAMDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, PAMDatabase::class.java, "pam_database.db")
                .fallbackToDestructiveMigration()
                //.addCallback(CALLBACK)
                .build()

        fun getPAMDatabase(context: Context): PAMDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
    }

}