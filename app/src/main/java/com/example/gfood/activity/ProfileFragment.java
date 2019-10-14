package com.example.gfood.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView imgAvatarUser;
    private TextView tvUsername, tvChangePwd, tvPayment, tvHistory, tvSignOut;
    private SharedPreferences sharedPreferences;

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
        tvChangePwd = (TextView) view.findViewById(R.id.tvProfChangePwd);
        tvPayment = (TextView) view.findViewById(R.id.tvProfPayment);
        tvHistory = (TextView) view.findViewById(R.id.tvProfHistory);
        tvSignOut = (TextView) view.findViewById(R.id.tvProfSignOut);

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
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
