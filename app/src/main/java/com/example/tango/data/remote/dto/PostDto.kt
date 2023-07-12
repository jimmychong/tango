package com.example.tango.data.remote.dto

import androidx.annotation.Keep
import com.example.tango.domain.model.Post
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PostDto(
    val id: Int? = 0,
    val title: String? = "",
    val body: String? = "",
    val userId: Int? = 0,
)

fun PostDto.toPost(): Post {
    return Post(
        postId = id,
        title = title,
        userId = userId,
        body = body,
    )
}

data class NewPostRequest(
    val title: String,
    val body: String,
    val userId: Int
)