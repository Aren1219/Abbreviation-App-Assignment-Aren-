package com.example.abbreviationappassignmentaren.util

import com.example.abbreviationappassignmentaren.models.DefinitionsModel

sealed class UiState {
    object Loading: UiState()
    data class Success(val abbrevResponse: DefinitionsModel): UiState()
    data class Error(val error: Throwable): UiState()
}