package me.ykrank.s1next.data.api

import com.github.ykrank.androidtools.data.CacheParam
import com.github.ykrank.androidtools.data.Resource
import kotlinx.coroutines.flow.Flow
import me.ykrank.s1next.data.api.model.wrapper.ForumGroupsWrapper
import me.ykrank.s1next.data.api.model.wrapper.PostsWrapper
import me.ykrank.s1next.data.api.model.wrapper.ThreadsWrapper

/**
 * RxCache provide cache for retrofit
 * Created by ykrank on 2017/4/22.
 */
interface ApiCacheProvider {
    suspend fun getForumGroupsWrapper(
        param: CacheParam? = null
    ): Flow<Resource<ForumGroupsWrapper>>

    suspend fun getThreadsWrapper(
        forumId: String?,
        typeId: String?,
        page: Int,
        param: CacheParam? = null
    ): Flow<Resource<ThreadsWrapper>>

    suspend fun getPostsWrapper(
        threadId: String?,
        authorId: String?,
        page: Int,
        param: CacheParam? = null
    ): Flow<Resource<PostsWrapper>>
}