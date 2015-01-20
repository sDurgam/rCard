package sph.durga.rCard.jobseeker;

import org.json.JSONException;
import org.json.JSONObject;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.BluetoothService;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker.MyRcard;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class sendrCardActivity extends BaseActivity {


	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	private BluetoothAdapter btAdapter;
	private BluetoothService btService;
	private Context mContext;
	private MyRcard myrcardObj;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) 
                {
                    connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK)
                {
                	SetupBluetoothService();
                } else
                {
                    Toast.makeText(this, "Bluetooth is not enabled or cannot enable bluetooth",
                            Toast.LENGTH_SHORT).show();
                    this.finish();
                }
        }
    }
	private void connectDevice(Intent data) 
	{
		String address = data.getExtras()
		                .getString(ListDevicesActivity.EXTRA_DEVICE_ADDRESS);
		        BluetoothDevice device = btAdapter.getRemoteDevice(address);
		        btService.connect(device, GetrCardJSONObject());
	}
	private void SetupBluetoothService()
	{
		if (btService == null)
		{
			btService = new BluetoothService(this, mHandler, Constants.sockettype.client);
		}
		Intent serverIntent = new Intent(this, ListDevicesActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}
	@Override
	protected void onCreate(Bundle arg0) 
	{
		super.onCreate(arg0);
		setContentView(R.layout.jobseeker_send_rcard_activity);
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) 
		{

			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			this.finish();
		}
		mContext = this;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		myrcardObj = null;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (btService != null) 
		{
			btService.start();
		}
	}

	private JSONObject GetrCardJSONObject()
	{
		myrcardObj = new MyRcard(dbHelper);
		myrcardObj.FetchRCard();
		JSONObject jsonObject = new JSONObject();
		try 
		{
			jsonObject.put(SQLiteDBHelper.MYRCARD_NAME, myrcardObj.getName());
			jsonObject.put(SQLiteDBHelper.MYRCARD_PHONE, myrcardObj.getPhone());
			jsonObject.put(SQLiteDBHelper.MYRCARD_PRIMARY_SKILLS, myrcardObj.getPrimaryskills());
			jsonObject.put(SQLiteDBHelper.MYRCARD_ANDROID_EXP, myrcardObj.getAndroidexp().toString());
			jsonObject.put(SQLiteDBHelper.MYRCARD_IOS_EXP, myrcardObj.getIosexp().toString());
			jsonObject.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_ANDROID, myrcardObj.getAndurl());
			jsonObject.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_IOS, myrcardObj.getIosurl());
			jsonObject.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_OTHER, myrcardObj.getOthurl());
			jsonObject.put(SQLiteDBHelper.MYRCARD_LINKEDIN_URL, myrcardObj.getLinkurl());
			jsonObject.put(SQLiteDBHelper.MYRCARD_RESUME_URL, myrcardObj.getResumeurl());
			jsonObject.put(SQLiteDBHelper.MYRCARD_HIGHEST_DEGREE, myrcardObj.getDegree());
			jsonObject.put(SQLiteDBHelper.MYRCARD_OTHER_INFO, myrcardObj.getOtherinfo());
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return jsonObject;
	}
	@Override
	protected void onStart() 
	{
		super.onStart();
		if (!btAdapter.isEnabled())
		{
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}
		else
		{
			SetupBluetoothService();
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (btService != null) 
		{
			btService.stop();
		}
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			String mConnectedDeviceName;
			switch (msg.what)
			{
			case Constants.MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
				if (null != mContext) {
					Toast.makeText(mContext, "Connected to "
							+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
				}
				break;
			case Constants.MESSAGE_TOAST:
				if (null != mContext) {
					Toast.makeText(mContext, msg.getData().getString(Constants.TOAST),
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};

}
