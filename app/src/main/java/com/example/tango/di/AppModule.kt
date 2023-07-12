package com.example.tango.di

import com.example.tango.coomon.Constants.BASE_URL
import com.example.tango.data.remote.CommentApi
import com.example.tango.data.remote.PostsApi
import com.example.tango.data.respository.CommentRepositoryImpl
import com.example.tango.data.respository.PostRepositoryImpl
import com.example.tango.domain.respository.CommentRepository
import com.example.tango.domain.respository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun providePostApi(okHttpClient: OkHttpClient): PostsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PostsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostsApi): PostRepository {
        return PostRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideCommentApi(okHttpClient: OkHttpClient): CommentApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(api: CommentApi): CommentRepository {
        return CommentRepositoryImpl(api)
    }
}