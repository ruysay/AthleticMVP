package com.theathletic.interview.articles.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.theathletic.interview.R

class ArticleListAdapter :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    var articleModels: List<ArticlesContract.ArticleUiModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.findViewById(R.id.container)
        val titleView: TextView = view.findViewById(R.id.title)
        val authorView: TextView = view.findViewById(R.id.author)
        val imageView: ImageView = view.findViewById(R.id.image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_article, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        articleModels.getOrNull(position)?.let { article ->
            with(viewHolder) {
                titleView.text = article.title
                imageView.load(article.imageUrl)
                authorView.text = article.author.orEmpty()
                authorView.visibility = if (article.displayAuthor) View.VISIBLE else View.GONE

                container.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount() = articleModels.size

}
