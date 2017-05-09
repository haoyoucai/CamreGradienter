package me.kaini.level.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import me.kaini.level.R;

public class GravityActivity extends Activity implements SensorEventListener{
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
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);		
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
	}



	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this,sensor);
		super.onPause();
	}


	private float[] gravity = new float[3];
	private float[] motion  = new float[3];
	private double ratioY;
	private double angle;
	private int counter = 1;

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < 3; i ++){
			/* accelermeter�Ǻ����еģ���֮ǰС���ӵ�log��֪������Ϊ�����Ǻ����������ƶ��豸�����ı仯����̫�죬����ҡ���ֻ���������������ͻȻ��
			 * ���ͨ��low-pass filter���������й��� */
			gravity[i] = (float) (0.1 * event.values[i] + 0.9 * gravity[i]);
			motion[i] = event.values[i] - gravity[i];
		}
		
		//����������Y�᷽���������G*cos(��)
		ratioY = gravity[1]/SensorManager.GRAVITY_EARTH;
		if(ratioY > 1.0)
			ratioY = 1.0;
		if(ratioY < -1.0)
			ratioY = -1.0;
		//��æ���ֵ������z��ķ�����������ֵ��
		angle = Math.toDegrees(Math.acos(ratioY));
		if(gravity[2] < 0)
			angle = - angle;
		
		//����ɨ����ÿ10�α仯��ʾһ��ֵ
		if(counter ++ % 10 == 0){
			tv.setText("Raw Values : \n"
					+  "   x,y,z = "+ event.values[0] + "," + event.values[1] + "," + event.values[2] + "\n"
					+  "Gravity values : \n"
					+  "   x,y,z = "+ gravity[0] + "," + gravity[1] + "," + gravity[2] + "\n"
					+  "Motion values : \n"
					+  "   x,y,z = "+ motion[0] + "," + motion[1] + "," + motion[2] + "\n"
					+  "Y��Ƕ� :" + angle	);
			tv.invalidate();
			counter = 1;
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


}
