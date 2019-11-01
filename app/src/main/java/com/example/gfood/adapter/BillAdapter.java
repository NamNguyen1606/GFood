package com.example.gfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.ResultBill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillAdapter extends BaseAdapter {

    Context context;
    int billLayout;
    List<ResultBill> resultBills;

    public BillAdapter(Context context, int billLayout, List<ResultBill> resultBills) {
        this.context = context;
        this.billLayout = billLayout;
        this.resultBills = resultBills;
    }

    @Override
    public int getCount() {
        return resultBills.size();
    }

    @Override
    public Object getItem(int position) {
        return resultBills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView tvID, tvTotal, tvCreateAt, tvStatus;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(billLayout, null);
            viewHolder.tvID = view.findViewById(R.id.tvLvBillId);
            viewHolder.tvTotal = view.findViewById(R.id.tvLvBillTotal);
            viewHolder.tvCreateAt = view.findViewById(R.id.tvLvBillCreateAt);
            viewHolder.tvStatus = view.findViewById(R.id.tvLvBillStatus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
            ResultBill resultBill = resultBills.get(position);
        String id = resultBill.getId() + "";
        String total = resultBill.getTotal() + "";
        String createAt = resultBill.getCreateAt();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");

        try {
            Date date = simpleDateFormat.parse(createAt);
            createAt = DateFormat.getInstance().format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String status = resultBill.getStatus();

        viewHolder.tvID.setText(id);
        viewHolder.tvTotal.setText(total);
        viewHolder.tvCreateAt.setText(createAt);
        viewHolder.tvStatus.setText(status);


        return view;
    }
}
