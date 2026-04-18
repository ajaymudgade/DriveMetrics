package com.example.drivemetrics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.drivemetrics.UI.ClusterView
import com.example.drivemetrics.repository.CarRepositoryImpl
import com.example.drivemetrics.viewmodel.DashboardViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var clusterView: ClusterView   // 👈 IMPORTANT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Repository
        val repository = CarRepositoryImpl(
            MockCarDataSource()
        )

        // ViewModel
        viewModel = DashboardViewModel(repository)

        // UI View
        clusterView = ClusterView(this)

        setContentView(clusterView)

        // 👇 CALL FUNCTION HERE
        observeData()
    }

    // 👇 THIS IS WHERE YOUR CODE GOES
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {

                clusterView.speed = it.speed
                clusterView.fuel = it.fuelLevel

                // Fake RPM (since emulator doesn't provide it)
                clusterView.rpm = it.speed * 50

                clusterView.invalidate()
            }
        }
    }
}