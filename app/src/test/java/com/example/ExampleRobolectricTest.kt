package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Hydratation", appName)
  }

  @Test
  fun `instantiate HydrationViewModel with factory`() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val factory = com.example.ui.HydrationViewModel.factory(app)
    val viewModel = factory.create(com.example.ui.HydrationViewModel::class.java)
    org.junit.Assert.assertNotNull(viewModel)
    assertEquals(2000, viewModel.uiState.value.targetMl)
  }
}
