package com.example.projectapp_1005_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sm;
    Sensor sr;
    TextView txv;
    ImageView igv;
    RelativeLayout layout;
    double mx = 0, my = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txv = (TextView)findViewById(R.id.txvIno);
        igv = (ImageView)findViewById(R.id.igvMove);
        layout = (RelativeLayout)findViewById(R.id.layout);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onSensorChanged(SensorEvent event){
        if(mx == 0){
            mx = (layout.getWidth()-igv.getWidth())/20.0;
            my = (layout.getHeight()-igv.getHeight())/20.0;
        }

        RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams)igv.getLayoutParams();
        parms.leftMargin = (int)((5 - event.values[0]) * 2 * mx);
        parms.topMargin = (int)((5 + event.values[1]) * 2 * my);
        igv.setLayoutParams(parms);

        txv.setText(String.format("X-axis: %1.2f, Y-axis: %1.2f, Z-axis: %1.2f", event.values[0], event.values[1], event.values[2]));
    }
}

