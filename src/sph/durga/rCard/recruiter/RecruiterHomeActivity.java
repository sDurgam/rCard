package sph.durga.rCard.recruiter;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class RecruiterHomeActivity extends FragmentActivity {

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

	@Override
	protected void onResume()
	{
		
		super.onResume();
	}
	
	public void ListrCardsClick(View view)
	{
		Toast.makeText(this, "list rcard clicked", Toast.LENGTH_SHORT).show();
		Intent in = new Intent(this, RecruiterRcardListActivity.class);
		startActivity(in);
		
	}
}
