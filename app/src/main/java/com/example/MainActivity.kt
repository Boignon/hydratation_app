package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.HydrationScreen
import com.example.ui.HydrationViewModel
import com.example.ui.theme.HydrationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      HydrationTheme {
        val hydrationViewModel: HydrationViewModel = viewModel(
          factory = HydrationViewModel.factory(application)
        )
        HydrationScreen(viewModel = hydrationViewModel)
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
  androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}
