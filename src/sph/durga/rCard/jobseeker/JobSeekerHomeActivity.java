package sph.durga.rCard.jobseeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.resumeshare.R;

public class JobSeekerHomeActivity  extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_home_activity);
	}
	
	public void ReviewRcardClick(View view)
	{
		Intent in = new Intent(this, EditRcardActivity.class);
		startActivity(in);
	}
	
	public void SendRcardClick(View view)
	{
		Intent in = new Intent(this, SendRcardActivity.class);
		startActivity(in);
	}
}
