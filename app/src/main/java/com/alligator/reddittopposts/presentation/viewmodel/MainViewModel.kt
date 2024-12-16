package com.alligator.reddittopposts.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alligator.reddittopposts.domain.model.Post
import com.alligator.reddittopposts.domain.usecase.GetNextPosts
import com.alligator.reddittopposts.utils.network.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main View Model
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNextPosts: GetNextPosts,

    private val connectivityObserver: ConnectivityObserver

) : ViewModel() {

    val networkStatus = connectivityObserver.status

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    var isLoading = mutableStateOf(false)

    init {
        connectivityObserver.start()
        loadNextPost()
    }

    fun loadNextPost(){
        viewModelScope.launch(Dispatchers.IO) {
            if (!isLoading.value){
                isLoading.value=true
                while (networkStatus.value!=ConnectivityObserver.Status.Available){
                    delay(100)
                }

                val fetchedPosts = getNextPosts.invoke() ?: emptyList()

                //Check for duplicates
                val newPosts = fetchedPosts.filter { newPost ->
                    _posts.value.none { it.stockImage == newPost.stockImage }
                }

                _posts.value+=newPosts
                isLoading.value=false
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        connectivityObserver.stop()
    }

}