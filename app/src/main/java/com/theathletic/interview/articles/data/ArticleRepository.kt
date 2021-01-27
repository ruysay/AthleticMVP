package com.theathletic.interview.articles.data

import com.theathletic.interview.articles.data.remote.ArticleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ArticleRepository(private val articleApi: ArticleApi) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun getArticles(): List<Article> {
        return articleApi.getArticles()
    }
}