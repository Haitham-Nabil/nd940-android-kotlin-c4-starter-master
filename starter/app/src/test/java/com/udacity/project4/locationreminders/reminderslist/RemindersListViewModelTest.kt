package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    //TODO: provide testing to the RemindersListViewModel and its live data objects

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    //@ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var remiderViewModel: RemindersListViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var remiderRepository: FakeDataSource

    @Before
    fun setupStatisticsViewModel() {
        // Initialise the repository with no tasks.
        remiderRepository = FakeDataSource()

        remiderViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(),remiderRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun loadReminders_whenShowLoadingIsTure(){
        //When
        remiderViewModel.loadReminders()
        //Then
        assertEquals(remiderViewModel.remindersList.getOrAwaitValue(), emptyList<ReminderDataItem>())
    }

    @Test
    fun loadReminders_whenShowNoDataIsFalse(){
        //val item = ReminderDTO("Computer Mall", "ask for labtop price", "tanta", 30.23549 , 30.6548)
        //When
        remiderViewModel.showNoData.value = false
        //Then
        assertEquals(remiderViewModel.showNoData.getOrAwaitValue(), false)
    }
    @Test

    fun loadReminders_whenShowNoDataIstrue(){

        //When
        remiderViewModel.showNoData.value = true
        //Then
        assertEquals(remiderViewModel.showNoData.getOrAwaitValue(), true)
    }

}