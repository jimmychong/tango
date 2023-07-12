package com.example.tango.domain.user_case

import android.util.Log
import com.example.tango.coomon.DispatcherIo
import com.example.tango.coomon.Resource
import com.example.tango.coomon.UserName
import com.example.tango.data.remote.dto.toPost
import com.example.tango.domain.model.Post
import com.example.tango.domain.respository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val postRepository: PostRepository,
    private val userName: UserName
) {
    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        try {
            val posts = postRepository.getPosts().map { it.toPost() }
            userName.setUniqueNames(posts)

            emit(Resource.Success(posts))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }.flowOn(ioDispatcher)
}
