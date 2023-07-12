package com.example.tango.presentation.post_list

import com.example.tango.domain.model.Post

sealed class PostListState {
    data class Loading(val isShow: Boolean) : PostListState()
    data class Posts(val posts: List<Post>) : PostListState()
    data class Error(val error: String) : PostListState()
    data class AddedNewPost(val post: Post) : PostListState()
}
