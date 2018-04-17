package com.arpan.recursiveto_do

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_todo.view.*

// Created on 14-04-2018

class TodoListAdapter(val mTodoItemList: ArrayList<TodoItem>,val mContext: Context) : RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder>() {

    override fun getItemCount(): Int = mTodoItemList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TodoItemViewHolder {
        return TodoItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_todo, parent, false))
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder?, position: Int) {
        holder!!.bindData(mTodoItemList[position])
    }


    inner class TodoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val intent = Intent(mContext, ToDoActivity::class.java)

            intent.putExtra(MainActivity.TODO_TITLE, mTodoItemList[adapterPosition].title)
            intent.putExtra(MainActivity.TODO_BODY, mTodoItemList[adapterPosition].body)
            intent.putExtra("RETURNING", true)

            mContext.startActivity(intent)
        }

        fun bindData(todoItem: TodoItem) {
            itemView.taskNameEditText.setText(todoItem.title)
            itemView.completedBox.isSelected = todoItem.completed

            if (todoItem.subTodoList != null) {
//                populateSubTodos(itemView.subToDoLinearLayout, todoItem.subTodoList)
            }
        }

    }

}