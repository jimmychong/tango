package com.example.tango.domain.user_case

import com.example.tango.coomon.DispatcherIo
import com.example.tango.coomon.Resource
import com.example.tango.data.remote.dto.toComment
import com.example.tango.domain.model.Comment
import com.example.tango.domain.respository.CommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(postId: Int): Flow<Resource<List<Comment>>> = flow {
        try {
            val comments = commentRepository.getComments(postId).map { it.toComment() }
            emit(Resource.Success(comments))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }.flowOn(ioDispatcher)
}
