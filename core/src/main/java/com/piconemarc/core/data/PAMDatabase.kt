package com.piconemarc.core.data

import android.content.ContentValues
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.piconemarc.core.data.account.AccountDao
import com.piconemarc.core.data.category.CategoryDao
import com.piconemarc.core.data.operation.OperationDao
import com.piconemarc.core.domain.Constants.ACCOUNT_TABLE
import com.piconemarc.core.domain.Constants.CATEGORY_TABLE
import com.piconemarc.core.domain.Constants.OPERATION_TABLE
import com.piconemarc.core.domain.DateTypeConverter
import com.piconemarc.core.domain.entityDTO.AccountDTO
import com.piconemarc.core.domain.entityDTO.CategoryDTO
import com.piconemarc.core.domain.entityDTO.OperationDTO

@Database(
    entities = [AccountDTO::class, CategoryDTO::class, OperationDTO::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DateTypeConverter::class)
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
                .addCallback(CALLBACK)
                .build()

        fun getPAMDatabase(context: Context): PAMDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }


        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                createAccounts(db)
                createCategories(db)
                createOperations(db)
            }

            private fun createCategories(db: SupportSQLiteDatabase){
                val contentValues = ContentValues()

                for (category in Generator.generateCategories()){
                    contentValues.put("id", category.id)
                    contentValues.put("color", category.color)
                    contentValues.put("name", category.name)

                    db.insert(CATEGORY_TABLE, OnConflictStrategy.IGNORE, contentValues)
                }
            }

            private fun createAccounts(db: SupportSQLiteDatabase){
                val contentValues = ContentValues()

                for (account in Generator.generateAccounts()){
                    contentValues.put("id", account.id)
                    contentValues.put("accountBalance", account.accountBalance)
                    contentValues.put("name", account.name)

                    db.insert(ACCOUNT_TABLE, OnConflictStrategy.IGNORE, contentValues)
                }
            }

            private fun createOperations(db: SupportSQLiteDatabase){
                val contentValues = ContentValues()

                for (operation in Generator.generateOperations()){
                    contentValues.put("id", operation.id)
                    contentValues.put("amount", operation.amount)
                    contentValues.put("name", operation.name)
                    contentValues.put("isRecurrent", operation.isRecurrent)
                    contentValues.put("endDateMonth", operation.endDateMonth)
                    contentValues.put("endDateYear", operation.endDateYear)
                    contentValues.put("accountId", operation.accountId)
                    contentValues.put("categoryId",operation.categoryId)
                    contentValues.put("categoryId",operation.emitDate?.time)


                    db.insert(OPERATION_TABLE, OnConflictStrategy.IGNORE, contentValues)
                }
            }

        }
    }
}