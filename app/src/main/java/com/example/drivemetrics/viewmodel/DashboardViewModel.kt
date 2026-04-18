package com.example.drivemetrics.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivemetrics.repository.CarRepository
import com.example.drivemetrics.Constants
import com.example.drivemetrics.data.TelemetryData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: CarRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TelemetryData())
    val state: StateFlow<TelemetryData> = _state

    init {
        viewModelScope.launch {
            repository.getTelemetry().collect {
                Log.d(Constants.TAG, "ViewModel received data: $it")
                _state.value = it
            }
        }
    }
}