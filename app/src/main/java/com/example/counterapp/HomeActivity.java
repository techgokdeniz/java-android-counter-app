package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private Button PositiveButton,NegativeButton,AyarlarButton;
    private TextView CounterTxt,AltLimit,UstLimit,Bildirim,Titresim;
    private Integer Counter;

    private SensorManager sensorManager;
    private Sensor sensor;
    private long shakeTimestamp;
    private SensorEventListener sensorEventListener;


    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.PositiveButton:
                    Arttir(1);
                    break;
                case R.id.NegativeButton:
                    Azalt(1);
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bildirim = (TextView) findViewById(R.id.Bildirim);
        AltLimit = (TextView) findViewById(R.id.altLimit);
        UstLimit = (TextView) findViewById(R.id.UstLimit);
        Titresim = (TextView) findViewById(R.id.Titresim);

        Bildirim.setText(String.valueOf(SharedPrefs.getInstance(this).read("bildirim" ,false)));
        AltLimit.setText(String.valueOf(SharedPrefs.getInstance(this).read("altlimit" ,"0")));
        UstLimit.setText(String.valueOf(SharedPrefs.getInstance(this).read("ustlimit" ,"100")));
        Titresim.setText(String.valueOf(SharedPrefs.getInstance(this).read("titresim" ,false)));



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        PositiveButton =  (Button) findViewById(R.id.PositiveButton);
        NegativeButton = (Button) findViewById(R.id.NegativeButton);
        AyarlarButton = (Button) findViewById(R.id.AyarlarButton);
        CounterTxt = (TextView) findViewById(R.id.CounterTxt);

        PositiveButton.setOnClickListener(clickListener);
        NegativeButton.setOnClickListener(clickListener);

        AyarlarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Ayarlar = new Intent(getApplicationContext(),Settings.class);
                startActivity(Ayarlar);
            }
        });



        InitCounter();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            Azalt(5);
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            Arttir(5);
        }
        return true;
    };

    private void InitCounter(){
        Counter=0;
        CounterTxt.setText(Counter+"");
    }

    private void Arttir(Integer deger){
        Counter+=deger;
        CounterTxt.setText(Counter+"");
    }

    private void Azalt(Integer deger){
        Counter-=deger;
        CounterTxt.setText(Counter+"");
    }


    @Override
    protected void onResume() {
        super.onResume();

        Bildirim.setText(String.valueOf(SharedPrefs.getInstance(this).read("bildirim",false)));
        Titresim.setText(String.valueOf(SharedPrefs.getInstance(this).read("titresim",false)));
        AltLimit.setText(String.valueOf(SharedPrefs.getInstance(this).read("altlimit","0")));
        UstLimit.setText(String.valueOf(SharedPrefs.getInstance(this).read("ustlimit","100")));


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                final float SHAKE_GRAVITY = 2F;
                final int SHAKE_SLOP_TIME=500;

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                float gX = x / SensorManager.GRAVITY_EARTH;
                float gY = y / SensorManager.GRAVITY_EARTH;
                float gZ = z / SensorManager.GRAVITY_EARTH;


                float gForce = (float)Math.sqrt((double) (gX*gX+gY*gY+gZ*gZ));

                if(gForce>SHAKE_GRAVITY){
                    final long now = System.currentTimeMillis();

                    if(shakeTimestamp + SHAKE_SLOP_TIME>now){
                        return;
                    }

                    shakeTimestamp = now;

                    InitCounter();


                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_UI);



    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(sensorEventListener);
    }

}