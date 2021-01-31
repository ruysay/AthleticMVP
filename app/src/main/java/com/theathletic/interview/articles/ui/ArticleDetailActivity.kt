package com.theathletic.interview.articles.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.theathletic.interview.R
import com.theathletic.interview.main.MainApplication
import com.theathletic.interview.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class ArticleDetailActivity: BaseActivity<ArticleDetailPresenter, ArticleDetailContract.ViewState>(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        setupPresenter()

        val articleId = intent.getStringExtra(ArticleDetailActivity::class.java.simpleName)!!
        presenter.getArticleById(articleId)
    }

    override fun setupPresenter() = getViewModel<ArticleDetailPresenter>()


    override fun renderState(viewState: ArticleDetailContract.ViewState) {
        viewState.articleModel?.let { article ->
            detail_title.text = article.title
            detail_body.text = article.body
            MainApplication.picassoWithCache.load(article.imageUrl).into(detail_imv)
        }
    }
}