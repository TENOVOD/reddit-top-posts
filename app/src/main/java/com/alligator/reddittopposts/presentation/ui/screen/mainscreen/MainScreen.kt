package com.alligator.reddittopposts.presentation.ui.screen.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.IndeterminateCircularIndicator
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.NoInternetConnection
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.PostCard
import com.alligator.reddittopposts.presentation.viewmodel.MainViewModel
import com.alligator.reddittopposts.utils.network.ConnectivityObserver


@Composable
fun MainScreen(
    vm: MainViewModel= hiltViewModel()
){

    val posts by vm.posts.collectAsState()
    val isLoading by vm.isLoading
    val networkStatus by vm.networkStatus.collectAsState()
    AnimatedVisibility(networkStatus!=ConnectivityObserver.Status.Available) {
        NoInternetConnection {
            vm.loadNextPost()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues->

        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(posts){
                    PostCard(it)
                    Spacer(Modifier.fillMaxWidth().height(1.dp).alpha(0.2f).background(MaterialTheme.colorScheme.scrim))
                }
                item{
                    LaunchedEffect(posts.size) {
                        vm.loadNextPost()
                    }
                    IndeterminateCircularIndicator(isLoading=isLoading)
                }
            }
        }
    }
}