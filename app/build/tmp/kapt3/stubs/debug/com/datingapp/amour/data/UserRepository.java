package com.datingapp.amour.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 #2\u00020\u0001:\u0001#B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\rJ\u0016\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u00182\u0006\u0010\u0011\u001a\u00020\fJ$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u001a\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u001e\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0011\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006$"}, d2 = {"Lcom/datingapp/amour/data/UserRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "database", "Lcom/google/firebase/database/DatabaseReference;", "profilesRef", "usersRef", "getProfileByEmail", "Lcom/datingapp/amour/data/UserProfile;", "email", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserByEmail", "Lcom/datingapp/amour/data/User;", "getUserByUid", "uid", "getUserProfile", "markProfileComplete", "Lkotlin/Result;", "", "markProfileComplete-gIAlu-s", "observeUser", "Lkotlinx/coroutines/flow/Flow;", "saveUser", "user", "saveUser-gIAlu-s", "(Lcom/datingapp/amour/data/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserProfile", "profile", "saveUserProfile-gIAlu-s", "(Lcom/datingapp/amour/data/UserProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLastLogin", "updateLastLogin-gIAlu-s", "Companion", "app_debug"})
public final class UserRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference usersRef = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference profilesRef = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.datingapp.amour.data.UserRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.datingapp.amour.data.UserRepository.Companion Companion = null;
    
    private UserRepository(android.content.Context context) {
        super();
    }
    
    /**
     * Get user by UID
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByUid(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.User> $completion) {
        return null;
    }
    
    /**
     * Get user by email
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.User> $completion) {
        return null;
    }
    
    /**
     * Observe user data changes in real-time
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.datingapp.amour.data.User> observeUser(@org.jetbrains.annotations.NotNull()
    java.lang.String uid) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.UserProfile> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProfileByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datingapp.amour.data.UserProfile> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/datingapp/amour/data/UserRepository$Companion;", "", "()V", "INSTANCE", "Lcom/datingapp/amour/data/UserRepository;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datingapp.amour.data.UserRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}