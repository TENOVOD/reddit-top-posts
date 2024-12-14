package com.alligator.reddittopposts.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedditUserApiResponseDto(
    val data: UserData
) {
    @Serializable
    data class UserData(
        @SerialName("icon_img")
        val iconImage:String
    )
}