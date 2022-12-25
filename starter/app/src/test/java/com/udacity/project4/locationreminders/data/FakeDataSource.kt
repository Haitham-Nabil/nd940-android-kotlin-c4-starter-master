package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var remiderList: MutableList<ReminderDTO>? = mutableListOf()) : ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source
var whenError = false

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        //TODO("Return the reminders")
        if (whenError) return Result.Error("Reminder Error")
        remiderList?.let { return Result.Success(it) }
        return Result.Error("Reminder Error")
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
       // TODO("save the reminder")
        remiderList?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        //TODO("return the reminder with the id")
        val reminder = remiderList?.find { reminderDTO ->
            reminderDTO.id == id
        }
        return if (reminder!=null){
            reminder?.let { Result.Success(it) }
        }else{
            Result.Error("Remider Error not found by id")
        }
    }

    override suspend fun deleteAllReminders() {
        //TODO("delete all the reminders")
        remiderList?.clear()
    }


}