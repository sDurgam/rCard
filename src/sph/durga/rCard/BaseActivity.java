package sph.durga.rCard;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	protected SQLiteDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle arg0) 
	{
		super.onCreate(arg0);
	}

	@Override
	protected void onPause() {
		dbHelper.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		dbHelper = new SQLiteDBHelper(this);
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}
	
}
