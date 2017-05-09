package me.kaini.level;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.kaini.level.view.LevelView;
import me.kaini.level.view.LineGradienterView;

/**
 * @author chen.canney@gmail.com
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_circle_gradienter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CircleGradienterActivity.class));
            }
        });
        findViewById(R.id.btn_line_gradienter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LineGradienterAcitivity.class));
            }
        });
        findViewById(R.id.btn_gyro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GyroActivity.class));
            }
        });
        findViewById(R.id.btn_others).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,me.kaini.level.sensor.MainActivity.class));
            }
        });

        // 取消方向传感器的监听
    }
}
