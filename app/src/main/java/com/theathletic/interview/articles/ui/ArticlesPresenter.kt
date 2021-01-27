package com.theathletic.interview.articles.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.theathletic.interview.articles.data.Article
import com.theathletic.interview.articles.data.ArticleRepository
import com.theathletic.interview.mvp.DataState
import com.theathletic.interview.mvp.BasePresenter
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticlesPresenter(
    private val articleRepository: ArticleRepository
) : BasePresenter<ArticlesState, ArticlesContract.ViewState>() {
    override val initialState by lazy {
        ArticlesState(isLoading = true)
    }

    init {
        Timber.i("creating new articles presenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initialize() {
        presenterScope.launch {
            val articles = articleRepository.getArticles()
            updateState {
                copy(
                    isLoading = false,
                    articles = articles
                )
            }
        }
    }

    override fun transform(data: ArticlesState): ArticlesContract.ViewState {
        return ArticlesContract.ViewState(
            showLoading = data.isLoading,
            articleModels = data.articles.map { it.toUiModel() }
        )
    }

    private fun Article.toUiModel() =
        ArticlesContract.ArticleUiModel(
            title = title,
            imageUrl = imageUrlString
        )
}

data class ArticlesState(
    val isLoading: Boolean,
    val articles: List<Article> = emptyList()
) : DataState