package com.example.tango.domain.user_case

import com.example.tango.coomon.DispatcherIo
import com.example.tango.coomon.Resource
import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.toPost
import com.example.tango.domain.model.Post
import com.example.tango.domain.respository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddNewPostUseCase @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val repository: PostRepository
) {
    operator fun invoke(title: String, body: String): Flow<Resource<Post>> = flow {
        try {
            val result =  repository.addNewPosts(NewPostRequest(title, body, 1))

            emit(Resource.Success(result.toPost()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }.flowOn(ioDispatcher)
}