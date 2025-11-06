package com.cleanarchitecturenotesapp.feature_note.domain.util

sealed class NoteOrder(
    open val orderType: OrderType
) {
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)
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