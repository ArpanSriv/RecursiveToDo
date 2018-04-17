package com.arpan.recursiveto_do

// Created on 14-04-2018

class TodoItem(val completed: Boolean,
               val title: String,
               val body: String?,
               val subTodoList: ArrayList<TodoItem>?)