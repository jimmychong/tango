package com.example.tango.data.respository

import com.example.tango.data.remote.PostsApi
import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.PostDto
import com.example.tango.domain.respository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostsApi
) : PostRepository {
    override suspend fun getPosts(): List<PostDto> {
        return api.getPosts()
    }

    override suspend fun addNewPosts(request: NewPostRequest): PostDto {
        return api.addNewPosts(request)
    }

    override suspend fun updatePost(id: Int, post: PostDto): PostDto {
        return api.updatePost(id, post)
    }
}