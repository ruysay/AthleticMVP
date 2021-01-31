package com.theathletic.interview.articles.ui

import com.theathletic.interview.mvp.UiModel

interface ArticleDetailContract {

    data class ViewState(
        val showLoading: Boolean,
        val articleModel: ArticleUiModel?
    ): com.theathletic.interview.mvp.ViewState

    class ArticleUiModel(
        val id: String,
        val title: String,
        val author: String? = null,
        val displayAuthor: Boolean = false,
        val imageUrl: String?,
        val body: String?
    ): UiModel {
        override val stableId = title
    }

    sealed class Event: com.theathletic.interview.mvp.Event() {
        object ShowToastEvent: Event()
    }


}