package com.example.gfood.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
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
    View footerView;
    int pageNumber = 2;
    boolean isloading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        lvBill = findViewById(R.id.lvBill);

        LayoutInflater inflaterFooter = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflaterFooter.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();

        final Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("Token_Access", "");

        apiService = APIutils.getAPIService();
        apiService.getBill(token, "api/bill/").enqueue(new Callback<Bill>() {
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
        loadMoreData();
    }

    // Load more bill
    public void loadMoreData(){
        lvBill.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if((firstVisibleItem + visibleItemCount == totalItemCount)
                        && totalItemCount != 0
                        && isloading == false
                        && limitData == false ){
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                } else {
                }
            }
        });
    }

    // Handler
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    lvBill.addFooterView(footerView);
                    break;
                case 1:
                    //get data
                    getBillData(pageNumber++);
                    Log.e("Loading", "WORK");
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    // Thread
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    public void getBillData(int pageNumber) {
        final String token = sharedPreferences.getString("Token_Access", "");

        apiService = APIutils.getAPIService();
        apiService.getBill(token, "api/bill/?page="+ pageNumber).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                for(int i = 0; i < 10; i++){
                    if(response.body().getNext() == null){
                        limitData = true;
                        lvBill.removeFooterView(footerView);
                    }
                        try{
                            ResultBill resultbill = response.body().getResults().get(i);
                            Log.e("Bill ID", resultbill.getId() + "");
                            resultBill.add(resultbill);
                            lvBill.removeFooterView(footerView);
                        } catch (Exception e){
                        }


                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {

            }
        });
    }

}
