package com.hxtruonglhsang.cooky.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.utils.Constant;
import com.hxtruonglhsang.cooky.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddFragment extends Fragment {
    private Button btnSave;
    private ImageView img;
    private ProgressBar progressBar;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        getElementFromFagment(view);

        return view;
    }

    private void getElementFromFagment(View view) {
        btnSave = (Button) view.findViewById(R.id.save);
        img = (ImageView) view.findViewById(R.id.selectImg);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Constant.REQUEST_CODE_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.uploadImage(getContext(),img,"image",progressBar);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constant.REQUEST_CODE_IMAGE && resultCode == Constant.RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
