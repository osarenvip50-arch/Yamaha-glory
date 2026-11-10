package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.FridayDao
import com.example.data.model.*

@Database(
    entities = [
        TaskItem::class,
        ProspectItem::class,
        UpworkJobItem::class,
        RemoteSession::class,
        MemoryItem::class,
        ProjectItem::class,
        ChatMessage::class,
        Oox1ModelItem::class,
        CreativeVideoProject::class,
        VoiceProductionItem::class,
        MusicTrackItem::class,
        CodeProjectItem::class,
        VerificationReport::class,
        SpecializedAgentItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class FridayDatabase : RoomDatabase() {
    abstract fun fridayDao(): FridayDao

    companion object {
        @Volatile
        private var INSTANCE: FridayDatabase? = null

        fun getDatabase(context: Context): FridayDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FridayDatabase::class.java,
                    "friday_assistant_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
