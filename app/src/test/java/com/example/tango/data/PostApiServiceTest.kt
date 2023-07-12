package com.example.tango.data

import com.example.tango.data.remote.PostsApi
import com.example.tango.data.remote.dto.NewPostRequest
import com.example.tango.data.remote.dto.PostDto
import com.example.tango.data.respository.FakePostRepository
import com.example.tango.data.respository.PostRepositoryImpl
import com.example.tango.domain.respository.PostRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class PostApiServiceTest {
    @Mock
    private lateinit var api: PostsApi

    private lateinit var postRepository: PostRepository
    private lateinit var fakeResponseRepository: PostRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        postRepository = PostRepositoryImpl(api)
        fakeResponseRepository = FakePostRepository(api)
    }

    @Test
    fun testGetPosts() = runBlocking {

        val mockPosts = fakeResponseRepository.getPosts()
        val response = Response.success(mockPosts)

        `when`(api.getPosts()).thenReturn(response.body())

        val result = postRepository.getPosts()

        assertEquals(mockPosts, result)
    }

    @Test
    fun testAddNewPost() = runBlocking {

        val newPostRequest = NewPostRequest("title", "body", 3)
        val response = Response.success(fakeResponseRepository.addNewPosts(newPostRequest))

        `when`(api.addNewPosts(newPostRequest)).thenReturn(response.body())

        val result = postRepository.addNewPosts(newPostRequest)

        assert(result == fakeResponseRepository.addNewPosts(newPostRequest))
    }

    @Test
    fun testUpdatePost() = runBlocking {

        val postId = 1
        val updatePostRequest = PostDto(postId, "Updated Title", "Updated Body")
        val response = Response.success(fakeResponseRepository.updatePost(postId, updatePostRequest))

        `when`(api.updatePost(postId, updatePostRequest)).thenReturn(response.body())

        val result = postRepository.updatePost(1,updatePostRequest)

        assert(result == fakeResponseRepository.updatePost(1,updatePostRequest))
    }


}