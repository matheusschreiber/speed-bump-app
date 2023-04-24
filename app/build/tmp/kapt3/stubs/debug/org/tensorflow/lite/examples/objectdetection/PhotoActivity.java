package org.tensorflow.lite.examples.objectdetection;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0012\u0010\u0019\u001a\u00020\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J-\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020!H\u0016\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0016J\u000e\u0010&\u001a\u00020\u00132\u0006\u0010\'\u001a\u00020(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006)"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/PhotoActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/tensorflow/lite/examples/objectdetection/OnSignDetectedAlert;", "()V", "activityStartBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/ActivityPhotoBinding;", "pickedBitMap", "Landroid/graphics/Bitmap;", "getPickedBitMap", "()Landroid/graphics/Bitmap;", "setPickedBitMap", "(Landroid/graphics/Bitmap;)V", "pickedPhoto", "Landroid/net/Uri;", "getPickedPhoto", "()Landroid/net/Uri;", "setPickedPhoto", "(Landroid/net/Uri;)V", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onSignDetected", "detected", "", "pickPhoto", "view", "Landroid/view/View;", "app_debug"})
public final class PhotoActivity extends androidx.appcompat.app.AppCompatActivity implements org.tensorflow.lite.examples.objectdetection.OnSignDetectedAlert {
    private org.tensorflow.lite.examples.objectdetection.databinding.ActivityPhotoBinding activityStartBinding;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri pickedPhoto;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.Bitmap pickedBitMap;
    private java.util.HashMap _$_findViewCache;
    
    public PhotoActivity() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri getPickedPhoto() {
        return null;
    }
    
    public final void setPickedPhoto(@org.jetbrains.annotations.Nullable()
    android.net.Uri p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getPickedBitMap() {
        return null;
    }
    
    public final void setPickedBitMap(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void pickPhoto(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    public void onSignDetected(boolean detected) {
    }
}