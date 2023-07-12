package com.example.tango.domain.model

import com.example.tango.data.remote.dto.PostDto
import java.io.Serializable


data class Post(
    val postId: Int? = 0,
    val title: String? = "",
    val userId: Int? = 0,
    val body: String? = "",
    var userName: String? = "",
) : Serializable {
    fun toPostDto(): PostDto {
        return PostDto(
            id = postId,
            title = title,
            userId = userId,
            body = body,
        )
    }
}