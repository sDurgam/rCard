package sph.durga.rCard.jobseeker;

import sph.durga.rCard.R;
import sph.durga.rCard.Utils.CompaniesListAdapter.CustomAdapter;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.Companies;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class DisplayComapaniesList extends FragmentActivity 
{
	GridView comapniesListGrid;
	SQLiteDBHelper dbHelper;
	Companies companiesObj;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_list_company_activity);
		comapniesListGrid = (GridView) findViewById(R.id.comapniesListGrid);
	}
	

	@Override
	protected void onResume() 
	{
		super.onResume();
		dbHelper = new SQLiteDBHelper(this);
		companiesObj = new Companies(dbHelper);
		 companiesObj.GetcompaniesList();
		
		
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		dbHelper.close();
	}
	
	CustomAdapter adapter = new CustomAdapter(this, R.layout.row_grid, storeList);
	transcationsListView.setAdapter(adapter);
}
