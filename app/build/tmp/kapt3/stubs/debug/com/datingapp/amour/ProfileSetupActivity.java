package com.datingapp.amour;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\"\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u00042\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\u0012\u0010&\u001a\u00020 2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0014J\b\u0010)\u001a\u00020 H\u0002J\b\u0010*\u001a\u00020 H\u0002J\u001e\u0010+\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010.R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0011X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/datingapp/amour/ProfileSetupActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "PICK_IMAGE_REQUEST", "", "btnBack", "Landroid/widget/ImageView;", "btnSave", "Landroid/widget/Button;", "currentImageIndex", "etAge", "Landroid/widget/EditText;", "etFullName", "etLocation", "gridPictures", "Landroidx/gridlayout/widget/GridLayout;", "imageUris", "", "Landroid/net/Uri;", "[Landroid/net/Uri;", "imgProfile", "profileUri", "rgGender", "Landroid/widget/RadioGroup;", "rgOrientation", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "userEmail", "", "userRepository", "Lcom/datingapp/amour/data/UserRepository;", "loadExistingUser", "", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "pickImageFromGallery", "saveProfile", "uploadImage", "uri", "filename", "(Landroid/net/Uri;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProfileSetupActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.ImageView btnBack;
    private android.widget.ImageView imgProfile;
    private androidx.gridlayout.widget.GridLayout gridPictures;
    private android.widget.EditText etFullName;
    private android.widget.EditText etAge;
    private android.widget.RadioGroup rgGender;
    private android.widget.RadioGroup rgOrientation;
    private android.widget.EditText etLocation;
    private android.widget.Button btnSave;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri profileUri;
    @org.jetbrains.annotations.NotNull()
    private final android.net.Uri[] imageUris = null;
    private int currentImageIndex = -1;
    private final int PICK_IMAGE_REQUEST = 101;
    private com.datingapp.amour.data.UserRepository userRepository;
    private com.google.firebase.storage.FirebaseStorage storage;
    private java.lang.String userEmail;
    
    public ProfileSetupActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void pickImageFromGallery() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void loadExistingUser() {
    }
    
    private final void saveProfile() {
    }
    
    private final java.lang.Object uploadImage(android.net.Uri uri, java.lang.String filename, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
}