package com.invictus.bloghub.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictus.bloghub.models.blogitem
import com.invictus.bloghub.repository.BlogFirebase
import com.invictus.bloghub.repository.FlaskApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddBlogScreenViewModel : ViewModel() {

    private val fire= BlogFirebase()

    fun addBlog(content: blogitem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fire.addBlog(content) // Add the blog using BlogFirebase
            }
        }
    }

    private val _isLoading = mutableStateOf<Boolean?>(null)
    val isLoading: State<Boolean?> get() = _isLoading

    private val api = FlaskApi()

    private val _category = api.category
    val category: State<FlaskApi.CategoryInfo?> = _category

    fun categorize(content: String)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true

                api.fetchcategory(content)

                _isLoading.value = false


            }

        }
    }






}