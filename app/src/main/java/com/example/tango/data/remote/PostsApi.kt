package com.example.tango.data.remote

import com.example.tango.coomon.Resource
import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.PostDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostsApi {

    @GET("/jimmychong/tango/posts")
    suspend fun getPosts(): List<PostDto>

    @POST("/jimmychong/tango/posts")
    suspend fun addNewPosts(@Body newPost: NewPostRequest): PostDto

    @PUT("/jimmychong/tango/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body newPost: PostDto): PostDto
}