package com.alligator.reddittopposts.presentation.ui.screen.mainscreen.components

import android.provider.MediaStore.Downloads
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.alligator.reddittopposts.R
import com.alligator.reddittopposts.domain.model.Post
import com.alligator.reddittopposts.utils.openInBrowser
import com.alligator.reddittopposts.utils.saveImageToGallery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DownloadContentDialog(isActive: Boolean, post: Post, onDismiss: () -> Unit) {
    val context = LocalContext.current
    AnimatedVisibility(isActive) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(Modifier.size(30.dp))
                        Text(text = "Just save me..")
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .clickable(
                                    onClick = onDismiss,
                                    indication = ripple(),
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            contentDescription = null
                        )
                    }

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        model = post.stockImage,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DialogButton(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            text = "Open",
                            onAction = {
                                openInBrowser(context, url = post.stockImage!!)
                            })
                        DialogButton(text = "Download", onAction = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val uri =  saveImageToGallery(context, imageUrl = post.stockImage?:"")
                                withContext(Dispatchers.Main){
                                    if(uri!=null){
                                        Toast.makeText(context, "Image already downloaded!", Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(context, "Sorry, I can't download this image :(", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                        })
                    }

                }
            }

        }
    }
}

@Composable
private fun DialogButton(modifier: Modifier = Modifier, text: String, onAction: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onClick = onAction,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}