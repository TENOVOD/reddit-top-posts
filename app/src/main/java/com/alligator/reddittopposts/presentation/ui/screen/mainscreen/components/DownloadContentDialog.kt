package com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components

import android.provider.MediaStore.Downloads
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.alligator.reddittopposts.domain.model.Post

@Composable
fun DownloadContentDialog(isActive:Boolean, post: Post,onDismiss:()->Unit){

    AnimatedVisibility(isActive) {
        Dialog(onDismissRequest = onDismiss) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center){
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = post.thumbnail,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }

}