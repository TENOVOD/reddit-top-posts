package com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alligator.reddittopposts.R
import com.alligator.reddittopposts.domain.model.Post
import com.alligator.reddittopposts.utils.convertToHoursAgo


@Composable
fun PostCard(post: Post, openFullSize: (Post) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopPartOfCard(post)
            ContentPart(post = post, openFullSize = openFullSize)
            post.countOfComments?.let { BottomPart(post.countOfComments) }

        }

    }
}


@Composable
private fun ContentPart(post: Post, openFullSize: (Post) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable(
            onClick = { openFullSize(post) },
            indication = ripple(),
            interactionSource = remember { MutableInteractionSource() }
        ), contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = post.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
private fun TopPartOfCard(post: Post) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
                post.authorName?.let { Text(text = it) }

            }
            post.created?.let { Text(convertToHoursAgo(post.created)) }

        }
        post.title?.let {
            Text(text = post.title)
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
            tint = Color.Unspecified
        )
        Text(text = countOfComment.toString())
    }

}