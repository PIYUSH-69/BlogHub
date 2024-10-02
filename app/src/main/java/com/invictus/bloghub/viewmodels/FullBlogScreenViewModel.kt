package com.invictus.bloghub.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictus.bloghub.repository.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullBlogScreenViewModel : ViewModel() {
    private val api = Api()

    private val _summary = api.summary
    val summary: State<String?> = _summary

    fun summarize(content: String)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                api.fetchSummarizedText(content)
            // Replace with your summarization logic
            }

        }
    }

}
