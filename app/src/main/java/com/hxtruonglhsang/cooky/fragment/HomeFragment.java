
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.adapter.FoodInNewsfeedAdapter;
import com.hxtruonglhsang.cooky.model.Food;
import com.hxtruonglhsang.cooky.service.FoodService;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listViewNewsfeed = (ListView) view.findViewById(R.id.listviewNewsfeed);

//        final Food food = new Food();
//        List<String> img = new ArrayList<>();
//        img.add("https://static.vietnammm.com/images/restaurants/vn/NP373OQ/products/mi-quang-dac-biet.png");
//
//        food.setName("Mỳ quảng");
//        food.setImages(img);
//        food.setUserId("lhsang");
//        food.setLikes(img);
//        food.setDescription("Mì Quảng là một món ăn đặc trưng của Quảng Nam, Việt Nam, cùng với món cao lầu.\n" +
//                "\n" +
//                "Mì Quảng thường được làm từ sợi mì bằng bột gạo xay mịn và tráng thành từng lớp bánh mỏng, sau đó thái theo chiều ngang để có những sợi mì mỏng khoảng 2mm. Sợi mì làm bằng bột mỳ được trộn thêm một số phụ gia cho đạt độ giòn, dai. Dưới lớp mì là các loại rau sống, trên mì là thịt heo nạc, tôm, thịt gà cùng với nước dùng được hầm từ xương heo. Người ta còn bỏ thêm đậu phụng rang khô và giã dập, hành lá thái nhỏ, rau thơm, ớt đỏ... Thông thường nước dùng rất ít.");
        final ArrayList<Food> foods = new ArrayList<>();

        // start test data
        //ArrayList<Food> foods = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String message = dataSnapshot.toString();
//                    Log.d("xxx all:", "Value is: " + message);
                    for (DataSnapshot foodDataSnapshot : dataSnapshot.getChildren()) {
                        Food foodData = foodDataSnapshot.getValue(Food.class);
                        foods.add(foodData);
//                        Log.d("xxx food", "Value is: " + foodData.toString());
                        FoodService.getFoodIngredientsById(foodDataSnapshot.getKey());
                        FoodService.getFoodLikesById(foodDataSnapshot.getKey(), new IFoodLikesCallback() {
                            @Override
                            public void onCallback(List<String> likes) {
                                Log.d("xxx likes: ", likes.toString());
                            }
                        });
                    }

                    // add to food Adapter
                    FoodInNewsfeedAdapter foodAdapter = new FoodInNewsfeedAdapter(getActivity(), R.layout.newsfeed, foods);
                    listViewNewsfeed.setAdapter(foodAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("xxx Error", "Failed to read value.", error.toException());
            }
        };
        FoodService.getAllFood(valueEventListener);
        // end test data

//        foods.add(food);
//        foods.add(food);
//        foods.add(food);
//        foods.add(food);
//        foods.add(food);


        return view;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface IFoodLikesCallback {
        void onCallback(List<String> likes);
    }

    public interface IFoodsCallback {
        void onCallback(List<Food> foods);
    }


}
