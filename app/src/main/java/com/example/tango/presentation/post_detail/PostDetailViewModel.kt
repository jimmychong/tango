package com.example.tango.presentation.post_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tango.coomon.Resource
import com.example.tango.domain.model.Post
import com.example.tango.domain.user_case.GetCommentUseCase
import com.example.tango.domain.user_case.UpdatePostUseCase
import com.example.tango.presentation.ui.PostDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    val commentUseCase: GetCommentUseCase,
    val updatePostUseCase: UpdatePostUseCase,
) : ViewModel() {

    private val _state = MutableSharedFlow<DetailListState>()
    val state = _state.asSharedFlow()

    var postData: Post? = null
    var noOfComment: Int = 0

    init {
        stateHandle.get<Post>(PostDetailActivity.POST_KEY)?.let {
            postData = it
            getCommentDetail(it)
        }
    }

    private fun getCommentDetail(post: Post) {
        commentUseCase(postId = post.postId ?: 0).onStart {
            DetailListState.Loading(true)
        }.onEach {
            when (it) {
                is Resource.Error -> _state.emit(
                    DetailListState.Error(
                        error = it.message ?: "An unexpected error occured"
                    )
                )

                is Resource.Success -> {
                    noOfComment = it.data?.size ?: 0
                    _state.emit(
                        DetailListState.Display(
                            Pair(
                                post,
                                it.data?.size ?: 0
                            )
                        )
                    )
                }
            }
        }.onCompletion {
            DetailListState.Loading(false)
        }.launchIn(viewModelScope)
    }

    fun updatePost(title: String, content: String) {
        val newData = postData?.toPostDto()?.copy(title = title, body = content) ?: return
        updatePostUseCase(newData).onStart {
            DetailListState.Loading(true)
        }.onEach {
            when (it) {
                is Resource.Error -> _state.emit(
                    DetailListState.Error(
                        error = it.message ?: "An unexpected error occured"
                    )
                )

                is Resource.Success -> {
                    it.data?.let { post ->
                        _state.emit(
                            DetailListState.Display(
                                Pair(
                                    post,
                                    noOfComment
                                )
                            )
                        )
                    }
                }
            }
        }.onCompletion {
            DetailListState.Loading(false)
        }.launchIn(viewModelScope)
    }
}