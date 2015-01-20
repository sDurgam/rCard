package sph.durga.rCard.recruiter;

import java.util.ArrayList;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import sph.durga.rCard.Utils.CompaniesListAdapter;
import sph.durga.rCard.Utils.CompanyDisplay;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.RcardLookUp;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class RecruiterRcardListActivity extends BaseActivity
{

	GridView rCardGrid;
	RcardLookUp rcardlookUpObj;
	ArrayList<String[]> rcardlookupList;
	
	@Override
	protected void onCreate(Bundle arg0) 
	{
		super.onCreate(arg0);
		setContentView(R.layout.recruiter_display_job_seeker_activity);
		rCardGrid = (GridView) findViewById(R.id.displayRcardGrid);
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
		rcardlookUpObj = new RcardLookUp(dbHelper);
		rcardlookupList =  rcardlookUpObj.FetchrCardLookupList();
//		companiesListGrid.setAdapter(csadapter);
	}

	
}
