package me.kaini.level.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;

import me.kaini.level.R;

public class AccelerometerSensorActivity extends Activity implements SensorEventListener{
	private SensorManager sensorManager = null;
	private Sensor sensor = null,gravitySensor = null,linearAcceleSensor = null;
	private TextView tv = null;
	private WindowManager window = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_list_activity); 
		tv = (TextView)findViewById(R.id.sensor_list_text);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);		
		gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		linearAcceleSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
		
		showInfo1("resolution is " + sensor.getResolution());
		
		showInfo1("APIΪ" + Build.VERSION.SDK_INT);
		window = (WindowManager)getSystemService(WINDOW_SERVICE);
		showRotation();
	}
	
	

	@Override
	protected void onResume() {
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(this, linearAcceleSensor, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}



	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onPause();
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		showInfo1(sensor.getName() + " accuracy changed: " + accuracy);
	}

	private int count = 1;
	@Override
	/* ���ڼ���������������x��y��z��������ļ��ٶȣ��ֱ��values[0]��values[1]��values[2]�ж�ȡ��*/
	public void onSensorChanged(SensorEvent event) {
		if(count++ % 40 == 0){
			int type = event.sensor.getType(); 
			if(type == Sensor.TYPE_ACCELEROMETER){
				showInfo1("��������" + " x:" + event.values[0] + " y:" + event.values[1]
					+ " z:" + event.values[2]);
			}else if(type == Sensor.TYPE_GRAVITY){
				showInfo1("�����ǣ�" + " x:" + event.values[0] + " y:" + event.values[1]
						+ " z:" + event.values[2]);
			}else if(type == Sensor.TYPE_LINEAR_ACCELERATION){
				showInfo1("���Լ�����" + " x:" + event.values[0] + " y:" + event.values[1]
						+ " z:" + event.values[2]);
			}
			count = 1;
		}
	}
	
	private void showRotation(){
		int rotation = window.getDefaultDisplay().getRotation();
		switch(rotation){
		case Surface.ROTATION_0:
			showInfo1("����ROTATION 0(" + rotation + ")");
			break;
		case Surface.ROTATION_90:
			showInfo1("����ROTATION 90(" + rotation + ")");
			break;
		case Surface.ROTATION_180:
			showInfo1("����ROTATION 180(" + rotation + ")");
			break;
		case Surface.ROTATION_270:
			showInfo1("����ROTATION 270(" + rotation + ")");
			break;	
		default:
			showInfo1("����(" + rotation + ")");
			break;
		}
		
	}
	
	private void showInfo(String info){
		//tv.append("\n" + info);
		Log.d("������",info);
	}

	private void showInfo1(String info){
		tv.setText(info + "\n" + tv.getText());
		//tv.append("\n" + info);
		Log.d("������",info);
	}
	
}
