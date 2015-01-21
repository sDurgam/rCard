package sph.durga.rCard.recruiter;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.BluetoothService;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCardsLookUp;
import sph.durga.rCard.jobseeker.ListDevicesActivity;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class RecruiterHomeActivity extends BaseActivity {

	Context mContext;
	private static final int REQUEST_CONNECT_DEVICE = 2;
	private static final int REQUEST_ENABLE_BT = 1;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.recruiter_home_activity);
		mContext = this;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		if(btService != null)
		{
			btService.stop();
		}
	}

	RCardsLookUp rcardlookUpObj;
	@Override
	protected void onResume()
	{
		super.onResume();
		rcardlookUpObj = new RCardsLookUp(dbHelper);
		//InsertDummyRCards();
	}

	private void InsertDummyRCards()
	{
		rcardlookUpObj.InsertrCardslookuptable("aa", "aa", "bbg", "aaa", 1,5,"aa", "aa", "bb", "aaa", "cc", "dd", "ee");
		rcardlookUpObj.InsertrCardslookuptable("aabb", "aaaaaa", "bbaaaaaaa", "aaaaaaaa", 2,3,"aabbbbb", "aacccc", "bbeeee", "aaaeeee", "cceee", "ddeee", "eeeee");
	}

	public void ListrCardsClick(View view)
	{
		Toast.makeText(this, "list rcard clicked", Toast.LENGTH_SHORT).show();
		Intent in = new Intent(this, RecruiterRcardListActivity.class);
		startActivity(in);

	}
	BluetoothAdapter btAdapter;
	BluetoothService btService ;
	public void receiverCardsClick(View view)
	{
		//enable bluetooth
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
				SetupBluetoothService();
			}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{	
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) 
			{
				btService = new BluetoothService(this, mHandler, Constants.sockettype.server);
				btService.start();
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
	private void SetupBluetoothService()
	{
		if (btService == null)
		{
			btService = new BluetoothService(this, mHandler, Constants.sockettype.client);
		}
		Intent serverIntent = new Intent(this, ListDevicesActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what)
			{
			case Constants.MESSAGE_RCARD_JSON_DATA:
				// save the connected device's name
				String data = msg.getData().toString();
				Toast.makeText(mContext,data,
						Toast.LENGTH_SHORT).show();
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

