package com.cleanarchitecturenotesapp.feature_note.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}