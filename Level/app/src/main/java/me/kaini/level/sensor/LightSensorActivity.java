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

public class LightSensorActivity extends Activity implements SensorEventListener{
	private SensorManager sensorManager = null;
	private Sensor lightSensor = null;
	private TextView tv = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_list_activity); //ʹ����ͬ��layout
		tv = (TextView)findViewById(R.id.sensor_list_text);
		
		//��1����ȡLight������
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);		
	}
	
	

	@Override
	/* ��2��������˼�������������������������Ҫ�ĵ磬�������Ӧ��ֻ����Ҫ��ʱ����м�������
	 * �������activity����ǰ̨�Ͳ���Ҫ�����������onResume()ע�����������onPause()��ע��������*/
	protected void onResume() {
		/* ������������ָSensor Event�仯֪ͨ��Ƶ�ʣ���ЧֵΪNORMAL��UI��GAME��FASTER�� 
		 * ��Щ����������������ʱ����д������ݣ����ڴ�������������ѹ�������ܻᵼ��APP���������⣬��˸�����Ҫѡ����ʵ�Ƶ�ʡ�
		 * ������תʸ����������ͨ����Ҫ���ϵ�ȥ��ȡ��*/
		sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(this,lightSensor);
		super.onPause();
	}



	
	/*��3��SensorEventListenerҪʵ�������ӿ�onAccuracyChanged()��onSensorChanged()��
	 * onAccuracyChanged()���ھ��ȸı����ע�������ʱ���á�accuracy��Ϊ4����0��unreliable����1��low����2��medium����3��high��
	 * ע��0�������������⣬ͬʱ�Ǵ�������ҪУ׼��
	 * */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		showInfo(sensor.getName() + " accuracy changed: " + accuracy);
	}

	@Override
	/*��3��SensorEventListenerҪʵ�������ӿ�onAccuracyChanged()��onSensorChanged()��
	 * onSensorChanged()�ڴ�������ֵ�����仯�Ѿ�ע�������ʱ���ã������Ƶ�ʾ���ע���еĲ�������
	 * ���ڹ⴫��������Ч��ֵ�����values[0]�еģ���λΪSI lunx���⴫����ͨ��λ���Ϸ���һ�㿿��ࣩ��
	 * ����ǰ������ͷ�⻹��һ���ף�һ����������ڸǻᴥ��onSensorChanged()*/
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		showInfo("Get Sensor Event: " + event.sensor.getName() + " " + event.values[0] );
	}
	
	

	private void showInfo(String info){
		tv.append("\n" + info);
		Log.d("LightSensor",info);
	}
}
