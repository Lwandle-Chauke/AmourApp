package com.datingapp.amour.data;

/**
 * Repository for managing user and user profile data.
 * Handles saving to BOTH Room (offline) and Firebase (online).
 * Also supports syncing Firebase → Room for keeping local data fresh.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0013J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000eJ\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/datingapp/amour/data/UserRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "db", "Lcom/datingapp/amour/data/AppDatabase;", "firebaseDb", "Lcom/google/firebase/database/DatabaseReference;", "profileDao", "Lcom/datingapp/amour/data/UserProfileDao;", "userDao", "Lcom/datingapp/amour/data/UserDao;", "getProfile", "Lcom/datingapp/amour/data/UserProfile;", "email", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserByEmail", "Lcom/datingapp/amour/data/User;", "saveUser", "", "user", "saveUserProfile", "profile", "syncProfileFromFirebase", "syncUserFromFirebase", "Companion", "app_debug"})
public final class UserRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.datingapp.amour.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datingapp.amour.data.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datingapp.amour.data.UserProfileDao profileDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference firebaseDb = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.datingapp.amour.data.UserRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.datingapp.amour.data.UserRepository.Companion Companion = null;
    
    private UserRepository(android.content.Context context) {
        super();
    }
    
    /**
     * Save user to Room and Firebase.
     */
    public final void saveUser(@org.jetbrains.annotations.NotNull()
    com.datingapp.amour.data.User user) {
    }
    
    /**
     * Save user profile to Room and Firebase.
     */
    public final void saveUserProfile(@org.jetbrains.annotations.NotNull()
    com.datingapp.amour.data.UserProfile profile) {
    }
    
    /**
     * Fetch user from local DB (offline-first).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.User> $completion) {
        return null;
    }
    
    /**
     * Fetch profile from local DB (offline-first).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.UserProfile> $completion) {
        return null;
    }
    
    /**
     * Sync latest user data from Firebase → Room.
     * Keeps local DB fresh when online.
     */
    public final void syncUserFromFirebase(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    /**
     * Sync latest user profile data from Firebase → Room.
     */
    public final void syncProfileFromFirebase(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/datingapp/amour/data/UserRepository$Companion;", "", "()V", "INSTANCE", "Lcom/datingapp/amour/data/UserRepository;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Singleton pattern: get repository instance.
         */
        @org.jetbrains.annotations.NotNull()
        public final com.datingapp.amour.data.UserRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}