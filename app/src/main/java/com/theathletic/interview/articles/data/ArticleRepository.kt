package com.theathletic.interview.articles.data

import com.theathletic.interview.articles.data.remote.ArticleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ArticleRepository(private val articleApi: ArticleApi) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var cachedArticles = listOf<Article>()

    suspend fun getArticles(): List<Article> {
        cachedArticles = articleApi.getArticles()
        return cachedArticles
    }

    suspend fun getArticleById(id: String?): Article? {
        id?: return null
        return cachedArticles.firstOrNull{ article ->
            id == article.id
        }
    }
}