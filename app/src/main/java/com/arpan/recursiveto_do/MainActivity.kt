package com.arpan.recursiveto_do

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.arpan.recursiveto_do.R.id.tasksRecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.*

class MainActivity : AppCompatActivity() {

    companion object {
        val RETURNING_FROM_ACTIVITY = "RETURNING_FROM_ACTIVITY"
        val TODO_TITLE = "TODO_TITLE"
        val TODO_BODY = "TODO_BODY"
        var returning = false
    }

    val dbHelper = TodoListDBHelper(this)

    private val mTodos = ArrayList<TodoItem>()
    private var mAdapter: TodoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        addTodoFab.setOnClickListener({
            val intent = Intent(this@MainActivity, ToDoActivity::class.java)
            startActivity(intent)
        })


        val task = LoadTask()
        task.execute()

    }

    private fun setSampleData() {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_TODO_TITLE, "Sample Title")
            put(TodoContract.TodoEntry.COLUMN_TODO_BODY, "Lorem Ipsum Dolor Sit Amet, Constrectur Mita Piea.")
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)

    }

    private fun loadData() {
        mTodos.clear()

        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, TodoContract.TodoEntry.COLUMN_TODO_TITLE, TodoContract.TodoEntry.COLUMN_TODO_BODY, TodoContract.TodoEntry.COLUMN_COMPLETED)

        val selection = null

        val cursor = db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        )

        with(cursor) {
            while (moveToNext()) {
                val todoName = cursor.getString(getColumnIndexOrThrow(com.arpan.recursiveto_do.TodoContract.TodoEntry.COLUMN_TODO_TITLE))
                val todoBody = cursor.getString(getColumnIndexOrThrow(com.arpan.recursiveto_do.TodoContract.TodoEntry.COLUMN_TODO_BODY))
                val completed = false

                mTodos.add(com.arpan.recursiveto_do.TodoItem(completed, todoName, todoBody, null))
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                loadData()
                tasksRecyclerView.adapter.notifyDataSetChanged()
            }
        }
    }

    inner class LoadTask: AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            loadData()
            return null
        }

        override fun onPostExecute(result: Void?) {

            Toast.makeText(this@MainActivity, "Task Complete", Toast.LENGTH_SHORT).show()

            mAdapter = TodoListAdapter(mTodos, this@MainActivity)

            tasksRecyclerView.adapter = mAdapter

            tasksRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            tasksRecyclerView.adapter.notifyDataSetChanged()

        }
    }
}
