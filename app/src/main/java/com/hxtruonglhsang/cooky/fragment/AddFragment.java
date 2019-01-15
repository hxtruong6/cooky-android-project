package com.hxtruonglhsang.cooky.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.model.Ingredient;
import com.hxtruonglhsang.cooky.model.Step;
import com.hxtruonglhsang.cooky.service.FoodService;
import com.hxtruonglhsang.cooky.utils.Constant;
import com.hxtruonglhsang.cooky.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AddFragment extends Fragment {
    private Button btnSave,btnAddIngredient,btnAddStep;
    private ImageView img;
    private ProgressBar progressBar;
    private LinearLayout parentLayout,parentLayoutStep;
    private TextView txtName,txtDesciption;
    private Food food;

    List<Ingredient> ingredientList;
    List<Step> stepList;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
        food=new Food();

        getElementFromFagment(view);

        return view;
    }

    private void getElementFromFagment(View view) {

        btnSave = (Button) view.findViewById(R.id.save);
        btnAddIngredient = (Button) view.findViewById(R.id.ingredientAdd);
        btnAddStep = (Button)view.findViewById(R.id.stepAdd) ;
        img = (ImageView) view.findViewById(R.id.selectImg);
        parentLayout= (LinearLayout) view.findViewById(R.id.parentLayout);
        parentLayoutStep= (LinearLayout) view.findViewById(R.id.parentLayoutStep);

        txtDesciption=(TextView)view.findViewById(R.id.txtDescription);
        txtName=(TextView)view.findViewById(R.id.txtName);

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

                ingredientList=getDataFromIngredient();
                stepList=getDataFromStep();
                String nameFood=txtName.getText().toString();

                food.setName(nameFood);
                food.setIngredients(ingredientList);
                food.setSteps(stepList);
                food.setUserId("user001");//test
                food.setDescription(txtDesciption.getText().toString());

                Utils.uploadImage(getContext(), img, "image", new Utils.UploadImageCallBack() {
                    @Override
                    public void onCallback(String url) {
                        List<String> images =new ArrayList<>();
                        images.add(url);
                        food.setImages(images);

                        FoodService.saveFood(food);
                    }
                });

            }
        });
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.ingredient_field, null);
                Button btnDelete = (Button)rowView.findViewById(R.id.deleteFiledIngradient);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parentLayout.removeView((View) v.getParent());
                    }
                });
                parentLayout.addView(rowView, parentLayout.getChildCount() - 1);
            }
        });
        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.step_field, null);
                TextView name=(TextView)rowView.findViewById(R.id.steapName);
                name.setText("Bước "+(parentLayoutStep.getChildCount()+1)+":");
                parentLayoutStep.addView(rowView, parentLayoutStep.getChildCount() - 1);
            }
        });
    }

    private List<Step> getDataFromStep() {
        List<Step> steps=new ArrayList<>();

        for (int i=0;i<parentLayoutStep.getChildCount();i++){
            Step step =new Step();
            View view =parentLayoutStep.getChildAt(i);

            EditText description= (EditText)view.findViewById(R.id.descriptionStep);

            step.setDescription(description.getText().toString());
            step.setStepNumber(i+1);

            steps.add(step);
        }

        return steps;
    }

    private List<Ingredient> getDataFromIngredient() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i=0;i<parentLayout.getChildCount();i++){
            Ingredient ingredient =new Ingredient();
            View view =parentLayout.getChildAt(i);
            EditText nameIngredient= (EditText)view.findViewById(R.id.nameIngedient);
            EditText amount= (EditText)view.findViewById(R.id.amountIngerdient);
            EditText type= (EditText)view.findViewById(R.id.typeIngerdient);

            ingredient.setName(nameIngredient.getText().toString());
            ingredient.setAmount(Float.parseFloat(amount.getText().toString()));
            ingredient.setType(type.getText().toString());

            ingredients.add(ingredient);
        }

        return ingredients;
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
