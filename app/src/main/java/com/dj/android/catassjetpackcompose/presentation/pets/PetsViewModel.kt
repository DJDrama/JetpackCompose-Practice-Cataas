package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import com.dj.android.catassjetpackcompose.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetsViewModel(
    private val petsRepository: PetsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPets()
    }

    private fun getPets() {
        _uiState.value = PetsUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = petsRepository.getPets()) {
                is Result.Error -> _uiState.update {
                    it.copy(
                        error = result.error,
                        isLoading = false
                    )
                }

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            pets = result.data,
                            isLoading = false
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
    val error: String? = null
)