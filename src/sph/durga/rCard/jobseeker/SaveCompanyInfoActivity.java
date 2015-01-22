package sph.durga.rCard.jobseeker;

import org.json.JSONException;
import org.json.JSONObject;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.rCardService;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker.Companies;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker.MyRcard;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaveCompanyInfoActivity extends BaseActivity 
{
	Companies cmpObj;
	EditText companynameTxt;
	EditText companycontactTxt;
	EditText companyemailTxt;
	EditText companyotherInfoTxt;
	Button sendrCardBtn;
	int companyId = -1;
	private MyRcard myrcardObj;
	private static Context mContext;
	
	
	private static final int REQUEST_CONNECT_DEVICE = 2;
	private static final int REQUEST_ENABLE_BT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_save_company_activity);
		companynameTxt = (EditText) findViewById(R.id.companynameTxt);
		companycontactTxt = (EditText) findViewById(R.id.companycontactTxt);
		companyemailTxt = (EditText) findViewById(R.id.companyemailTxt);
		companyotherInfoTxt = (EditText) findViewById(R.id.companyotherInfoTxt);
		sendrCardBtn = (Button) findViewById(R.id.sendrCardBtn);
		mContext = this;
	}

	//	private void EnableBluetooth()
	//	{
	//		btAdapter = BluetoothAdapter.getDefaultAdapter();
	//		if(btAdapter == null)
	//		{
	//			Toast.makeText(this, "This device does not support bluetoot", Toast.LENGTH_LONG).show();
	//			sendrCardBtn.setEnabled(false);
	//		}
	//		else
	//		{
	//			//enable bluetooth
	//			if(!btAdapter.isEnabled())
	//			{
	//				Intent enableBTintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	//				startActivityForResult(enableBTintent, REQUEST_ENABLE_BT);
	//			}
	//			else
	//			{
	//				Intent btDiscoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	//				startActivityForResult(btDiscoverIntent, REQUEST_DEVICE_DISCOVERABLE);
	//			}
	//		}
	//		
	//	}

	//	@Override
	//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	//	{
	//		super.onActivityResult(requestCode, resultCode, data);
	//		if(requestCode == REQUEST_ENABLE_BT)
	//		{
	//			if(resultCode != RESULT_CANCELED)
	//			{
	//				//enable bluetooth device for discovery
	//				Intent btDiscoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	//				startActivityForResult(btDiscoverIntent, REQUEST_DEVICE_DISCOVERABLE);
	//			}
	//			else
	//			{
	//				Toast.makeText(this, "Need to turn on bluetooth first", Toast.LENGTH_LONG).show();
	//			}
	//		}
	//		else if(requestCode == REQUEST_DEVICE_DISCOVERABLE)
	//		{
	//			if(resultCode != RESULT_CANCELED)
	//			{
	//				sendrCardBtn.setEnabled(true);
	//				//open client socket
	//				BluetoothService btService = new BluetoothService(this, mHandler, Constants.sockettype.client);
	//				btService.start();
	//			}
	//			else
	//			{
	//				Toast.makeText(this, "Your device needs to be discovered to be connected", Toast.LENGTH_LONG).show();
	//				sendrCardBtn.setEnabled(false);
	//			}
	//		}
	//	}

	private int SaveCompanyInfo()
	{
		String companyname = companynameTxt.getText().toString();
		String contactname = companycontactTxt.getText().toString();
		String email = companyemailTxt.getText().toString();
		String otherinfo = companyotherInfoTxt.getText().toString();
		int result = -1;
		if(!companyname.equals(""))
		{
			result = cmpObj.SaveCompany(companyname, contactname, email, otherinfo);
			if(result == -1)
			{
				Toast.makeText(this, "company details could not be saved", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "company details successfully saved", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			Toast.makeText(this, "please enter company name", Toast.LENGTH_LONG).show();
		}
		return result;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		cmpObj = null;
		if(rCardService != null)
		{
			rCardService.stop();
		}
	}

	@Override
	protected void onDestroy() {
		if(rCardService != null)
		{
			rCardService.stop();
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		cmpObj = new Companies(dbHelper);
	}

	public void saveCompanyInfoClick(View view)
	{
		companyId = SaveCompanyInfo();
	}

	public void sendRcardClick(View view)
	{
		if(companyId == -1)
		{
			companyId = SaveCompanyInfo();
		}
		if(companyId != -1)
		{
//			Intent in = new Intent(this, SendrCardActivity.class);
//			Bundle b = new Bundle();
//			b.putInt(Constants.COMPANY_ID, companyId);
//			in.putExtras(b);
//			startActivity(in);
			SetupBluetooth();
		}
	}
	
	BluetoothAdapter btAdapter;
	rCardService rCardService;

	private void SetupBluetooth()
	{
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) 
		{

			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else
		{
			if (!btAdapter.isEnabled())
			{
				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			}
			else
			{
				ListBluetoothDevices();
			}
		}
	}
	private void ListBluetoothDevices()
	{
		if (rCardService == null)
		{
			rCardService = new rCardService(this, mHandler, Constants.sockettype.client);
		}
		Intent serverIntent = new Intent(this, ListDevicesActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		
	}

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
				ListBluetoothDevices();
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
		rCardService.connect(device, GetrCardJSONObject());
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
			jsonObject.put(SQLiteDBHelper.MYRCARD_EMAIL, myrcardObj.getEmail());
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
	
	private final Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {

			String mConnectedDeviceName;
			switch (msg.what)
			{
			case Constants.MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName =  msg.getData().getString(Constants.DEVICE_NAME);
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
			case Constants.MESSAGE_JSON_DATA_WRITE:
			{
				//update rcard sent column of the company table 
				Companies compObj = new Companies(dbHelper);
				compObj.UpdateMyrCard(companyId, true);
				Toast.makeText(mContext, "Your rCard succesfully sent to " + companynameTxt.getText().toString(),
						Toast.LENGTH_SHORT).show();
			}
			break;
			}
		}
	};
}
