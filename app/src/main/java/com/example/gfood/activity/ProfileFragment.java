package com.example.gfood.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.UserInfo;
import com.example.gfood.retrofit2.model.UserInfomation;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final String WEBSITE_URL = "https://softwaredevelopmentproject.azurewebsites.net/";
    private ImageView imgAvatarUser;
    private TextView tvUsername, tvAddress, tvPhoneNumber, tvChangePwd, tvPayment, tvBill, tvSignOut;
    private Button btnEdit;
    private SharedPreferences sharedPreferences;
    private APIService apiService;
    private Bitmap avatar;
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
        tvBill = (TextView) view.findViewById(R.id.tvProfBill);
        tvSignOut = (TextView) view.findViewById(R.id.tvProfSignOut);
        btnEdit = (Button) view.findViewById(R.id.btnProfEdit);

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

                // check null
                //ID
                sharedPreferences.edit().putString("ID_User", response.body().getId()+"").apply();
                //Name
                if((response.body().getName() == null) == false){
                    name = response.body().getName().toString();
                    sharedPreferences.edit().putString("Name_Info", name).apply();
                }

                // Phone number
                if((response.body().getPhone() == null) == false){
                    phone = response.body().getPhone().toString();
                    sharedPreferences.edit().putString("Phone_Info", phone).apply();
                }

                // Address
                if((response.body().getAddress() == null) == false){
                    address = response.body().getAddress().toString();
                    sharedPreferences.edit().putString("Address_Info", address).apply();
                }

                // Avatar
                if((response.body().getImage().isEmpty()) == false){
                    imgAvatar = response.body().getImage();
                    sharedPreferences.edit().putString("AvatarImage_Info", WEBSITE_URL + imgAvatar).apply();
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

        String name = sharedPreferences.getString("Name_Info", " ");
        String phone =sharedPreferences.getString("Phone_Info", " ");
        String address = sharedPreferences.getString("Address_Info", " ");
        String imgAvatar = sharedPreferences.getString("AvatarImage_Info", " ");

        tvUsername.setText(name);
        tvPhoneNumber.setText("Phone number: "+phone);
        tvAddress.setText("Address: " + address);
        try{
            Picasso.with(getContext().getApplicationContext()).load(imgAvatar).resize(250, 200).into(imgAvatarUser);
        } catch (Exception e){

        }


        //Change avatar
        imgAvatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PickImageDialog.build(new PickSetup(), new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        avatar = r.getBitmap();
                        Picasso.with(getContext()).load(r.getUri()).resize(250, 200).into(imgAvatarUser);
                        String avatarBase64 = encodeImage(avatar);
                        Log.e("BASE64", avatarBase64);
                    }
                }).show(getActivity().getSupportFragmentManager());


            }
        });

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

        //Edit profile
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit();
            }
        });
    }


    private void dialogEdit(){
        Context context;
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_profile);

        final EditText edtName = (EditText) dialog.findViewById(R.id.edtProfEditName);
        final EditText edtPhoneNumber = (EditText) dialog.findViewById(R.id.edtProfEditPhoneNumber);
        final EditText edtAddress = (EditText) dialog.findViewById(R.id.edtProfEditAddress);
        Button btnSave = (Button) dialog.findViewById(R.id.btnProfEditSave);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnProfEditCancel);
        dialog.show();

        // Save Profile
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = sharedPreferences.getString("ID_User", " ");
                String name = edtName.getText().toString();
                String phone = edtPhoneNumber.getText().toString();
                String address = edtAddress.getText().toString();
                String imgAvatar = sharedPreferences.getString("AvatarImage_Info", " ");


                UserInfomation userInfo = new UserInfomation(name, phone, address, "");
                String url = "api/user/" + id;
                Log.e("URL", url);
                String token = sharedPreferences.getString("Token_Access", "");

                // Edit profile
                apiService.editProfile(token,url,userInfo).enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if(response.isSuccessful()){
                            Log.e("edit profile", "Done" + response.body().getName());
                        } else {
                            Log.e("edit profile", "Fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }
                });

                dialog.dismiss();
            }
        });

        // Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private String encodeImage(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

}
