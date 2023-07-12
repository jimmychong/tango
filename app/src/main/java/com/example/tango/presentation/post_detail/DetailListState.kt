package com.example.tango.presentation.post_detail

import com.example.tango.domain.model.Post

sealed class DetailListState {
    data class Loading(val isShow: Boolean) : DetailListState()
    data class Display(val displayData: Pair<Post,Int>) : DetailListState()
    data class Error(val error: String) : DetailListState()
}