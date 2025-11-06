package com.cleanarchitecturenotesapp.feature_note.domain.util

/**
 * Sealed class representing different ordering options for notes.
 *
 * @property orderType The type of ordering (Ascending or Descending)
 */
sealed class NoteOrder(
    open val orderType: OrderType
) {
    /**
     * Order notes by title.
     */
    class Title(orderType: OrderType): NoteOrder(orderType)
    
    /**
     * Order notes by date/timestamp.
     */
    class Date(orderType: OrderType): NoteOrder(orderType)
    
    /**
     * Order notes by color.
     */
    class Color(orderType: OrderType): NoteOrder(orderType)
    
    /**
     * Order notes by favorite/wishlist status.
     */
    class Fav(orderType: OrderType): NoteOrder(orderType)

    /**
     * Creates a copy of this NoteOrder with a different order type.
     *
     * @param orderType The new order type to apply
     * @return A new NoteOrder instance with the specified order type
     */
    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
            is Fav -> Fav(orderType)
        }
    }
}