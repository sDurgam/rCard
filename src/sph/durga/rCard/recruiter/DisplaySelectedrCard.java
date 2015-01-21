package sph.durga.rCard.recruiter;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCards;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DisplaySelectedrCard extends BaseActivity
{
	Button saveAllBtn;
	String email;
	RCards rcardsObj;
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
	
	@Override
	protected void onCreate(Bundle extras)
	{
		super.onCreate(extras);
		setContentView(R.layout.jobseeker_review_rcard_activity);
		saveAllBtn = (Button) findViewById(R.id.saveAllBtn);
		saveAllBtn.setVisibility(View.INVISIBLE);
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
		email = getIntent().getExtras().getString(Constants.jobseeker_email);
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		rcardsObj = new RCards(dbHelper);
		rcardsObj.FetchRCard(email);
		populateRCard();
		//Get rcard
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	public void populateRCard()
	{
		nameTxt.setText(rcardsObj.getName() == null ? "" : rcardsObj.getName());
		phoneTxt.setText(rcardsObj.getPhone() == null ? "" : rcardsObj.getPhone());
		emailTxt.setText(email);
		primaryskillsTxt.setText(rcardsObj.getPrimaryskills() == null ? "" : rcardsObj.getPrimaryskills());
		androidexpList.setSelection(rcardsObj.getAndroidexp() == null ? 0 : rcardsObj.getAndroidexp());
		iosexpList.setSelection(rcardsObj.getIosexp() == null ? 0 : rcardsObj.getIosexp());
		androidportfolioTxt.setText(rcardsObj.getAndurl() == null ? "" : rcardsObj.getAndurl());
		iosportfolioTxt.setText(rcardsObj.getIosurl() == null ? "" : rcardsObj.getIosurl());
		othportfolioTxt.setText(rcardsObj.getOthurl() == null ? "" : rcardsObj.getOthurl());
		linkedinTxt.setText(rcardsObj.getLinkurl() == null ? "" : rcardsObj.getLinkurl());
		resumeTxt.setText(rcardsObj.getResumeurl() == null ? "" : rcardsObj.getResumeurl());
		otherinfoTxt.setText(rcardsObj.getOtherinfo() == null ? "" : rcardsObj.getOtherinfo());
		degreeTxt.setText(rcardsObj.getDegree() == null ? "" : rcardsObj.getDegree());
	}
	
}
