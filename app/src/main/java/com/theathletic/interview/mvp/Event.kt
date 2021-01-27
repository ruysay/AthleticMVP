package com.theathletic.interview.mvp

/**
 * Base class for sealed Event classes that communicate non-state changes from a Presenter to a View.
 *
 * e.g.
 *
 * [in ArticleListContract]
 * ...
 *   interface ArticleListContract {
 *     sealed class Event : com.theathletic.interview.mvp.Event() {
 *       class ShowItemLongClickSheet(val itemId: Long) : Event()
 *       object ScrollToTopOfList : Event()
 *     }
 *   }
 * ...
 *
 * [in ArticleListFragment]
 * ...
 *    presenter.observe<ArticleListContract.Event>(this) { event ->
 *      when (event) {
 *        is ArticleListContract.Event.ShowItemLongClickSheet -> showLongClick(event.itemId)
 *        is ArticleListContract.Event.ScrollToTopOfList -> scrollToTopOfList()
 *      }
 *    }
 * ...
 */
abstract class Event