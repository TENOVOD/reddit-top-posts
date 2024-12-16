package com.alligator.reddittopposts.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@SuppressLint("InlinedApi")
suspend fun saveImageToGallery(context: Context, imageUrl: String): Uri? {
    val fileName = "Image_${System.currentTimeMillis()}"

    return withContext(Dispatchers.IO) {
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val relativeLocation = "Pictures/UltraReddit"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, relativeLocation)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val imageUri = context.contentResolver.insert(collection, contentValues) ?: return@withContext null
        try {
            context.contentResolver.openOutputStream(imageUri)?.use { out ->
                URL(imageUrl).openStream().use { input ->
                    input.copyTo(out)
                }
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            context.contentResolver.update(imageUri, contentValues, null, null)
            return@withContext imageUri
        } catch (e: Exception) {
            context.contentResolver.delete(imageUri, null, null)
            e.printStackTrace()
            null
        }
    }
}