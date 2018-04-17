package com.arpan.recursiveto_do

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.nfc.NfcAdapter
import android.provider.BaseColumns

// Created on 15-04-2018

class TodoContract {

    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo_list"
        const val COLUMN_TODO_TITLE = "title"
        const val COLUMN_TODO_BODY = "body"
        const val COLUMN_COMPLETED = "completed"
        const val COLUMN_IS_CHILD = "is_child"
        const val COLUMN_PARENT_ID = "parent_id"
    }

}

class TodoListDBHelper (val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        val DATABASE_NAME = "todo.db"
        val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
                "CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                        "${TodoContract.TodoEntry.COLUMN_PARENT_ID} INTEGER, " +
                        "${TodoContract.TodoEntry.COLUMN_IS_CHILD} INTEGER, " +
                        "${TodoContract.TodoEntry.COLUMN_TODO_TITLE} TEXT, " +
                        "${TodoContract.TodoEntry.COLUMN_TODO_BODY} TEXT, " +
                        "${TodoContract.TodoEntry.COLUMN_COMPLETED} INTEGER" +
                        ")"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"
    }

}

