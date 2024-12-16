package com.alligator.reddittopposts.presentation.ui.screen.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.IndeterminateCircularIndicator
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components.PostCard
import com.alligator.reddittopposts.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    vm: MainViewModel= hiltViewModel()
){

    val posts by vm.posts.collectAsState()
    val isLoading by vm.isLoading



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