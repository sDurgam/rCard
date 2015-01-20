package sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import sph.durga.rCard.Utils.jobseeker.CompanyDisplay;
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
		Cursor cursor = reader.query(SQLiteDBHelper.TABLE_COMPANY, new String[] { SQLiteDBHelper.COMPANY_NAME, SQLiteDBHelper.COMPANY_MYRCARD_SENT}, null, null, null, null, null);
		if(cursor.getCount() > 0)
		{
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
		}
		cursor.close();
		reader.close();
		return companiesList;
	}


	private boolean GetCompany(SQLiteDatabase db,  String name, String whereClause, String[] whereArgs)
	{
		Cursor cursor = db.query(SQLiteDBHelper.TABLE_COMPANY, new String[] { SQLiteDBHelper.COMPANY_NAME, SQLiteDBHelper.COMPANY_MYRCARD_SENT}, whereClause, whereArgs, null, null, null);
		if(cursor.getCount() > 0)
		{
			//update
			return true;
		}
		else
		{
			//insert
			return false;
		}

	}

	public int SaveCompany(String name, String contactname, String email, String otherInfo)
	{
		int result;
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		String whereClause = SQLiteDBHelper.COMPANY_NAME + "= ?";
		String[] whereArgs = { name };
		ContentValues values = new ContentValues();
		values.put(SQLiteDBHelper.COMPANY_CONTACT_NAME, contactname);
		values.put(SQLiteDBHelper.COMPANY_EMAIL, email);
		values.put(SQLiteDBHelper.COMPANY_OTHER_INFO, otherInfo);
		if(GetCompany(writer, name, whereClause, whereArgs))
		{
			//update
			result = writer.update(SQLiteDBHelper.TABLE_COMPANY, values, whereClause, whereArgs);
		}
		else
		{
			//insert
			values.put(SQLiteDBHelper.COMPANY_NAME, name);
			result = (int) writer.insert(SQLiteDBHelper.TABLE_COMPANY, null, values);
		}
		writer.close();
		return result;
	}
}
