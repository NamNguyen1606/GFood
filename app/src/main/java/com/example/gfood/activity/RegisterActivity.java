package com.example.gfood.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.gfood.R;

public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayoutRegister);
        setFragment(new SignInFragment());

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
            return;
        }
        super.onBackPressed();
    }
}
