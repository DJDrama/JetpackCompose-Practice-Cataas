package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import com.dj.android.catassjetpackcompose.domain.utils.Result
import com.dj.android.catassjetpackcompose.presentation.utils.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetsViewModel(
    private val petsRepository: PetsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PetsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPets()
    }

    fun updateCat(cat: Cat) {
        viewModelScope.launch {
            petsRepository.updateCat(cat = cat)
        }
    }

    fun getFavoritePets() {
        viewModelScope.launch {
            petsRepository.getFavoritePets().collect { favorites ->
                _uiState.update {
                    it.copy(favorites = favorites)
                }
            }
        }
    }

    fun getPets() {
        _uiState.value = PetsUiState(isLoading = true)
        viewModelScope.launch {
            petsRepository.getPets().asResult().collect {
                when (it) {
                    is Result.Error ->
                        _uiState.update { state ->
                            state.copy(
                                error = it.error,
                                isLoading = false,
                            )
                        }

                    is Result.Success ->
                        _uiState.update { state ->
                            state.copy(
                                pets = it.data,
                                isLoading = false,
                            )
                        }
                }
            }
        }
    }
}

data class PetsUiState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val favorites: List<Cat> = emptyList(),
    val error: String? = null,
)