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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.Account;
import com.example.gfood.retrofit2.model.Token;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

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
    private SharedPreferences sharedPreferences;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = getContext();
        sharedPreferences = context.getSharedPreferences("Acount_info",Context.MODE_PRIVATE );
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

        // Sign In
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                Account account = new Account(username, password);

                // Post request
                mAPIServer = APIutils.getAPIService();

                mAPIServer.login(username,password).enqueue(new Callback<Token>() {
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
