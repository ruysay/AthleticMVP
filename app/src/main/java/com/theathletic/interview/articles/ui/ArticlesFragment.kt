package com.theathletic.interview.articles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.theathletic.interview.R
import com.theathletic.interview.main.MainActivity
import com.theathletic.interview.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_articles.article_list
import kotlinx.android.synthetic.main.fragment_articles.progress_circular
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class ArticlesFragment
    : BaseFragment<ArticlesPresenter, ArticlesContract.ViewState>() {
    private lateinit var listAdapter: ArticleListAdapter
    override fun setupPresenter() = getViewModel<ArticlesPresenter>()

    override fun renderState(viewState: ArticlesContract.ViewState) {
        progress_circular.visibility = if (viewState.showLoading) View.VISIBLE else View.GONE
        listAdapter.articleModels = viewState.articleModels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_articles, container, false)
        listAdapter = ArticleListAdapter()
        return view
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        article_list.adapter = listAdapter
        article_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    /**
     *
     *
     *     override fun onRecyclerViewItemClickListener(arg1: Any?, arg2: Any?, arg3: Any?) {
     *     val articleId = arg1 as String
     *     (activity as MainActivity).goToArticleDetailFragment()
     *     }
     */

}