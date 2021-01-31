package com.theathletic.interview

import com.theathletic.interview.articles.data.Article
import com.theathletic.interview.articles.data.remote.ArticleApi

/**
 * A proxy to test API which is served by MockServer
 */
class JsonRepository(private val api: ArticleApi) {
    suspend fun getArticleList(): List<Article> = api.getArticles()
}