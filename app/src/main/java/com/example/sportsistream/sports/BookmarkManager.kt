package com.example.sportsistream.sports

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object BookmarkManager {

    private const val PREF_NAME = "sports_bookmarks"
    private const val KEY = "bookmarked_items"
    private val gson = Gson()

    fun getAll(context: Context): MutableList<NewsItem> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, "[]") ?: "[]"
        val type = object : TypeToken<MutableList<NewsItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun isBookmarked(context: Context, id: String): Boolean =
        getAll(context).any { it.id == id }

    /** Returns true if the item was added, false if it was removed. */
    fun toggle(context: Context, item: NewsItem): Boolean {
        val list = getAll(context)
        val idx = list.indexOfFirst { it.id == item.id }
        return if (idx >= 0) {
            list.removeAt(idx)
            save(context, list)
            false
        } else {
            list.add(item)
            save(context, list)
            true
        }
    }

    private fun save(context: Context, list: List<NewsItem>) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY, gson.toJson(list))
            .apply()
    }
}
