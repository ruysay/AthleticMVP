package com.theathletic.interview.mvp

interface ArticleInteractor: Interactor {
    fun getArticleById(id: String)
}