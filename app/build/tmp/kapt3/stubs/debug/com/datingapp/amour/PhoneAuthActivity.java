package com.datingapp.amour;

/**
 * Handles phone number authentication via Firebase.
 * Flow:
 * 1. User enters phone number
 * 2. Firebase sends OTP
 * 3. User enters OTP
 * 4. Firebase verifies and signs them in
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/datingapp/amour/PhoneAuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "btnSendOtp", "Landroid/widget/Button;", "btnVerifyOtp", "etOtp", "Landroid/widget/EditText;", "etPhoneNumber", "progressBar", "Landroid/widget/ProgressBar;", "verificationId", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "sendOtp", "signInWithPhoneAuthCredential", "credential", "Lcom/google/firebase/auth/PhoneAuthCredential;", "verifyOtp", "app_debug"})
public final class PhoneAuthActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.EditText etPhoneNumber;
    private android.widget.EditText etOtp;
    private android.widget.Button btnSendOtp;
    private android.widget.Button btnVerifyOtp;
    private android.widget.ProgressBar progressBar;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String verificationId;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    
    public PhoneAuthActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Sends OTP to entered phone number
     */
    private final void sendOtp() {
    }
    
    /**
     * Verifies OTP entered by user
     */
    private final void verifyOtp() {
    }
    
    /**
     * Sign in user using Firebase credential
     */
    private final void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
    }
}