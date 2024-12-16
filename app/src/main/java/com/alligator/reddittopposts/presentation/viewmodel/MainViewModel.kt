package com.alligator.reddittopposts.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligator.reddittopposts.domain.model.Post
import com.alligator.reddittopposts.domain.usecase.GetNextPosts
import com.alligator.reddittopposts.domain.usecase.GetPreviousPosts
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNextPosts: GetNextPosts,
    private val getPreviousPosts: GetPreviousPosts
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    var isLoading = mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedPosts = getNextPosts.invoke() ?: emptyList()
            _posts.value = fetchedPosts
            Log.d("MAINVIEWMODEL", posts.value.toString())

        }
    }

    fun loadNextPost(){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value=true
            val fetchedPosts = getNextPosts.invoke() ?: emptyList()
            _posts.value+=fetchedPosts
            Log.d("MAINVIEWMODEL", posts.value.toString())
            isLoading.value=false
        }
    }

}