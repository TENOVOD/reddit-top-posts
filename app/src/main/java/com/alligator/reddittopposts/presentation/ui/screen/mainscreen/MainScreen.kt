package com.alligator.reddittopposts.presentation.ui.screen.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.PostCard
import com.alligator.reddittopposts.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    vm: MainViewModel= hiltViewModel()
){

    val posts by vm.posts.collectAsState()

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
                    PostCard(it){}
                }
            }
        }
    }
}