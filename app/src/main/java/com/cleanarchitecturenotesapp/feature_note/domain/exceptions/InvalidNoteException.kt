package com.cleanarchitecturenotesapp.feature_note.domain.exceptions

/**
 * Exception thrown when a note validation fails.
 * Typically thrown when title or content is blank.
 *
 * @param message Error message describing the validation failure
 */
class InvalidNoteException(message: String): Exception(message)