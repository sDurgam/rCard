package sph.durga.rCard.jobseeker;

import java.util.UUID;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.BluetoothService;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker.Companies;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
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
	
	private static Context mContext;

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

	 private static final Handler mHandler = new Handler() 
	 {
	        @Override
	        public void handleMessage(Message msg)
	        {
	           // FragmentActivity activity = this.getActivity();
	            String mConnectedDeviceName;
				switch (msg.what) 
	            { 
	                case Constants.MESSAGE_DEVICE_NAME:
	                    // save the connected device's name
	                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
	                    if (null != mContext)
	                    {
	                        Toast.makeText(mContext, "Connected to "
	                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
	                    }
	                    break;
	                case Constants.MESSAGE_TOAST:
	                    if (null != mContext)
	                    {
	                        Toast.makeText(mContext, msg.getData().getString(Constants.TOAST),
	                                Toast.LENGTH_SHORT).show();
	                    }
	                    break;
	            }
	        }
	    };

	private void SaveCompanyInfo()
	{
		String companyname = companynameTxt.getText().toString();
		String contactname = companycontactTxt.getText().toString();
		String email = companyemailTxt.getText().toString();
		String otherinfo = companyotherInfoTxt.getText().toString();
		int result;
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
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		cmpObj = null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cmpObj = new Companies(dbHelper);
	}

	public void saveCompanyInfoClick(View view)
	{
		SaveCompanyInfo();
	}

	public void sendRcardClick(View view)
	{
		SaveCompanyInfo();
		Intent in = new Intent(this, SendrCardActivity.class);
		startActivity(in);
	}
}
