package org.tensorflow.lite.examples.objectdetection;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u00011B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001e\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\"\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\u00182\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J-\u0010!\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#2\u0006\u0010%\u001a\u00020&H\u0016\u00a2\u0006\u0002\u0010\'J\u0010\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020*H\u0016J\u000e\u0010+\u001a\u00020\u00182\u0006\u0010,\u001a\u00020-J\u0018\u0010.\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010/\u001a\u000200H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u00062"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/PhotoActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/tensorflow/lite/examples/objectdetection/OnSignDetectedAlert;", "()V", "activityStartBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/ActivityPhotoBinding;", "pickedBitMap", "Landroid/graphics/Bitmap;", "getPickedBitMap", "()Landroid/graphics/Bitmap;", "setPickedBitMap", "(Landroid/graphics/Bitmap;)V", "pickedPhoto", "Landroid/net/Uri;", "getPickedPhoto", "()Landroid/net/Uri;", "setPickedPhoto", "(Landroid/net/Uri;)V", "drawDetectionResult", "bitmap", "detectionResults", "", "Lorg/tensorflow/lite/examples/objectdetection/PhotoActivity$DetectionResult;", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onSignDetected", "detected", "", "pickPhoto", "view", "Landroid/view/View;", "runObjectDetection", "galleryPhoto", "Landroid/widget/ImageView;", "DetectionResult", "app_debug"})
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
    
    private final void runObjectDetection(android.graphics.Bitmap bitmap, android.widget.ImageView galleryPhoto) {
    }
    
    private final android.graphics.Bitmap drawDetectionResult(android.graphics.Bitmap bitmap, java.util.List<org.tensorflow.lite.examples.objectdetection.PhotoActivity.DetectionResult> detectionResults) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/PhotoActivity$DetectionResult;", "", "boundingBox", "Landroid/graphics/RectF;", "text", "", "(Landroid/graphics/RectF;Ljava/lang/String;)V", "getBoundingBox", "()Landroid/graphics/RectF;", "getText", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class DetectionResult {
        @org.jetbrains.annotations.NotNull()
        private final android.graphics.RectF boundingBox = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String text = null;
        
        @org.jetbrains.annotations.NotNull()
        public final org.tensorflow.lite.examples.objectdetection.PhotoActivity.DetectionResult copy(@org.jetbrains.annotations.NotNull()
        android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull()
        java.lang.String text) {
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
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public DetectionResult(@org.jetbrains.annotations.NotNull()
        android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull()
        java.lang.String text) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.graphics.RectF component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.graphics.RectF getBoundingBox() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getText() {
            return null;
        }
    }
}