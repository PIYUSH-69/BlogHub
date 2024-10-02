package com.invictus.bloghub.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictus.bloghub.models.blogitem
import com.invictus.bloghub.repository.BlogFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel:ViewModel() {
    private val fire= BlogFirebase()
    val blogs : StateFlow<List<blogitem>> = fire.blogs

init {
    viewModelScope.launch {

        withContext(Dispatchers.IO) {
            Log.d(TAG, "gfdg: ")
            fire.getBlogs()
        }

    }
}
}
