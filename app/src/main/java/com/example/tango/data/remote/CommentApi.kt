package com.example.tango.data.remote

import com.example.tango.data.remote.dto.CommentDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApi {

    @GET("/jimmychong/tango/posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: Int): List<CommentDto>

}