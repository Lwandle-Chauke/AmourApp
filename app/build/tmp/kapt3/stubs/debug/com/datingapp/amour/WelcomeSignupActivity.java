package com.datingapp.amour;

/**
 * Activity for user signup.
 * Saves user both offline (Room) and online (Firebase Realtime Database)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J$\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001cH\u0002J$\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/datingapp/amour/WelcomeSignupActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnGoogle", "Landroid/widget/Button;", "btnPhone", "btnSignUp", "db", "Lcom/datingapp/amour/data/AppDatabase;", "etConfirmPassword", "Landroid/widget/EditText;", "etEmail", "etName", "etPassword", "firebaseDB", "Lcom/google/firebase/database/DatabaseReference;", "tvLogin", "Landroid/widget/TextView;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onGoogleClicked", "onPhoneClicked", "onSignUpClicked", "openLogin", "saveUserOffline", "name", "", "email", "passwordHash", "saveUserOnline", "app_debug"})
public final class WelcomeSignupActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.EditText etName;
    private android.widget.EditText etEmail;
    private android.widget.EditText etPassword;
    private android.widget.EditText etConfirmPassword;
    private android.widget.Button btnSignUp;
    private android.widget.Button btnGoogle;
    private android.widget.Button btnPhone;
    private android.widget.TextView tvLogin;
    private com.datingapp.amour.data.AppDatabase db;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference firebaseDB = null;
    
    public WelcomeSignupActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Handle email/password signup
     */
    private final void onSignUpClicked() {
    }
    
    /**
     * Save user in Room DB (offline)
     */
    private final void saveUserOffline(java.lang.String name, java.lang.String email, java.lang.String passwordHash) {
    }
    
    /**
     * Save user in Firebase Realtime Database (online)
     */
    private final void saveUserOnline(java.lang.String name, java.lang.String email, java.lang.String passwordHash) {
    }
    
    private final void onGoogleClicked() {
    }
    
    private final void onPhoneClicked() {
    }
    
    private final void openLogin() {
    }
}