package com.dj.android.catassjetpackcompose.data.db.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CatsTypeConverters {
    @TypeConverter
    fun convertTagsToString(tags: List<String>): String {
        return Json.encodeToString(value = tags)
    }

    @TypeConverter
    fun convertStringToTags(tags: String): List<String> {
        return Json.decodeFromString(string = tags)
    }
}