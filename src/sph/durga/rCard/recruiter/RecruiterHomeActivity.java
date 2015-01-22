package sph.durga.rCard.recruiter;

import org.json.JSONException;
import org.json.JSONObject;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.rCardService;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCardsLookUp;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RecruiterHomeActivity extends BaseActivity {

	static Context mContext;
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

	static RCardsLookUp rcardlookUpObj;
	@Override
	protected void onResume()
	{
		super.onResume();
		rcardlookUpObj = new RCardsLookUp(dbHelper);
		//InsertDummyRCards();
	}

	public void ListrCardsClick(View view)
	{
		Toast.makeText(this, "list rcard clicked", Toast.LENGTH_SHORT).show();
		Intent in = new Intent(this, RecruiterRcardListActivity.class);
		startActivity(in);

	}
	BluetoothAdapter btAdapter;
	rCardService btService ;
	public void receiverCardsClick(View view)
	{
		Toast.makeText(mContext, "waiting for rCard", Toast.LENGTH_SHORT).show();
		((Button)view).setEnabled(false);
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
				btService = new rCardService(this, mHandler, Constants.sockettype.server);
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
			btService = new rCardService(this, mHandler, Constants.sockettype.server);
		}
		btService.start();
	}

	public static final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what)
			{
			case Constants.MESSAGE_RCARD_JSON_DATA:
				try 
				{
					String json = msg.getData().toString();
					JSONObject jsonObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
					if(jsonObj != null)
					{
						jsonObj = jsonObj.getJSONObject("rcard_json_data");
						String name = jsonObj.getString(SQLiteDBHelper.RCARDS_NAME);
						String phone = jsonObj.getString(SQLiteDBHelper.RCARDS_PHONE);
						String email = jsonObj.getString(SQLiteDBHelper.RCARDS_EMAIL);
						String primaryskills = jsonObj.getString(SQLiteDBHelper.RCARDS_PRIMARY_SKILLS);
						String androidexp = jsonObj.getString(SQLiteDBHelper.RCARDS_ANDROID_EXP);
						String iosexp = jsonObj.getString(SQLiteDBHelper.RCARDS_IOS_EXP);
						String andurl = jsonObj.getString(SQLiteDBHelper.RCARDS_PORTFOLIO_ANDROID);
						String iosurl = jsonObj.getString(SQLiteDBHelper.RCARDS_PORTFOLIO_IOS);
						String othurl = jsonObj.getString(SQLiteDBHelper.RCARDS_PORTFOLIO_OTHER);
						String linkurl = jsonObj.getString(SQLiteDBHelper.RCARDS_LINKEDIN_URL);
						String resumeurl = jsonObj.getString(SQLiteDBHelper.RCARDS_RESUME_URL);
						String otherinfo = jsonObj.getString(SQLiteDBHelper.RCARDS_OTHER_INFO);
						String degree = jsonObj.getString(SQLiteDBHelper.RCARDS_HIGHEST_DEGREE);
						int androidexpInt = (!androidexp.equals("")) ? Integer.valueOf(androidexp) : 0; 
						int iosexpint = (!iosexp.equals("")) ? Integer.valueOf(iosexp) : 0; 
						rcardlookUpObj.InsertrCardslookuptable(name, phone, email, primaryskills, androidexpInt, iosexpint, andurl, iosurl, othurl, linkurl, resumeurl, degree, otherinfo);
						Toast.makeText(mContext, Constants.message_rcards_received_saved + name, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e)
				{
					
					e.printStackTrace();
					Toast.makeText(mContext, Constants.message_rcards_received_notsaved, Toast.LENGTH_SHORT).show();
				}
				((Button)((RecruiterHomeActivity)mContext).findViewById(R.id.rCardClickBtn)).setEnabled(true);
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

