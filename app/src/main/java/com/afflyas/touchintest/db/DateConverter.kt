package com.afflyas.touchintest.db

import androidx.room.TypeConverter
import java.sql.Date

/**
 *
 * Converter to be able to store Date objects in the database
 *
 */
object DateConverter {

    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }


    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}