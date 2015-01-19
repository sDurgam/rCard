package sph.durga.rCard;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.jobseeker.JobSeekerHomeActivity;
import sph.durga.rCard.recruiter.RecruiterHomeActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class HomeScreenActivity extends FragmentActivity
{
	SQLiteDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_activity);
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		dbHelper = new SQLiteDBHelper(this);
		int category = GetUserCategory();
		if(category == 1) //job seeker
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
	protected void onPause()
	{
		super.onPause();
		dbHelper.close();
	}
	
	private int GetUserCategory()
	{
		return dbHelper.GetUserCategory(dbHelper.getReadableDatabase());
	}
	
	public void jobSeekerClick(View view)
	{
		//create user and category table
		SQLiteDatabase employerListDb = dbHelper.getWritableDatabase();
		dbHelper.CreateJobSeekerTables(employerListDb);
		employerListDb.close();
		
		//redirect user to review or share rcard page
		Intent in = new Intent(this, JobSeekerHomeActivity.class);
		startActivity(in);
	}
	
	public void recruiterClick(View view)
	{
		
	}
}
