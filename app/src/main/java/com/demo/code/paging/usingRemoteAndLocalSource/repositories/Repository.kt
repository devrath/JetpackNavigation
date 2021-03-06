package com.demo.code.paging.usingRemoteAndLocalSource.repositories

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.demo.code.paging.usingRemoteSource.database.LocalDatabase
import com.demo.code.paging.usingRemoteSource.models.FeedPost
import com.demo.code.paging.usingRemoteSource.networking.ApiClient
import com.demo.code.paging.usingRemoteSource.networking.RemoteService
import com.demo.code.paging.usingRemoteSource.repositories.DataMediator
import kotlinx.coroutines.flow.Flow

class Repository(context: Context) {

    // Remote API reference
    private val apiService = ApiClient.getClient().create(RemoteService.service)
    // Local database reference
    private val localDatabase = LocalDatabase.create(context)

    /**
     * @return Flow of Paging data
     */
    @OptIn(ExperimentalPagingApi::class)
    fun fetchPosts(): Flow<PagingData<FeedPost>> {

        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 1),
            remoteMediator = DataMediator(apiService, localDatabase),
            pagingSourceFactory = { localDatabase.postsDao().getPosts() }
        ).flow

    }

}