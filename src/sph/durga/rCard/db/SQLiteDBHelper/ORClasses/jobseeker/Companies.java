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
		reader.close();
		cursor.close();
		return companiesList;
	}


	private boolean GetCompany(SQLiteDatabase db,  String name, String whereClause, String[] whereArgs)
	{
		Cursor cursor = db.query(SQLiteDBHelper.TABLE_COMPANY, new String[] { SQLiteDBHelper.COMPANY_NAME, SQLiteDBHelper.COMPANY_MYRCARD_SENT}, whereClause, whereArgs, null, null, null);
		if(cursor.getCount() > 0)
		{
			cursor.close();
			//update
			return true;
		}
		else
		{
			//insert
			return false;
		}
	}
	
	private int GetCompanyID(SQLiteDatabase db,  String name, String whereClause, String[] whereArgs)
	{
		int companyId;
		Cursor cursor = db.query(SQLiteDBHelper.TABLE_COMPANY, new String[] { SQLiteDBHelper.COMPANY_ID}, whereClause, whereArgs, null, null, null);	
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			companyId = cursor.getInt(0);
		}
		else
		{
			companyId = -1;
		}
		cursor.close();
		return companyId;
	}

	public int SaveCompany(String name, String contactname, String email, String otherInfo)
	{
		int companyId;
		int result;
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		String whereClause = SQLiteDBHelper.COMPANY_NAME + "= ? AND " + SQLiteDBHelper.COMPANY_EMAIL + "= ?";
		String[] whereArgs = { name, email };
		ContentValues values = new ContentValues();
		values.put(SQLiteDBHelper.COMPANY_CONTACT_NAME, contactname);
		values.put(SQLiteDBHelper.COMPANY_EMAIL, email);
		values.put(SQLiteDBHelper.COMPANY_OTHER_INFO, otherInfo);
		companyId = GetCompanyID(writer, name, whereClause, whereArgs);
		if(companyId != -1)
		{
			//update
			result = writer.update(SQLiteDBHelper.TABLE_COMPANY, values, whereClause, whereArgs);
		}
		else
		{
			//insert
			values.put(SQLiteDBHelper.COMPANY_NAME, name);
			result = (int) writer.insert(SQLiteDBHelper.TABLE_COMPANY, null, values);
			if(result != -1)
			{
				//get company id
				companyId = GetCompanyID(writer, name, whereClause, whereArgs);
			}
			else
			{
				companyId = -1;
			}
		}
		writer.close();
		return companyId;
	}
	
	public void UpdateMyrCard(int ID, boolean isrCardSent)
	{
		ContentValues cv = new ContentValues();
		cv.put(SQLiteDBHelper.COMPANY_MYRCARD_SENT, isrCardSent);
		String whereClause = SQLiteDBHelper.COMPANY_ID + "= ?";
		String[] whereArgs = { String.valueOf(ID) };
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		writer.update(SQLiteDBHelper.TABLE_COMPANY, cv, whereClause, whereArgs);
		writer.close();	
	}
}
