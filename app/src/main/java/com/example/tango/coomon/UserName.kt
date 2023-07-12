package com.example.tango.coomon

import com.example.tango.domain.model.Post
import javax.inject.Inject


class UserName @Inject constructor() {

    var hmUserName: HashMap<Int, String> = HashMap<Int, String>()

    fun setUniqueNames(posts: List<Post>) {
        for (post in posts) {
            val userId = post.userId ?: 0
            if (hmUserName[userId] == null) {
                val name = "User${userId}"
                hmUserName[userId] = name
                post.userName = name
            } else
                post.userName = hmUserName[userId]
        }
    }
}