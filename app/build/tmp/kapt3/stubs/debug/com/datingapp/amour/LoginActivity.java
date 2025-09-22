package com.datingapp.amour;

/**
 * Activity for login.
 * Checks offline (Room DB) first, then retrieves user data if needed.
 * User can pick up where they left off.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/datingapp/amour/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnGoogle", "Landroid/widget/Button;", "btnLogin", "btnPhone", "db", "Lcom/datingapp/amour/data/AppDatabase;", "etEmail", "Landroid/widget/EditText;", "etPassword", "firebaseDB", "Lcom/google/firebase/database/DatabaseReference;", "tvSignUp", "Landroid/widget/TextView;", "loginUser", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.EditText etEmail;
    private android.widget.EditText etPassword;
    private android.widget.Button btnLogin;
    private android.widget.Button btnGoogle;
    private android.widget.Button btnPhone;
    private android.widget.TextView tvSignUp;
    private com.datingapp.amour.data.AppDatabase db;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.DatabaseReference firebaseDB = null;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Login with email/password
     */
    private final void loginUser() {
    }
}