package com.example.gfood.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.UserInfo;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView imgAvatarUser;
    private TextView tvUsername, tvAddress, tvPhoneNumber, tvChangePwd, tvPayment, tvHistory, tvSignOut;
    private SharedPreferences sharedPreferences;
    private APIService apiService;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imgAvatarUser = (ImageView) view.findViewById(R.id.imgProfUserAvatar);
        tvUsername = (TextView) view.findViewById(R.id.tvProfUsername);
        tvPhoneNumber = (TextView) view.findViewById(R.id.tvProfPhone);
        tvAddress = (TextView) view.findViewById(R.id.tvProfAddress);
        tvChangePwd = (TextView) view.findViewById(R.id.tvProfChangePwd);
        tvPayment = (TextView) view.findViewById(R.id.tvProfPayment);
        tvHistory = (TextView) view.findViewById(R.id.tvProfHistory);
        tvSignOut = (TextView) view.findViewById(R.id.tvProfSignOut);

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("Token_Access", "");
        apiService = APIutils.getAPIService();

        apiService.getUserInfo(token).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                String userName = response.body().getUsername();
                String name = (String) response.body().getName();
                String phone = " ";
                String address = " ";
                String imgAvatar = " ";

                // Reset value for append
                tvAddress.setText("Address: ");
                tvPhoneNumber.setText("Phone Number: ");

                // check null
                //Name
                if((response.body().getName() == null) == false){
                    name = response.body().getName().toString();
                    tvUsername.setText(name);
                    sharedPreferences.edit().putString("Name_Info", name);
                } else {
                    tvUsername.setText(userName);
                }

                // Phone number
                if((response.body().getPhone() == null) == false){
                    phone = response.body().getPhone().toString();
                    tvPhoneNumber.append(phone);
                    sharedPreferences.edit().putString("Phone_Info", phone);
                }

                // Address
                if((response.body().getAddress() == null) == false){
                    address = response.body().getAddress().toString();
                    tvAddress.append(address);
                    sharedPreferences.edit().putString("Address_Info", phone);
                }

                // Avatar
                if((response.body().getImage().isEmpty()) == false){
                    imgAvatar = response.body().getImage();
                    sharedPreferences.edit().putString("AvatarImage_Info", imgAvatar);
                    try{
                        Picasso.with(getContext().getApplicationContext()).load(imgAvatar).into(imgAvatarUser);
                    }catch (Exception e){

                    }

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("ERROR:", "Profile");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Change Password
        tvChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePwdActivity.class);
                getActivity().startActivity(intent);
            }
        });


        //Sign Out
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new  Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }

}
