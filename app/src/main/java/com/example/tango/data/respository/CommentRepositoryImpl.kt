package com.example.tango.data.respository

import com.example.tango.data.remote.CommentApi
import com.example.tango.data.remote.dto.CommentDto
import com.example.tango.domain.respository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val api: CommentApi
) : CommentRepository {
    override suspend fun getComments(postId: Int): List<CommentDto> {
        return api.getComments(postId)
    }
}