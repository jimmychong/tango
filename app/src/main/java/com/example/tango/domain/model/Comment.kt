package com.example.tango.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Comment(
    val commentId: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
)


