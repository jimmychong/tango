package com.example.tango.coomon

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIo
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherDefault
@Module
@InstallIn(SingletonComponent::class)
class DispatcherProvider {
    @Provides
    @DispatcherIo
    fun providesIoDispatcher() = Dispatchers.IO
    @Provides
    @DispatcherDefault
    fun providesDefaultDispatcher() = Dispatchers.Default
}