package com.theathletic.interview.articles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theathletic.interview.R
import com.theathletic.interview.mvp.BaseFragment
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class ArticleDetailFragment
    : BaseFragment<ArticleDetailPresenter, ArticleDetailContract.ViewState>() {

    private lateinit var articleId: String

    override fun setupPresenter() = getViewModel<ArticleDetailPresenter>()

    override fun renderState(viewState: ArticleDetailContract.ViewState) {
        Timber.d("renderState: ${viewState.articleModel}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_detail, container, false)
        return view
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}