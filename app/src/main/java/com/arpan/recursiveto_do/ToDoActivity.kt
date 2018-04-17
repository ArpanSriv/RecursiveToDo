package com.arpan.recursiveto_do

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log

import kotlinx.android.synthetic.main.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    val mDBHelper = TodoListDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val db = mDBHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_TODO_TITLE, titleEditText.text.toString())
            put(TodoContract.TodoEntry.COLUMN_TODO_BODY, todoAboutEditText.text.toString())
            put(TodoContract.TodoEntry.COLUMN_COMPLETED, false)
            put(TodoContract.TodoEntry.COLUMN_IS_CHILD, false)
            put(TodoContract.TodoEntry.COLUMN_PARENT_ID, -1)
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)

    }
}
