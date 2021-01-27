package com.theathletic.interview.mvp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * The DataState interface indicates a class that represents the underlying State of a given
 * Presenter. This is often composed of data model classes and should not contain any UI code.
 * A DataState can be consistently updated as the Presenter receives updates from async triggers
 * such as network responses, user actions or other triggers.
 */
interface DataState

/**
 * The ViewState interface indicates a class that represents the current DataState into View models.
 * As the DataState updates, a Transformer will interpret those changes and create View models
 * accordingly (e.g. show/hide displays, convert Data models into display strings/values, etc.)
 */
interface ViewState

/**
 * The Transformer interface bridges a DataState and ViewState together so as the DataState is
 * updated, a ViewState is produced and sent to any observing View for rendering.
 *
 * e.g.
 *     override fun transform(data: ArticlesState): ArticlesContract.ViewState {
 *       return ArticlesContract.ViewState(
 *         showLoading = data.isLoading,
 *         articleModels = data.articles.map { it.toUiModel() }
 *       )
 *     }
 */
interface Transformer<in From, out To> {
    fun transform(data: From): To
}

/**
 * This class can be extended by Presenters to leverage the MVP framework. By doing so, there
 * are several things provided:
 * - strong typing of DataState and ViewState values to fulfill MVP contracts and connect to Views
 * - because it is also a ViewModel, it benefits from architecture components features
 * - a DataState channel instance that can be easily updated with the updateState function
 * - an observable ViewState that is automatically transformed via the transform interface and
 * observed by the BaseFragment
 * - an embedded event bus that can be observed by Views to communicate non-ViewState changes
 */
abstract class BasePresenter<
        DState : DataState,
        VState : ViewState
        > : ViewModel(),
    LifecycleObserver,
    Transformer<DState, VState> {

    /**
     * Implementing this ensures newly-created Views can render a reasonable, initial state.
     */
    protected abstract val initialState: DState

    val presenterScope get() = viewModelScope

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val state
        get() = _state.value
    private val _state by lazy { ConflatedBroadcastChannel(initialState) }

    @FlowPreview
    val viewState
        get() = _state
            .asFlow()
            .map { transform(it) }
            .distinctUntilChanged()

    /**
     * This function allows Presenters to update the DataState as the models change.
     * This can be done with a brand-new DataState instance or via incremental changes to the
     * current state:
     *
     * e.g.
     *
     * fun onSomeNetworkResponse(response: Response) {
     *   updateState { Contract.DataState(false, emptyList()) }
     * }
     *
     * or
     *
     * fun onSomeNetworkResponse(response: Response) {
     *   updateState {
     *     copy(isLoading = false, models = response.items)
     *   }
     * }
     */
    fun updateState(state: DState.() -> DState) {
        val newState = state(_state.value)
        _state.offer(newState)
    }

    private val eventBus = BroadcastChannel<Event>(1)

    @FlowPreview
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val eventConsumer: Flow<Event> = eventBus.asFlow()

    /**
     * This function allows Presenters to communicate Events with any observers (Views). ViewState
     * communication covers the majority of updates (re-renders), but there may be
     * other instances a ViewState cannot handle, such as displaying toasts, termination events,
     * navigation events, etc.
     *
     * e.g.
     * fun makeRequestForMandatoryData() {
     *   presenterScope.launch {
     *     val mandatoryData = repository.getThing()
     *     if (mandatoryData == null) {
     *       sendEvent(Contract.EarlyTerminationEvent)
     *     } else {
     *       // carry on
     *     }
     *   }
     * }
     */
    fun sendEvent(event: Event) {
        viewModelScope.launch { eventBus.send(event) }
    }

    /**
     * The observe function allows Views to collect events delivered via the sendEvent function.
     *
     * e.g. (in a View)
     * presenter.observe<Contract.Event>(this) { event ->
     *   when (event) {
     *     respond to different event types here
     *   }
     * }
     */
    @FlowPreview
    inline fun <reified T : Event> observe(
        lifecycleOwner: LifecycleOwner,
        crossinline onEvent: (T) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            eventConsumer.filterIsInstance<T>().collect { onEvent(it) }
        }
    }
}

