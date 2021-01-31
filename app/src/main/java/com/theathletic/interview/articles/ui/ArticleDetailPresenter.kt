package com.theathletic.interview.articles.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.theathletic.interview.articles.data.Article
import com.theathletic.interview.articles.data.ArticleRepository
import com.theathletic.interview.mvp.ArticleInteractor
import com.theathletic.interview.mvp.BasePresenter
import com.theathletic.interview.mvp.DataState
import com.theathletic.interview.mvp.Interactor
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticleDetailPresenter (
    private val articleRepository: ArticleRepository
) : BasePresenter<ArticleState, ArticleDetailContract.ViewState>(), ArticleInteractor {
    override val initialState by lazy {
        ArticleState(isLoading = true, article = null)
    }

    init {
        Timber.i("creating new article detail presenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initialize() {
        presenterScope.launch {
            updateState {
                copy(
                    isLoading = false,
                    article = null
                )
            }
        }
    }

    override fun transform(data: ArticleState): ArticleDetailContract.ViewState {
        return ArticleDetailContract.ViewState(
            showLoading = data.isLoading,
            articleModel = data.article?.toUiModel()
        )
    }

    private fun Article.toUiModel() =
        ArticleDetailContract.ArticleUiModel(
            id = id,
            title = title,
            imageUrl = imageUrlString,
            body = body
        )

    override fun getArticleById(id: String) {
        presenterScope.launch {
            val article = articleRepository.getArticleById(id)
            article?.let {
                updateState {
                    copy(
                        isLoading = false,
                        article = article
                    )
                }
            }
        }
    }
}

data class ArticleState(
    val isLoading: Boolean,
    val article: Article?
) : DataState