package com.cleanarchitecturenotesapp.feature_note.domain.util

/**
 * Sealed class representing the order direction for sorting.
 */
sealed class OrderType {
    /**
     * Ascending order (A-Z, 0-9, oldest to newest).
     */
    data object Ascending: OrderType()
    
    /**
     * Descending order (Z-A, 9-0, newest to oldest).
     */
    data object Descending: OrderType()
}