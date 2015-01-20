package sph.durga.rCard.recruiter;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCardsLookUp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class RecruiterHomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.recruiter_home_activity);
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
	}

	RCardsLookUp rcardlookUpObj;
	@Override
	protected void onResume()
	{
		super.onResume();
		rcardlookUpObj = new RCardsLookUp(dbHelper);
		InsertDummyRCards();
	}
	
	private void InsertDummyRCards() {
		rcardlookUpObj.InsertrCardslookuptable("aa", "aa", "bbg", "aaa", 1,5,"aa", "aa", "bb", "aaa", "cc", "dd", "ee");
		rcardlookUpObj.InsertrCardslookuptable("aabb", "aaaaaa", "bbaaaaaaa", "aaaaaaaa", 2,3,"aabbbbb", "aacccc", "bbeeee", "aaaeeee", "cceee", "ddeee", "eeeee");
	}
	
	public void ListrCardsClick(View view)
	{
		Toast.makeText(this, "list rcard clicked", Toast.LENGTH_SHORT).show();
		Intent in = new Intent(this, RecruiterRcardListActivity.class);
		startActivity(in);
		
	}
}
