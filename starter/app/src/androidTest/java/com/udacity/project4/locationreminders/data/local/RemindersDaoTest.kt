package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
//import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt
@get:Rule
var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    val list1 = ReminderDTO("Computer Mall", "ask for labtop price", "tanta", 30.23549 , 30.6548)
    val list2 = ReminderDTO("brother house", "say hello to friend", "tanta", 30.23545,30.5628)
    val list3 = ReminderDataItem("friend house", "say hello to friend", "tanta", 30.23545,30.5628)

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertReminderAndGetById() = runBlockingTest {

        database.reminderDao().saveReminder(list1)

        val result = database.reminderDao().getReminderById(list1.id)


        assertThat(result as ReminderDTO, notNullValue())
        assertThat(result.id, `is`(list1.id))
        assertThat(result.title, `is`(list1.title))
        assertThat(result.description, `is`(list1.description))
        assertThat(result.location, `is`(list1.location))
        assertThat(result.latitude, `is`(list1.latitude))
        assertThat(result.longitude, `is`(list1.longitude))
    }

    @Test
    fun getallremiders() = runBlockingTest {
        database.reminderDao().saveReminder(list1)
        database.reminderDao().saveReminder(list2)
        val result_all = database.reminderDao().getReminders()
        assertThat(result_all, `is`(notNullValue()))
    }

    @Test
    fun deleteallremiders() = runBlockingTest {
        database.reminderDao().saveReminder(list1)
        database.reminderDao().saveReminder(list2)
        database.reminderDao().deleteAllReminders()
        val result_deleted = database.reminderDao().getReminders()
        assertThat(result_deleted, `is`(emptyList()))
    }
}