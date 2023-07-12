package com.example.tango.data.remote.dto

import com.example.tango.domain.model.Comment

data class CommentDto(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)

fun CommentDto.toComment(): Comment {
    return Comment(
        commentId = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}