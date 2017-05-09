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

public class ProximitySensorActivity extends Activity implements SensorEventListener{
	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	private TextView tv = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_list_activity);
		tv = (TextView)findViewById(R.id.sensor_list_text);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		//�������Ⱥ����ֵ���������һ����˵���ý����봫�������ܸ����ӽ���Զ��������״̬��
		showInfo("resolution:" + sensor.getResolution());
		showInfo("max value:" + sensor.getMaximumRange());
	}
	
	

	@Override
	protected void onResume() {
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
	/* ���ڽ����봫��������Ч��ֵ�����values[0]�еģ���λΪcm��*/
	public void onSensorChanged(SensorEvent event) {
		showInfo("�������¼��� " + event.sensor.getName() + " " + event.values[0] );
	}
	
	
	private void showInfo(String info){
		tv.append("\n" + info);
		Log.d("ProximitySensor",info);
	}

}
