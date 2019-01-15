package com.hxtruonglhsang.cooky.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hxtruonglhsang.cooky.model.Food;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static String downloadUrl = "";

    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static ProgressBar progressBar;
    public static void uploadImage(final Context context, final ImageView img, String nameImg, final UploadImageCallBack uploadImageCallBack) {

        StorageReference storageRef = storage.getReferenceFromUrl(Constant.URL_IMAGE_STORAGE);

        Calendar calendar =Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child(nameImg+calendar.getTimeInMillis()+".png");

        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url = uri.getResult();

                Toast.makeText(context, "Upload thành công.", Toast.LENGTH_LONG).show();

                uploadImageCallBack.onCallback(url.toString());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Đang xử lý ...", Toast.LENGTH_LONG).show();
            }
        });
    }


    public static Object jsonToObject() {
        return null;
    }

    public static String objectToJson() {
        return null;
    }

    public interface UploadImageCallBack {
        void onCallback(String url);
    }
}
