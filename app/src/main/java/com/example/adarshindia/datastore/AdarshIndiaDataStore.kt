package com.coucouapp.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.adarshindia.utils.Constant.PREF_NAME
import com.example.adarshindia.utils.Constant.STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.system.measureTimeMillis


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = STORE_NAME,
    produceMigrations = { context -> listOf(SharedPreferencesMigration(context, PREF_NAME)) })


class AdarshIndiaDataStore @Inject constructor(private val context: Context) {

    suspend fun putString(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun putLong(key: String, value: Long) {
        val preferencesKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun putDouble(key: String, value: Double) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun getLong(Key: String): Long? {
        return try {
            val preferencesKey = longPreferencesKey(Key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getInt(Key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(Key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getBoolean(Key: String): Flow<Boolean?> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(Key)]
        }
    }

    suspend fun getDouble(Key: String): Double? {
        return try {
            val preferencesKey = doublePreferencesKey(Key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun clearDataStore() {
        context.dataStore.edit {
            it.clear()
        }
    }
}