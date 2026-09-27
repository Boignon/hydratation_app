package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.HydrationDatabase
import com.example.data.HydrationRecord
import com.example.data.HydrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HydrationUiState(
    val targetMl: Int = 2000,
    val currentMl: Int = 0,
    val records: List<HydrationRecord> = emptyList(),
    val isResetDialogOpen: Boolean = false,
    val lastAddedAmount: Int? = null,
    val isCustomDialogOpen: Boolean = false,
    val customAmountInput: String = "250",
    val showCelebration: Boolean = false
) {
    val progress: Float
        get() = if (targetMl > 0) (currentMl.toFloat() / targetMl).coerceIn(0f, 1f) else 0f

    val percentage: Int
        get() = if (targetMl > 0) ((currentMl.toFloat() / targetMl) * 100).toInt() else 0

    val remainingMl: Int
        get() = (targetMl - currentMl).coerceAtLeast(0)

    val isGoalReached: Boolean
        get() = currentMl >= targetMl
}

class HydrationViewModel(
    application: Application,
    private val repository: HydrationRepository
) : AndroidViewModel(application) {

    constructor(application: Application) : this(
        application,
        HydrationRepository(HydrationDatabase.getDatabase(application).hydrationDao())
    )

    private val _uiState = MutableStateFlow(HydrationUiState())
    val uiState: StateFlow<HydrationUiState> = _uiState.asStateFlow()

    init {
        observeTodayRecords()
    }

    private fun observeTodayRecords() {
        viewModelScope.launch {
            repository.getTodayRecords().collect { recordsList ->
                val total = recordsList.sumOf { it.amountMl }
                val previousTotal = _uiState.value.currentMl
                val target = _uiState.value.targetMl
                val justReachedGoal = previousTotal < target && total >= target

                _uiState.update { current ->
                    current.copy(
                        currentMl = total,
                        records = recordsList,
                        showCelebration = justReachedGoal || (current.showCelebration && total >= target)
                    )
                }
            }
        }
    }

    fun addWater(amountMl: Int = 250) {
        if (amountMl <= 0) return
        viewModelScope.launch {
            repository.addWater(amountMl)
            _uiState.update { it.copy(lastAddedAmount = amountMl) }
        }
    }

    fun reset() {
        viewModelScope.launch {
            repository.resetToday()
            _uiState.update {
                it.copy(
                    isResetDialogOpen = false,
                    lastAddedAmount = null,
                    showCelebration = false
                )
            }
        }
    }

    fun deleteRecord(id: Long) {
        viewModelScope.launch {
            repository.deleteRecord(id)
        }
    }

    fun setResetDialogOpen(open: Boolean) {
        _uiState.update { it.copy(isResetDialogOpen = open) }
    }

    fun setCustomDialogOpen(open: Boolean) {
        _uiState.update { it.copy(isCustomDialogOpen = open) }
    }

    fun updateCustomAmountInput(input: String) {
        val filtered = input.filter { it.isDigit() }.take(4)
        _uiState.update { it.copy(customAmountInput = filtered) }
    }

    fun addCustomAmount() {
        val amount = _uiState.value.customAmountInput.toIntOrNull() ?: 250
        if (amount in 10..5000) {
            addWater(amount)
            setCustomDialogOpen(false)
        }
    }

    fun dismissCelebration() {
        _uiState.update { it.copy(showCelebration = false) }
    }

    companion object {
        fun factory(application: Application): androidx.lifecycle.ViewModelProvider.Factory =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return HydrationViewModel(application) as T
                }
            }
    }
}
