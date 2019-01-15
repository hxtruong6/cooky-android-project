package com.hxtruonglhsang.cooky.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.adapter.FoodInNewsfeedAdapter;
import com.hxtruonglhsang.cooky.adapter.FoodSavedAdapter;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.service.FoodService;
import com.hxtruonglhsang.cooky.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ArrayList<Food> saveFoods = new ArrayList<>();
    FoodSavedAdapter foodAdapter;
    private OnFragmentInteractionListener mListener;

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listviewSaved);


        UserService.getSavedFoodsByUserId(new UserService.ISavedFoodsByUserIdCallback() {
            @Override
            public void onCallback(List<String> foodIds) {
                Log.d("xxx foodIds", foodIds.toString());
                saveFoods.clear();
                if (foodIds.size() > 0) {
                    addFoodToListView(foodIds.get(0), listView);
                }
                for (int i = 1; i < foodIds.size(); i++) {
                    addFoodToListView(foodIds.get(i), null);
                }
            }
        });
        return view;
    }

    private void addFoodToListView(String foodId, final ListView listView) {
        FoodService.getFoodById(foodId, new FoodService.IFoodsCallback() {
            @Override
            public void onCallback(Food food) {
                saveFoods.add(food);
                Log.d("xxx food", "length" + saveFoods.size());

                if (saveFoods.size() == 1 && listView != null) {
                    foodAdapter = new FoodSavedAdapter(getActivity(), R.layout.saved, saveFoods);
                    listView.setAdapter(foodAdapter);
                } else {
                    foodAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
