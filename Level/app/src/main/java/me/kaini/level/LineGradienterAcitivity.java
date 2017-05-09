package me.kaini.level;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.kaini.level.view.LineGradienterView;

/**
 * Created by DanLai on 2017/5/6.
 */

public class LineGradienterAcitivity extends AppCompatActivity implements SensorEventListener {
    private float currentRoll;
    private float currentPitch;

    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private Sensor mag_sensor;

    private int count = 0;

    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    // 旋转矩阵，用来保存磁场和加速度的数据
    private float r[] = new float[9];
    // 模拟方向传感器的数据（原始数据为弧度）
    private float values[] = new float[3];

    private LineGradienterView lineGradienterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradienter_line);
        lineGradienterView = (LineGradienterView) findViewById(R.id.lgv);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        // 给传感器注册监听：
//        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 取消方向传感器的监听
        sensorManager.unregisterListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消方向传感器的监听
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 获取手机触发event的传感器的类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
//                magValues = event.values.clone();
                break;
            case Sensor.TYPE_ORIENTATION:
                magValues = event.values.clone();
                break;
        }

//        SensorManager.getRotationMatrix(r, null, accValues, magValues);
//        SensorManager.getOrientation(r, values);

        // 获取　沿着Z轴转过的角度
        float azimuth = (float) (magValues[0]*Math.PI/180);

        // 获取　沿着X轴倾斜时　与Y轴的夹角
        float pitchAngle = (float) (magValues[1]*Math.PI/180);

        // 获取　沿着Y轴的滚动时　与X轴的角度
        //此处与官方文档描述不一致，所在加了符号（https://developer.android.google.cn/reference/android/hardware/SensorManager.html#getOrientation(float[], float[])）
        float rollAngle = (float) (magValues[2]*Math.PI/180);
        onAngleChanged(rollAngle, pitchAngle, azimuth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 角度变更后显示到界面
     *
     * @param rollAngle
     * @param pitchAngle
     * @param azimuth
     */
    private void onAngleChanged(float rollAngle, float pitchAngle, float azimuth) {

        if ((-4 / 180 * Math.PI > rollAngle - currentRoll || rollAngle - currentRoll > 4 / 180 * Math.PI)
                &&-4 / 180 * Math.PI > pitchAngle - currentPitch || pitchAngle - currentPitch > 4 / 180 * Math.PI) {
            lineGradienterView.setAngle(rollAngle, pitchAngle, azimuth);
            currentRoll = rollAngle;
            currentPitch = pitchAngle;
        }
    }
}
