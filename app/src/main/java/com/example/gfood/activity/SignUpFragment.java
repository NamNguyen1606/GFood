package com.example.gfood.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.Account;
import com.example.gfood.retrofit2.model.Token;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText edtUsername, edtPassword, edtConfirmPassword;
    private Button btn_signUp;
    private APIService mApiServer;
    private SharedPreferences sharedPreferences;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        edtUsername = (EditText) view.findViewById(R.id.edt_signUp_username);
        edtPassword = (EditText) view.findViewById(R.id.edt_signUp_password);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edt_signUp_ConfPassword);
        btn_signUp = (Button) view.findViewById(R.id.btn_signUp);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                //check null input
                if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                else {
                    mApiServer =  APIutils.getAPIService();

                    if (password.equals(confirmPassword)){

                        mApiServer.register(username, password).enqueue(new Callback<Account>() {
                            @Override
                            public void onResponse(Call<Account> call, Response<Account> response) {

                                try {

//                                // Login account
                                    mApiServer.login(username,password).enqueue(new Callback<Token>() {
                                        @Override
                                        public void onResponse(Call<Token> call, Response<Token> response) {
                                            // Get token
                                            try {
                                                sharedPreferences.edit().putString("Username", edtUsername.getText().toString()).apply();
                                                sharedPreferences.edit().putString("Password", edtPassword.getText().toString()).apply();
                                                sharedPreferences.edit().putString("Token_Access", "Bearer " + response.body().getAccess()).apply();
                                                sharedPreferences.edit().putString("Token_Refresh", "Bearer " + response.body().getRefresh()).apply();



                                                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();

                                            } catch (Exception e){
                                                Toast.makeText(getActivity(), "Token null", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        @Override
                                        public void onFailure(Call<Token> call, Throwable t) {
                                            Log.e("Error",t.getMessage());
                                        }
                                    });

                                } catch (Exception e){
                                    Toast.makeText(getActivity(), "Username alreadly exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Account> call, Throwable t) {
                                Log.e("Error Sign up", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}
