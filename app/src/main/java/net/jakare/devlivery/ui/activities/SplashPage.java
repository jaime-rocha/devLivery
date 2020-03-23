package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.jakare.devlivery.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashPage extends AppCompatActivity {

    private static final long SPLASH_TIME = 4000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        this.context = this;

        initTimer();
    }

    private void initTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(context, MainActivity.class);
                finish();
                startActivity(main);
            }
        }, SPLASH_TIME);
    }
}
