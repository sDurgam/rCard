package sph.durga.rCard.jobseeker;

import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.Rcard;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ReviewRcardActivity extends FragmentActivity
{
	SQLiteDBHelper dbHelper;
	Rcard rcardObj;
	EditText nameTxt;
	EditText phoneTxt;
	EditText emailTxt;
	EditText primaryskillsTxt;
	Spinner androidexpList;
	Spinner iosexpList;
	EditText androidportfolioTxt;
	EditText iosportfolioTxt;
	EditText othportfolioTxt;
	EditText linkedinTxt;
	EditText resumeTxt;
	EditText degreeTxt;
	EditText otherinfoTxt;
	boolean isEmailUpdated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseeker_review_rcard_activity);
		
		nameTxt = (EditText) findViewById(R.id.nameTxt);
		phoneTxt = (EditText) findViewById(R.id.phoneTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		primaryskillsTxt = (EditText) findViewById(R.id.primaryskillsTxt);
		androidexpList = (Spinner) findViewById(R.id.androidexpList);
		iosexpList = (Spinner) findViewById(R.id.iosexpList);
		androidportfolioTxt = (EditText) findViewById(R.id.androidportfolioTxt);
		iosportfolioTxt = (EditText) findViewById(R.id.iosportfolioTxt);
		othportfolioTxt = (EditText) findViewById(R.id.othportfolioTxt);
		linkedinTxt = (EditText) findViewById(R.id.linkedinTxt);
		resumeTxt = (EditText) findViewById(R.id.resumeTxt);
		degreeTxt = (EditText) findViewById(R.id.highestdegreeTxt);
		otherinfoTxt = (EditText) findViewById(R.id.otherinfoTxt);
		PopulateSpinners();

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		dbHelper = new SQLiteDBHelper(this);
		rcardObj = new Rcard(dbHelper);
		rcardObj.FetchRCard();
		populateRCard();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		dbHelper.close();
	}


	private void PopulateSpinners() 
	{
		Integer[] array = new Integer[50];
		for(int i =0; i < 50; i++)
		{
			array[i] = i;
		}

		androidexpList.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_list_item_1, array));
		iosexpList.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_list_item_1, array));

	}

//
//	public void saveName(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String name = nameTxt.toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_NAME, name);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void savePhone(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String phone = phoneTxt.toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_PHONE, phone);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void insertEmail(View view)
//	{
//		if(!isEmailUpdated)
//		{
//
//			String email = emailTxt.toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.InsertField(SQLiteDBHelper.RCARD_EMAIL, email);
//			
//			
//			
//			
//			isEmailUpdated = true;
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void savePrimarySkills(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String primaryskills = primaryskillsTxt.toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_PRIMARY_SKILLS, primaryskills);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveandroidExp(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String androidexp = androidexpList.getSelectedItem().toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_ANDROID_EXP,  androidexp);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveiosExp(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String iosexp = iosexpList.getSelectedItem().toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_IOS_EXP, iosexp);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveandroidportfolio(View view)
//	{
//		if(isEmailUpdated)
//		{
//			String andurl = androidportfolioTxt.toString();
//			SQLiteDatabase writer = dbHelper.getWritableDatabase();
//			rcardObj.UpdateField(SQLiteDBHelper.RCARD_PORTFOLIO_ANDROID, andurl);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveiosportfolio(View view)
//	{
//		if(isEmailUpdated)
//		{
//		String iosurl = iosportfolioTxt.toString();
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		rcardObj.UpdateField(SQLiteDBHelper.RCARD_PORTFOLIO_IOS, iosurl);	
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveothportfolio(View view)
//	{
//		if(isEmailUpdated)
//		{
//		String othurl = othportfolioTxt.toString();
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		rcardObj.UpdateField(SQLiteDBHelper.RCARD_PORTFOLIO_OTHER, othurl);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void savelinkedin(View view)
//	{
//		if(isEmailUpdated)
//		{
//		String linkurl = linkedinTxt.toString();
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		rcardObj.UpdateField(SQLiteDBHelper.RCARD_LINKEDIN_URL, linkurl);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveresumeurl(View view)
//	{
//		if(isEmailUpdated)
//		{
//		String resumeurl = resumeTxt.toString();
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		rcardObj.UpdateField(SQLiteDBHelper.RCARD_RESUME_URL, resumeurl);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	public void saveotherinfo(View view)
//	{
//		if(isEmailUpdated)
//		{
//		String otherinfo = otherinfoTxt.toString();
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		rcardObj.UpdateField(SQLiteDBHelper.RCARD_OTHER_INFO, otherinfo);
//		}
//		else
//		{
//			Toast.makeText(this, Constants.email_error, Toast.LENGTH_SHORT).show();
//		}
//	}

	public void SaveAll(View view)
	{
		String name = nameTxt.getText().toString();
		String phone = phoneTxt.getText().toString();
		String email = emailTxt.getText().toString();
		String primaryskills = primaryskillsTxt.getText().toString();
		String androidexp = androidexpList.getSelectedItem().toString();
		String iosexp = iosexpList.getSelectedItem().toString();
		String andurl = androidportfolioTxt.getText().toString();
		String iosurl = iosportfolioTxt.getText().toString();
		String othurl = othportfolioTxt.getText().toString();
		String linkurl = linkedinTxt.getText().toString();
		String resumeurl = resumeTxt.getText().toString();
		String otherinfo = otherinfoTxt.getText().toString();
		String degree = degreeTxt.getText().toString();
		long result = rcardObj.saveAll(name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, degree, otherinfo);
		if(result != -1)
		{
			isEmailUpdated = true;
		}
	}
	
	public void populateRCard()
	{
		nameTxt.setText(rcardObj.getName() == null ? "" : rcardObj.getName());
		phoneTxt.setText(rcardObj.getPhone() == null ? "" : rcardObj.getPhone());
		emailTxt.setText(rcardObj.getEmail() == null ? "" : rcardObj.getEmail());
		primaryskillsTxt.setText(rcardObj.getPrimaryskills() == null ? "" : rcardObj.getPrimaryskills());
		androidexpList.setSelection(rcardObj.getAndroidexp() == null ? 0 : rcardObj.getAndroidexp());
		iosexpList.setSelection(rcardObj.getIosexp() == null ? 0 : rcardObj.getIosexp());
		androidportfolioTxt.setText(rcardObj.getAndurl() == null ? "" : rcardObj.getAndurl());
		iosportfolioTxt.setText(rcardObj.getIosurl() == null ? "" : rcardObj.getIosurl());
		othportfolioTxt.setText(rcardObj.getOthurl() == null ? "" : rcardObj.getOthurl());
		linkedinTxt.setText(rcardObj.getLinkurl() == null ? "" : rcardObj.getLinkurl());
		resumeTxt.setText(rcardObj.getResumeurl() == null ? "" : rcardObj.getResumeurl());
		otherinfoTxt.setText(rcardObj.getOtherinfo() == null ? "" : rcardObj.getOtherinfo());
		degreeTxt.setText(rcardObj.getDegree() == null ? "" : rcardObj.getDegree());
	}
}
