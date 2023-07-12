package com.example.tango.data.respository

import com.example.tango.data.remote.PostsApi
import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.PostDto
import com.example.tango.domain.respository.PostRepository
import javax.inject.Inject

class FakePostRepository @Inject constructor(postsApi: PostsApi) : PostRepository {

    private val posts = fakePostList()

    private fun fakePostList(): MutableList<PostDto> {
        return mutableListOf(
            PostDto(
                id = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
                userId = 1
            ),
            PostDto(
                id = 2,
                title = "qui est esse",
                body = "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
                userId = 1
            ),
            PostDto(
                id = 3,
                title = "ea molestias quasi exercitationem repellat qui ipsa sit aut",
                body = "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut",
                userId = 2
            ),
            PostDto(
                id = 4,
                title = "eum et est occaecati",
                body = "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit",
                userId = 3
            ),
        )
    }

    override suspend fun getPosts(): List<PostDto> {
        return posts
    }

    override suspend fun addNewPosts(request: NewPostRequest): PostDto {
        posts.add(
            PostDto(
                id = 5,
                title = request.title,
                body = request.body,
                userId = 1
            )
        )
        return posts[posts.size - 1]
    }

    override suspend fun updatePost(id: Int, post: PostDto): PostDto {
        val updatePost = posts.find { it.id == id }?.copy(
            title = post.title,
            body = post.body
        )

        updatePost?.let { updatedPost ->
            val position = posts.indexOfFirst { it.id == id }

            posts.set(position, updatedPost)
        }


        return posts.find { it.id == id } ?: PostDto()
    }
}