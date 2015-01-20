package sph.durga.rCard.Utils.recruiter;

import java.util.ArrayList;

import sph.durga.rCard.Constants;
import sph.durga.rCard.R;
import sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter.RCardsLookUp;
import sph.durga.rCard.recruiter.DisplaySelectedrCard;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class jobSeekerListAdapter extends BaseAdapter
{
private Context mContext;
ArrayList<String[]> jobseekerlist;
RCardsLookUp rcardlookupObj;

public jobSeekerListAdapter(Context c, ArrayList<String[]>  jseekerList, RCardsLookUp rCardlkObj) 
{
	mContext = c;
	jobseekerlist = jseekerList;
	rcardlookupObj = rCardlkObj;
}

public int getCount() {
	return jobseekerlist.size();
}

public Object getItem(int position) {
	return null;
}

public long getItemId(int position) {
	return 0;
}

// create a new ImageView for each item referenced by the Adapter
public View getView(int position, View convertView, ViewGroup parent) 
{
	View rowView;
	String[] displayObj;
	if (convertView == null)
	{  // if it's not recycled, initialize some attributes
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		rowView = inflater.inflate(R.layout.recruiter_jobseeker_row_grid, parent, false);
		displayObj = new String[3];
		displayObj = jobseekerlist.get(position);
		TextView jobseekerName = (TextView) rowView.findViewById(R.id.jobseekerName);
		
		Spinner prioritySpinner = (Spinner) rowView.findViewById(R.id.jobseekerPriority);
		Constants.jobseeker_priority priority = Constants.jobseeker_priority.valueOf(displayObj[1]);
		prioritySpinner.setSelection(priority.ordinal());
		
		Button jobseekerrCardBtn = (Button) rowView.findViewById(R.id.jobseekerrCard);
		jobseekerName.setText(displayObj[0]);
		jobseekerrCardBtn.setTag(displayObj[2]);	//save email for further processing
		jobseekerrCardBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				//display rCard using the email from the tag
				Intent in = new Intent(mContext, DisplaySelectedrCard.class);
				Bundle mBundle = new Bundle();
				mBundle.putString(Constants.jobseeker_email, v.getTag().toString());
				in.putExtras(mBundle);
				 mContext.startActivity(in);
			}
		});
		
		
		Button jobseekersaverCardBtn = (Button) rowView.findViewById(R.id.jobseekersave);
		jobseekersaverCardBtn.setTag(displayObj[2]);	//save email for further processing
		jobseekersaverCardBtn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Spinner prioritySpinner = (Spinner) ((View)(v.getParent())).findViewById(R.id.jobseekerPriority);
				int priority = prioritySpinner.getSelectedItemPosition();
				//save priority
				rcardlookupObj.savePriority(v.getTag().toString(), priority);
			}
		});
	} else 
	{
		rowView = convertView;
	}
	return rowView;
}
}
