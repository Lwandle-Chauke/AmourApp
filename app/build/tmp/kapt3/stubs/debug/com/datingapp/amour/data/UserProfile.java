package com.datingapp.amour.data;

/**
 * UserProfile entity for profile details.
 * Links to User via uid.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ,2\u00020\u0001:\u0001,BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003Jm\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\u0014\u0010)\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010*J\t\u0010+\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006-"}, d2 = {"Lcom/datingapp/amour/data/UserProfile;", "", "uid", "", "email", "bio", "prompt1", "prompt2", "prompt3", "interests", "agePreference", "distancePreference", "lookingFor", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAgePreference", "()Ljava/lang/String;", "getBio", "getDistancePreference", "getEmail", "getInterests", "getLookingFor", "getPrompt1", "getPrompt2", "getPrompt3", "getUid", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toMap", "", "toString", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "user_profiles")
public final class UserProfile {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String uid = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bio = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String prompt1 = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String prompt2 = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String prompt3 = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String interests = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String agePreference = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String distancePreference = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lookingFor = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.datingapp.amour.data.UserProfile.Companion Companion = null;
    
    public UserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String bio, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt1, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt2, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt3, @org.jetbrains.annotations.NotNull()
    java.lang.String interests, @org.jetbrains.annotations.NotNull()
    java.lang.String agePreference, @org.jetbrains.annotations.NotNull()
    java.lang.String distancePreference, @org.jetbrains.annotations.NotNull()
    java.lang.String lookingFor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrompt1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrompt2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrompt3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInterests() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAgePreference() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDistancePreference() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLookingFor() {
        return null;
    }
    
    /**
     * Convert to Firebase-friendly map
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> toMap() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datingapp.amour.data.UserProfile copy(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String bio, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt1, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt2, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt3, @org.jetbrains.annotations.NotNull()
    java.lang.String interests, @org.jetbrains.annotations.NotNull()
    java.lang.String agePreference, @org.jetbrains.annotations.NotNull()
    java.lang.String distancePreference, @org.jetbrains.annotations.NotNull()
    java.lang.String lookingFor) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/datingapp/amour/data/UserProfile$Companion;", "", "()V", "fromMap", "Lcom/datingapp/amour/data/UserProfile;", "map", "", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datingapp.amour.data.UserProfile fromMap(@org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
            return null;
        }
    }
}