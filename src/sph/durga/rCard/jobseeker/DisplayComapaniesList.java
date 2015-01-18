package sph.durga.rCard.jobseeker;

import java.util.ArrayList;

import sph.durga.rCard.R;
import sph.durga.rCard.Utils.CompaniesListAdapter;
import sph.durga.rCard.Utils.CompanyDisplay;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.Companies;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

public class DisplayComapaniesList extends FragmentActivity 
{
	GridView comapniesListGrid;
	SQLiteDBHelper dbHelper;
	Companies companiesObj;
	CompaniesListAdapter csadapter;
	
	
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
		ArrayList<CompanyDisplay> companiesList = companiesObj.GetcompaniesList();
		csadapter = new CompaniesListAdapter(this, companiesList);
		comapniesListGrid.setAdapter(csadapter);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		dbHelper.close();
	}
}
