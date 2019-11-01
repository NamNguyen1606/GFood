package com.example.gfood.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.gfood.R;
import com.example.gfood.adapter.BillAdapter;
import com.example.gfood.retrofit2.model.Bill;
import com.example.gfood.retrofit2.model.ResultBill;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {
    BillAdapter billAdapter;
    List<ResultBill> resultBill;
    APIService apiService;
    SharedPreferences sharedPreferences;
    ListView lvBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        lvBill = findViewById(R.id.lvBill);

        final Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("Token_Access", "");

        apiService = APIutils.getAPIService();
        apiService.getBill(token).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(response.isSuccessful()){
                    resultBill = response.body().getResults();
                    billAdapter = new BillAdapter(context, R.layout.listview_bill, resultBill);
                    lvBill.setAdapter(billAdapter);
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {

            }
        });
    }
}
