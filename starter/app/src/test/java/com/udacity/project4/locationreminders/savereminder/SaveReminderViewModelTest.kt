package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {


    //TODO: provide testing to the SaveReminderView and its live data objects
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    //@ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var saveremiderViewModel: SaveReminderViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var saveremiderRepository: FakeDataSource

    @Before
    fun setupStatisticsViewModel() {
        // Initialise the repository with no tasks.
        saveremiderRepository = FakeDataSource()

        saveremiderViewModel = SaveReminderViewModel(ApplicationProvider.getApplicationContext(),saveremiderRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    val list1 = ReminderDataItem("Computer Mall", "ask for labtop price", "tanta", 30.23549 , 30.6548)
    val list2 = ReminderDataItem(null, "say hello to friend", "tanta", 30.23545,30.5628)
    val list3 = ReminderDataItem("friend house", "say hello to friend", null, 30.23545,30.5628)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveReminders_addItem_successfullyadditem() = runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        saveremiderViewModel.saveReminder(list1)
        assertThat(saveremiderViewModel.showLoading.getOrAwaitValue()).isTrue()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun validateEnteredData_addItemnulltitle_validateMsgEnterTitle() = runBlockingTest {
        assertThat(saveremiderViewModel.validateEnteredData(list2)).isFalse()
        assertThat(saveremiderViewModel.showSnackBarInt.getOrAwaitValue()).isEqualTo(R.string.err_enter_title)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun validateEnteredData_addItemNullDesc_validateMsgEnterlocation() = runBlockingTest {
        assertThat(saveremiderViewModel.validateEnteredData(list3)).isFalse()
        assertThat(saveremiderViewModel.showSnackBarInt.getOrAwaitValue()).isEqualTo(R.string.err_select_location)
    }

   /* @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteallremiders_removeData_returnZero() = runBlockingTest {

        saveremiderRepository.deleteAllReminders()
        val savedRemiders = saveremiderRepository.getReminders()

        assertEquals(0,savedRemiders)

    }
*/
}