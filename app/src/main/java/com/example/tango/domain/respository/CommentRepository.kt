package com.example.tango.domain.respository

import com.example.tango.data.remote.dto.CommentDto

interface CommentRepository {

    suspend fun getComments(postId: Int): List<CommentDto>
}