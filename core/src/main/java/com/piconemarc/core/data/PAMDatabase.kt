package com.piconemarc.core.data

import android.content.ContentValues
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.piconemarc.core.data.account.AccountDao
import com.piconemarc.core.data.category.CategoryDao
import com.piconemarc.core.data.operation.OperationDao
import com.piconemarc.core.data.payment.PaymentDao
import com.piconemarc.core.data.transfer.TransferDao
import com.piconemarc.core.domain.entityDTO.*
import com.piconemarc.core.domain.utils.Constants.ACCOUNT_TABLE
import com.piconemarc.core.domain.utils.Constants.CATEGORY_TABLE
import com.piconemarc.core.domain.utils.Constants.OPERATION_TABLE
import com.piconemarc.core.domain.utils.DateTypeConverter

@Database(
    entities = [AccountDTO::class, CategoryDTO::class, OperationDTO::class, TransferDTO::class, PaymentDTO::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DateTypeConverter::class)
abstract class PAMDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun operationDao(): OperationDao
    abstract fun transferDao():TransferDao
    abstract fun paymentDao(): PaymentDao

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
                createPayments(db)
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
                    contentValues.put("accountOverdraft", account.accountOverdraft)

                    db.insert(ACCOUNT_TABLE, OnConflictStrategy.IGNORE, contentValues)
                }
            }

            private fun createOperations(db: SupportSQLiteDatabase){
                val contentValues = ContentValues()

                for (operation in Generator.generateOperations()){
                    contentValues.put("id", operation.id)
                    contentValues.put("amount", operation.amount)
                    contentValues.put("name", operation.name)
                    contentValues.put("accountId", operation.accountId)
                    contentValues.put("categoryId",operation.categoryId)
                    contentValues.put("categoryId",operation.emitDate?.time)
                    contentValues.put("paymentId",operation.paymentId)


                    db.insert(OPERATION_TABLE, OnConflictStrategy.IGNORE, contentValues)
                }
            }
            private fun createPayments(db:SupportSQLiteDatabase){
                val contentValues = ContentValues()

                Generator.generatePayments().forEach{
                    contentValues.put("id", it.id)
                    contentValues.put("accountId", it.accountId)
                    contentValues.put("operationId", it.operationId)
                    contentValues.put("name", it.name)
                }
            }

        }
    }
}