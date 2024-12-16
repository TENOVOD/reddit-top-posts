package com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alligator.reddittopposts.R
import com.alligator.reddittopposts.domain.model.Post
import com.alligator.reddittopposts.utils.convertToHoursAgo
import com.alligator.reddittopposts.utils.openInBrowser


@Composable
fun PostCard(post: Post) {
    var isActiveDialog by remember { mutableStateOf(false) }
    DownloadContentDialog(isActive = isActiveDialog, post = post) {
        isActiveDialog = false
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopPartOfCard(post)
        ContentPart(post = post) {
            isActiveDialog = true
        }
        post.countOfComments?.let { BottomPart(post.countOfComments) }

    }


}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ContentPart(post: Post, openFullSize: () -> Unit) {
    val context = LocalContext.current
    val url = post.stockImage ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center
    ) {
        if (post.isVideo == true) {
            Button(
                onClick = { openInBrowser(context, url) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Open Video")
            }
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(
                        onClick = { openFullSize() },
                        indication = ripple(),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                model = post.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }


}


@Composable
private fun TopPartOfCard(post: Post) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    model = post.authorIcon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(
                            CircleShape
                        )
                )
                post.authorName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall
                    )

                }

            }
            post.created?.let {
                Text(
                    convertToHoursAgo(post.created),
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
        post.title?.let {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Composable
private fun BottomPart(countOfComment: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(25.dp)
                .graphicsLayer(scaleX = -1f),
            painter = painterResource(R.drawable.ic_comment),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        Text(text = countOfComment.toString())
    }

}