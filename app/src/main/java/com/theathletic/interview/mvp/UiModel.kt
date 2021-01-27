package com.theathletic.interview.mvp

interface UiModel {

    /**
     * An ID used to represent the data models the [UiModel] represents. This should be unique
     * for the given view it represents.
     */
    val stableId: String
}
