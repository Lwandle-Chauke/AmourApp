package com.datingapp.amour.data;

/**
 * The Room database for the Amour app.
 * Contains two entities: User and UserProfile.
 * Version 1: initial release.
 *
 * NOTE: We are NOT disabling exportSchema.
 * This allows Room to export schema history JSON files
 * into the directory we configure in build.gradle.kts.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/datingapp/amour/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "userDao", "Lcom/datingapp/amour/data/UserDao;", "userProfileDao", "Lcom/datingapp/amour/data/UserProfileDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.datingapp.amour.data.User.class, com.datingapp.amour.data.UserProfile.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.datingapp.amour.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.datingapp.amour.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.datingapp.amour.data.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.datingapp.amour.data.UserProfileDao userProfileDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/datingapp/amour/data/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/datingapp/amour/data/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Singleton pattern: ensures a single database instance across threads.
         */
        @org.jetbrains.annotations.NotNull()
        public final com.datingapp.amour.data.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}