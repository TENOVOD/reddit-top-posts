package com.alligator.reddittopposts.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openInBrowser(context: Context, url:String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}