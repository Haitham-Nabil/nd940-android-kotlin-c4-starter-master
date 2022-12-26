package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
//import com.udacity.project4.locationreminders.data.dto.ReminderDTO
//import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.annotations.NotNull
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class RemindersLocalRepositoryTest {

//    TODO: Add testing implementation to the RemindersLocalRepository.kt
@get:Rule
var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersLocalRepository: RemindersLocalRepository

    private lateinit var database: RemindersDatabase
    val list1 = ReminderDTO("Computer Mall", "ask for labtop price", "tanta", 30.23549 , 30.6548)


    @Before
    fun setup() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        remindersLocalRepository = RemindersLocalRepository(
            database.reminderDao(),
            Dispatchers.Main
        )
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveReminders_addnewReminder_Addsuccessfully() = runBlocking {

        remindersLocalRepository.saveReminder(list1)

        val reminders = remindersLocalRepository.getReminders() as? Result.Success
        val result2 = reminders?.data?.size

        assertEquals(1,result2)
    }

    @Test
    fun deleteAllReminders_EmptyList()= runBlocking {

        remindersLocalRepository.saveReminder(list1)
        remindersLocalRepository.deleteAllReminders()

        val remindersData = remindersLocalRepository.getReminders() as Result.Success

        assertThat(remindersData.data, `is` (emptyList()))
    }


}