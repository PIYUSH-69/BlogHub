package com.invictus.bloghub.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.invictus.bloghub.models.blogitem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class BlogFirebase {

    private val _blogs= MutableStateFlow<List<blogitem>>(emptyList())
    private val blogreference= Firebase.firestore.collection("blogs")
    val blogs: StateFlow<List<blogitem>>
        get() = _blogs

    suspend fun getBlogs(){
        val list=blogreference.get().await().toObjects(blogitem::class.java)
        Log.d(TAG, "getBlogs: "+list)
        _blogs.emit(list)
    }

    suspend fun addBlog(blog: blogitem) {
        try {
            // Add the blog to the "blogs" collection
            blogreference.add(blog).await()
            Log.d(TAG, "addBlog: Blog added successfully")
            // Optionally refresh the blogs list after adding
            getBlogs()
        } catch (e: Exception) {
            Log.e(TAG, "Error adding blog: ", e)
        }
    }





}