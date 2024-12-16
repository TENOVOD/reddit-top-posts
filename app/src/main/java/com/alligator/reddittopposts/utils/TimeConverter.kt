package com.alligator.reddittopposts.utils

import java.util.concurrent.TimeUnit

fun convertToHoursAgo(timestampStr: String): String {
    val timestamp = timestampStr.toDouble().toLong()
    val currentTimestamp = System.currentTimeMillis() / 1000
    val differenceInSeconds = currentTimestamp - timestamp
    val hoursAgo = TimeUnit.SECONDS.toHours(differenceInSeconds)
    return "$hoursAgo hours ago"
}