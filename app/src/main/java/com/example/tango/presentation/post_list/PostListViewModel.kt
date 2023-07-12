package com.example.tango.presentation.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tango.coomon.Resource
import com.example.tango.domain.user_case.AddNewPostUseCase
import com.example.tango.domain.user_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val addPostUseCase: AddNewPostUseCase,
) : ViewModel() {

    private val _state = MutableSharedFlow<PostListState>()
    val state = _state.asSharedFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        getPostsUseCase().onStart {
            _state.emit(PostListState.Loading(true))
        }.onEach {
            when (it) {
                is Resource.Error -> {
                    _state.emit(
                        PostListState.Error(error = it.message ?: "An unexpected error occured")
                    )
                }

                is Resource.Success -> {
                    _state.emit(PostListState.Posts(posts = it.data ?: emptyList()))
                }
            }
        }.onCompletion {
            _state.emit(PostListState.Loading(false))
        }.launchIn(viewModelScope)
    }

    fun addPost(title: String, body: String) {
        addPostUseCase(title, body).onStart {
            _state.emit(PostListState.Loading(true))
        }.onEach {
            when(it){
                is Resource.Error -> {
                    _state.emit(
                        PostListState.Error(error = it.message ?: "An unexpected error occured")
                    )
                }
                is Resource.Success -> {
                    if(it.data == null){
                        _state.emit(
                            PostListState.Error(error = "An unexpected error occured")
                        )
                    }else{
                        _state.emit(PostListState.AddedNewPost(post = it.data))
                    }
                }
            }
        }.onCompletion {
            _state.emit(PostListState.Loading(false))
        }.launchIn(viewModelScope)

    }
}