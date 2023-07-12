package com.example.tango.domain.respository

import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.PostDto

interface PostRepository {

    suspend fun getPosts(): List<PostDto>

    suspend fun addNewPosts(request: NewPostRequest): PostDto

    suspend fun updatePost(id: Int, post: PostDto): PostDto

}