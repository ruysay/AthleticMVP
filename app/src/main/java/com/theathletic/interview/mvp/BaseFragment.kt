package com.theathletic.interview.mvp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * This class can be extended by Fragments to leverage the MVP framework. By doing so,
 * there are several things provided:
 * - strong typing and initiation of a Presenter implementation that observes the fragment lifecycle
 * - collection of Presenter ViewState updates so the Fragment can re-render when necessary
 * - access to the Presenter Event flow for non-UI updates/triggers
 */
abstract class BaseFragment<T : BasePresenter<*, VS>, VS : ViewState> : Fragment() {

    lateinit var presenter: T
        private set

    /**
     * Because Presenter extends ViewModel, implementing classes can use architecture components
     * to retrieve the presenter with the getViewModel<>() function
     */
    abstract fun setupPresenter(): T

    /**
     * As models are updated and transformed, Presenter implementations should continually update
     * their state. This will automatically trigger a renderState invocation that Views
     * can use to update the UI.
     */
    abstract fun renderState(viewState: VS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This allows Presenters to listen to Fragment lifecycle events
        presenter = setupPresenter().apply {
            lifecycle.addObserver(this)
        }
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            presenter.viewState.collect {
                renderState(it)
            }
        }

        /*
        Implementing classes can override onViewCreated and also collect events sent from the
        Presenter.

        e.g.
            presenter.observe<Contract.Event>(this) { event ->
                when (event) {
                  // respond to different event types here
                }
            }
         */
    }
}

