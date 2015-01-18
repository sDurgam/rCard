package sph.durga.rCard;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.jobseeker.JobSeekerHomeActivity;
import sph.durga.rCard.recruiter.RecruiterHomeActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.resumeshare.R;

public class HomeScreenActivity extends FragmentActivity
{
	SQLiteDBHelper dbHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		int category = GetUserCategory();
		if(category == -1)
		{
			setContentView(R.layout.home_screen_activity);
		}
		else if(category == 1) //job seeker
		{
			Intent in = new Intent(this, JobSeekerHomeActivity.class);
			startActivity(in);
		}
		else if(category == 2) //recruiter
		{
			Intent in = new Intent(this, RecruiterHomeActivity.class);
			startActivity(in);
		}
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		dbHelper = new SQLiteDBHelper(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		dbHelper.close();
	}
	
	private int GetUserCategory()
	{
		return dbHelper.GetUserCategory(dbHelper.getReadableDatabase());
	}
	
	public void jobSeekerClick()
	{
		//create user and category table

		//redirect user to review or share rcard page
	}
	
	public void recruiterClick()
	{
		
	}
}
