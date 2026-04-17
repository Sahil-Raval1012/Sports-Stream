package com.example.sportsistream.istream

import android.content.Context

object SessionManager {
    private const val PREF = "istream_session"

    fun save(context: Context, id: Int, username: String, fullName: String) {
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit()
            .putInt("id", id)
            .putString("username", username)
            .putString("fullName", fullName)
            .apply()
    }

    fun getUserId(context: Context): Int? {
        val id = context.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("id", -1)
        return if (id == -1) null else id
    }

    fun getFullName(context: Context): String? =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE).getString("fullName", null)

    fun getUsername(context: Context): String? =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE).getString("username", null)

    fun isLoggedIn(context: Context): Boolean = getUserId(context) != null

    fun clear(context: Context) {
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().clear().apply()
    }
}
