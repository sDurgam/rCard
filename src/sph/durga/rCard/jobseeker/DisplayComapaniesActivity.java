package sph.durga.rCard.jobseeker;

import java.util.ArrayList;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import sph.durga.rCard.Utils.CompaniesListAdapter;
import sph.durga.rCard.Utils.CompanyDisplay;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.Companies;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.GridView;

public class DisplayComapaniesActivity extends BaseActivity 
{
	GridView companiesListGrid;
	Companies companiesObj;
	CompaniesListAdapter csadapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_list_company_activity);
		companiesListGrid = (GridView) findViewById(R.id.comapniesListGrid);
	}
	

	@Override
	protected void onResume() 
	{
		super.onResume();
		companiesObj = new Companies(dbHelper);
		ArrayList<CompanyDisplay> companiesList = companiesObj.GetcompaniesList();
		csadapter = new CompaniesListAdapter(this, companiesList);
		companiesListGrid.setAdapter(csadapter);
	}

	
	public void addCompanyBtn(View view)
	{
		Intent in = new Intent(this, SendRcardActivity.class);
		startActivity(in);
	}
}
