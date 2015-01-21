package sph.durga.rCard.Utils.jobseeker;

import java.util.ArrayList;

import sph.durga.rCard.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompaniesListAdapter extends BaseAdapter
{
	private Context mContext;
	ArrayList<CompanyDisplay> companiesList;

	public CompaniesListAdapter(Context c, ArrayList<CompanyDisplay> compList) {
		mContext = c;
		companiesList = compList;
	}

	public int getCount() {
		return companiesList.size();
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
		CompanyDisplay displayObj;
		if (convertView == null)
		{  // if it's not recycled, initialize some attributes
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			rowView = inflater.inflate(R.layout.jobseeker_company_row_grid, parent, false);
			displayObj = companiesList.get(position);
			TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
			TextView rcardSent = (TextView) rowView.findViewById(R.id.RcardSent);
			companyName.setText(displayObj.getCompanyName());
			rcardSent.setText(String.valueOf(displayObj.isRcardSent()));

		} else 
		{
			rowView = convertView;
		}
		return rowView;
	}
}
