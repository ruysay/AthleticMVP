package com.theathletic.interview.articles.data.remote

import com.theathletic.interview.articles.data.Article
import retrofit2.http.GET

interface ArticleApi {
    @GET("articles")
    suspend fun getArticles(): List<Article>
}

