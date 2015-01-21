package sph.durga.rCard.recruiter;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.bluetooth.BluetoothService;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCardsLookUp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class RecruiterHomeActivity extends BaseActivity {

	Context mContext;
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
		InsertDummyRCards();
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
	BluetoothService btService ;
	public void receiverCardsClick(View view)
	{
	
		btService = new BluetoothService(this, mHandler, Constants.sockettype.server);
		btService.start();
		
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

