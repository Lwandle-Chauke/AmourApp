package com.datingapp.amour;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0002J\b\u0010\u001c\u001a\u00020\u001aH\u0002J\b\u0010\u001d\u001a\u00020\u001aH\u0002J\u0012\u0010\u001e\u001a\u00020\u001a2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\u0010\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/datingapp/amour/InterestsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "btnBack", "Landroid/widget/ImageView;", "btnSaveInterests", "Landroid/widget/Button;", "cbFriends", "Landroid/widget/CheckBox;", "cbLongTerm", "cbShortTerm", "etBio", "Landroid/widget/EditText;", "etEducation", "etFriends", "etLifeGoal", "etWeekend", "flexHobbies", "Lcom/google/android/flexbox/FlexboxLayout;", "userEmail", "", "userRepository", "Lcom/datingapp/amour/data/UserRepository;", "getUserEmail", "", "initializeDependencies", "initializeUI", "loadExistingData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "populateUI", "profile", "Lcom/datingapp/amour/data/UserProfile;", "saveProfile", "app_debug"})
public final class InterestsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.datingapp.amour.data.UserRepository userRepository;
    private com.google.firebase.auth.FirebaseAuth auth;
    private android.widget.EditText etBio;
    private android.widget.EditText etLifeGoal;
    private android.widget.EditText etWeekend;
    private android.widget.EditText etFriends;
    private android.widget.CheckBox cbFriends;
    private android.widget.CheckBox cbShortTerm;
    private android.widget.CheckBox cbLongTerm;
    private com.google.android.flexbox.FlexboxLayout flexHobbies;
    private android.widget.EditText etEducation;
    private android.widget.Button btnSaveInterests;
    private android.widget.ImageView btnBack;
    private java.lang.String userEmail;
    
    public InterestsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeDependencies() {
    }
    
    private final void getUserEmail() {
    }
    
    private final void initializeUI() {
    }
    
    private final void loadExistingData() {
    }
    
    private final void populateUI(com.datingapp.amour.data.UserProfile profile) {
    }
    
    private final void saveProfile() {
    }
}