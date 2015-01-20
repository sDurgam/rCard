package sph.durga.rCard.jobseeker;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker.Companies;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendRcardActivity extends BaseActivity 
{
	Companies cmpObj;
	EditText companynameTxt;
	EditText companycontactTxt;
	EditText companyemailTxt;
	EditText companyotherInfoTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_send_rcard_activity);
		companynameTxt = (EditText) findViewById(R.id.companynameTxt);
		companycontactTxt = (EditText) findViewById(R.id.companycontactTxt);
		companyemailTxt = (EditText) findViewById(R.id.companyemailTxt);
		companyotherInfoTxt = (EditText) findViewById(R.id.companyotherInfoTxt);
	}

	private void SaveCompanyInfo()
	{
		String companyname = companynameTxt.getText().toString();
		String contactname = companycontactTxt.getText().toString();
		String email = companyemailTxt.getText().toString();
		String otherinfo = companyotherInfoTxt.getText().toString();
		int result;
		if(!companyname.equals(""))
		{
			result = cmpObj.SaveCompany(companyname, contactname, email, otherinfo);
			if(result == -1)
			{
				Toast.makeText(this, "company details could not be saved", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "company details successfully saved", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			Toast.makeText(this, "please enter company name", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		cmpObj = null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cmpObj = new Companies(dbHelper);
	}

	public void saveCompanyInfoClick(View view)
	{
		SaveCompanyInfo();
	}

	public void sendRcardClick(View view)
	{
		SaveCompanyInfo();
	}
}
