package com.datingapp.amour;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0010H\u0002J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0010H\u0002J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/datingapp/amour/PhoneAuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "btnSendOtp", "Landroid/widget/Button;", "btnVerifyOtp", "otpInput", "Landroid/widget/EditText;", "phoneInput", "progressBar", "Landroid/widget/ProgressBar;", "resendToken", "Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "storedVerificationId", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "openMainApp", "uid", "openProfileSetup", "email", "sendVerificationCode", "phoneNumber", "signInWithPhoneAuthCredential", "credential", "Lcom/google/firebase/auth/PhoneAuthCredential;", "app_debug"})
public final class PhoneAuthActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.firebase.auth.FirebaseAuth auth;
    private android.widget.EditText phoneInput;
    private android.widget.EditText otpInput;
    private android.widget.Button btnSendOtp;
    private android.widget.Button btnVerifyOtp;
    private android.widget.ProgressBar progressBar;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String storedVerificationId;
    private com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken;
    
    public PhoneAuthActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void sendVerificationCode(java.lang.String phoneNumber) {
    }
    
    private final void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
    }
    
    private final void openProfileSetup(java.lang.String uid, java.lang.String email) {
    }
    
    private final void openMainApp(java.lang.String uid) {
    }
}