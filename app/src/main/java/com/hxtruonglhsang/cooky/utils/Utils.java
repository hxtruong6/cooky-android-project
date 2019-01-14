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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static ProgressBar progressBar;
    public static String uploadImage(final Context context, ImageView img, String nameImg, ProgressBar progressBarFrom) {

        progressBar=progressBarFrom;
        String downloadUrl="";

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

                Toast.makeText(context, "Upload Success", Toast.LENGTH_LONG).show();
                Log.i("FBApp1 URL ", url.toString());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                long persentUpload=taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()*100;
                if(persentUpload<100){
                    progressBar.setVisibility(View.VISIBLE); //to show
                }else  progressBar.setVisibility(View.GONE); // to hide

            }
        });
        return downloadUrl;
    }


    public static Object jsonToObject() {
        return null;
    }

    public static String objectToJson() {
        return null;
    }
}
