package com.example.gfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gfood.retrofit2.model.Account;
import com.example.gfood.retrofit2.model.Password;
import com.example.gfood.retrofit2.model.Token;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText edtUsername, edtPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private FrameLayout prarentFrameLayout;
    private APIService mAPIServer;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        btnSignIn = (Button) view.findViewById(R.id.btn_signIn);
        edtUsername = (EditText) view.findViewById(R.id.edt_signIn_username);
        edtPassword = (EditText) view.findViewById(R.id.edt_signIn_password);
        tvSignUp = (TextView) view.findViewById(R.id.tv_signIn_signUp);
        prarentFrameLayout = (FrameLayout) getActivity().findViewById(R.id.frameLayoutRegister);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                Account account = new Account(username, password);

                // Post request
                mAPIServer = APIutils.getAPIService();

//                //change password
//                String token ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNTY5MDExMjgxLCJqdGkiOiJmN2MyZjVkYzU0YmE0ZmY4YTJhZTdlZTgzOGY5ZTFkMSIsInVzZXJfaWQiOjQsInR5cGUiOiJjdXN0b21lciJ9.TSFK7PyIp8GtqVxBDLWdEeo_Bh_iRhboHySsIZx99DU";
//                Log.e("ACCESS", token);
//                Password password1 = new Password("nam1606", "namnam");
//                mAPIServer.changePassword(token,password1).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if(response.isSuccessful()){
//                            Log.e("ACCESS", "sucessful");
//                            try {
//                                Log.e("Messeger", response.body().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Log.e("ACCESS", "false");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("ERROR", t.getMessage());
//                    }
//                });



                mAPIServer.login(username,password).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        // Get token
                        try {
                            Log.e("Refresh: ",response.body().getRefresh());
                            Log.e("Access: ",response.body().getAccess());
                            Intent intent = new Intent(getActivity(), HomePageActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        } catch (Exception e){
                            Toast.makeText(getActivity(), "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                    }
                });


            }
        });


    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(prarentFrameLayout.getId(),fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

}
