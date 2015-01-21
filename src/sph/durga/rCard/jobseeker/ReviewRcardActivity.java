package sph.durga.rCard.jobseeker;

import sph.durga.rCard.BaseActivity;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.Rcard;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class ReviewRcardActivity extends BaseActivity
{
	Rcard rcardObj;
	EditText nameTxt;
	EditText phoneTxt;
	ViewSwitcher emailViewSwitcher;
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
		emailViewSwitcher = (ViewSwitcher) findViewById(R.id.emailViewSwitcher);
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
		rcardObj = new Rcard(dbHelper);
		rcardObj.FetchRCard();
		populateRCard();
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
	
	public void SaveAll(View view)
	{
		String name = nameTxt.getText().toString();
		String phone = phoneTxt.getText().toString();
		String email = ((EditText)emailViewSwitcher.getCurrentView()).getText().toString();
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
		long result = rcardObj.saveRcard(name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, degree, otherinfo);
		if(result != -1)
		{
			isEmailUpdated = true;
			switchEditToView();
			
		}
	}
	
	public void switchEditToView()
	{
		emailViewSwitcher.showNext();
		((TextView)emailViewSwitcher.getCurrentView()).setText(rcardObj.getEmail() == null ? "" : rcardObj.getEmail());
	}
	
	public void TextViewClicked(View currView)
	{
		ViewSwitcher parentViewSwitcher = (ViewSwitcher) currView.getParent();
		String viewString = ((TextView) currView).getText().toString();
		parentViewSwitcher.showNext();
		((TextView)parentViewSwitcher.getCurrentView()).setText(viewString);
	}
	public void populateRCard()
	{
		nameTxt.setText(rcardObj.getName() == null ? "" : rcardObj.getName());
		phoneTxt.setText(rcardObj.getPhone() == null ? "" : rcardObj.getPhone());
		((TextView)emailViewSwitcher.getCurrentView()).setText(rcardObj.getEmail() == null ? "" : rcardObj.getEmail());
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
