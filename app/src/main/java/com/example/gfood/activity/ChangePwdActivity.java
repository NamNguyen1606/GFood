package com.example.gfood.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.Password;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdActivity extends AppCompatActivity {

    private EditText edtOldPwd, edtNewPwd, edtConfirmPwd;
    private Button btnApply, btnCancel;
    private SharedPreferences sharedPreferences;
    private APIService apiService;
    private String oldPwd, tokenRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        edtOldPwd = (EditText) findViewById(R.id.edtChagPwdOld);
        edtNewPwd = (EditText) findViewById(R.id.edtChagPwdNew);
        edtConfirmPwd = (EditText) findViewById(R.id.edtChagPwdNewConfirm);
        btnApply = (Button) findViewById(R.id.btnChagPwdApply);
        btnCancel = (Button) findViewById(R.id.btnChagPwdCancel);
        
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        oldPwd = sharedPreferences.getString("Password","");
        tokenRefresh = sharedPreferences.getString("Token_Access", "");
        
        //Input value


        apiService = APIutils.getAPIService();
        // Apply
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPwd.getText().toString();
                final String newPassword = edtNewPwd.getText().toString();
                String confirmPassword = edtConfirmPwd.getText().toString();

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Field can't Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if(oldPassword.equals(oldPwd)){
                        if(newPassword.equals(confirmPassword)){
                            Password password = new Password(oldPassword, newPassword);
                            apiService.changePassword(tokenRefresh, password).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        try {
                                            Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                                            sharedPreferences.edit().putString("Password", newPassword).commit();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Log.e("ACCESS", "false");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("ERROR", t.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Old password is incorect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
