package me.kaini.level.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import me.kaini.level.R;

public class GyroscopeSensorActivity extends Activity implements SensorEventListener{
	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	//private TextView tv = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_list_activity);
		//tv = (TextView)findViewById(R.id.sensor_list_text);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		showInfo("resolution is " + sensor.getResolution());
	}
	
	

	@Override
	protected void onResume() {
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}



	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this,sensor);
		super.onPause();
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		showInfo(sensor.getName() + " accuracy changed: " + accuracy);
	}

	@Override
	/* ���������ǣ���������x��y��z��������Ľ��ٶȣ��ֱ��values[0]��values[1]��values[2]�ж�ȡ����λΪ����/�롣*/
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
			showInfo("�¼���" + " x:" + event.values[0] + " y:" + event.values[1]
				+ " z:" + event.values[2]);
	}
	
	//�ڻ�ΪP6�Ļ����ϣ������Ƿǳ����У�ƽ�������棬���ڵ����ճɵ���΢���ڲ��ϵ�ˢ����Ϊ�˱���дUI��ɵ��������⣬ֻдLog��
	private void showInfo(String info){
		//tv.append("\n" + info);
		Log.d("������",info);
	}

}
