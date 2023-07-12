package com.example.tango.domain.user_case

import android.util.Log
import com.example.tango.coomon.DispatcherIo
import com.example.tango.coomon.Resource
import com.example.tango.data.remote.dto.PostDto
import com.example.tango.data.remote.dto.toPost
import com.example.tango.domain.model.Post
import com.example.tango.domain.respository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val postRepository: PostRepository
) {
    operator fun invoke(post: PostDto): Flow<Resource<Post>> = flow {
        try {
            val result = postRepository.updatePost(post.id ?: 0, post)
            emit(Resource.Success(result.toPost()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }.flowOn(ioDispatcher)
}