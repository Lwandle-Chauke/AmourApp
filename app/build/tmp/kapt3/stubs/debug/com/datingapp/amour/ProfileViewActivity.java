package com.datingapp.amour;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001c\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\b\u0010\u001f\u001a\u00020\u001cH\u0002J\b\u0010 \u001a\u00020\u001cH\u0002J\b\u0010!\u001a\u00020\u001cH\u0002J\u0012\u0010\"\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\b\u0010#\u001a\u00020\u001cH\u0002J\u0012\u0010$\u001a\u00020\u001c2\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\b\u0010\'\u001a\u00020\u001cH\u0014J\b\u0010(\u001a\u00020\u001cH\u0002J\b\u0010)\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/datingapp/amour/ProfileViewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "bottomNavigation", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "btnEditProfile", "Landroid/widget/Button;", "btnSaveContinue", "photoContainer", "Landroid/widget/LinearLayout;", "tvBio", "Landroid/widget/TextView;", "tvGender", "tvInterestsLifestyle", "tvLocation", "tvLookingFor", "tvNameAge", "tvOrientation", "tvPreferences", "tvPrompts", "userRepository", "Lcom/datingapp/amour/data/UserRepository;", "userUid", "", "buildPromptsText", "profile", "Lcom/datingapp/amour/data/UserProfile;", "displayUserData", "", "user", "Lcom/datingapp/amour/data/User;", "getUserUid", "initializeDependencies", "initializeViews", "loadImages", "loadProfileData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "setupClickListeners", "setupNavigation", "app_debug"})
public final class ProfileViewActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.datingapp.amour.data.UserRepository userRepository;
    private java.lang.String userUid;
    private android.widget.LinearLayout photoContainer;
    private android.widget.TextView tvNameAge;
    private android.widget.TextView tvGender;
    private android.widget.TextView tvOrientation;
    private android.widget.TextView tvBio;
    private android.widget.TextView tvPrompts;
    private android.widget.TextView tvLookingFor;
    private android.widget.TextView tvInterestsLifestyle;
    private android.widget.TextView tvPreferences;
    private android.widget.TextView tvLocation;
    private android.widget.Button btnEditProfile;
    private android.widget.Button btnSaveContinue;
    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigation;
    
    public ProfileViewActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeDependencies() {
    }
    
    private final void getUserUid() {
    }
    
    private final void initializeViews() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void setupNavigation() {
    }
    
    private final void loadProfileData() {
    }
    
    private final void displayUserData(com.datingapp.amour.data.User user, com.datingapp.amour.data.UserProfile profile) {
    }
    
    private final java.lang.String buildPromptsText(com.datingapp.amour.data.UserProfile profile) {
        return null;
    }
    
    private final void loadImages(com.datingapp.amour.data.User user) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
}