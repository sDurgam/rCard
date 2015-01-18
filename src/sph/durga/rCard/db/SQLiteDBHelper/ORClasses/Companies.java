package sph.durga.rCard.db.SQLiteDBHelper.ORClasses;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sph.durga.rCard.Utils.CompanyDisplay;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;

public class Companies
{
	SQLiteDBHelper dbHelper;
	public Companies(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
	}
	
	public ArrayList<CompanyDisplay> GetcompaniesList()
	{
		ArrayList<CompanyDisplay> companiesList = new ArrayList<CompanyDisplay>();
		CompanyDisplay cmpDisplayObj;
		int rcardSentInt;
		boolean isrcardSent;
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor = reader.query(SQLiteDBHelper.TABLE_COMPANY, new String[] { SQLiteDBHelper.COMPANY_NAME, SQLiteDBHelper.COMPANY_RCARD_SENT}, null, null, null, null, null);
		cursor.moveToFirst();
		do
		{
			cmpDisplayObj = new CompanyDisplay();
			cmpDisplayObj.setCompanyName(cursor.getString(0));
			rcardSentInt = cursor.getInt(1);
			if(rcardSentInt > 0)
			{
				isrcardSent = true;
			}
			else
			{
				isrcardSent = false;
			}
		cmpDisplayObj.setRcardSent(isrcardSent);
		companiesList.add(cmpDisplayObj);
		}while(cursor.moveToNext());
		cursor.close();
		return companiesList;
	}
}
