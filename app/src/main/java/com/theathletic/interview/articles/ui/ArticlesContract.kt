package com.theathletic.interview.articles.ui

import com.theathletic.interview.mvp.UiModel

interface ArticlesContract {

    data class ViewState(
        val showLoading: Boolean,
        val articleModels: List<ArticleUiModel>
    ): com.theathletic.interview.mvp.ViewState

    class ArticleUiModel(
        val title: String,
        val author: String? = null,
        val displayAuthor: Boolean = false,
        val imageUrl: String?
    ): UiModel {
        override val stableId = title
    }

    sealed class Event: com.theathletic.interview.mvp.Event() {
        object ShowToastEvent: Event()
    }
}