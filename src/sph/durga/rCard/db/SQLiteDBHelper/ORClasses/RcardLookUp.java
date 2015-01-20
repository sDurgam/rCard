package sph.durga.rCard.db.SQLiteDBHelper.ORClasses;

import java.util.ArrayList;

import sph.durga.rCard.Constants.jobseeker_priority;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Constants;

public class RcardLookUp
{

	SQLiteDBHelper dbHelper;
	String name;
	jobseeker_priority priority;
	String email;
	Rcard rcardObj;

	public SQLiteDBHelper getDbHelper() 
	{
		return dbHelper;
	}

	public RcardLookUp(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
		rcardObj = new Rcard(dbhelper);
	}
	
	public ArrayList<String[]> FetchrCardLookupList()
	{
		int priority_int;
		ArrayList<String[]> rlookupList = new ArrayList<String[]>();
		String[] rlookupdata;
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_RCARDLOOKUP,  new String[] { SQLiteDBHelper.RCARDLOOKUP_NAME, SQLiteDBHelper.RCARDLOOKUP_PRIORITY, SQLiteDBHelper.RCARDLOOKUP_EMAIL},  null, null, null, null, null);
		cursor.moveToFirst();
		if(cursor.getCount() > 0) 
		{
			rlookupdata = new String[2];
			rlookupdata[0] = cursor.getString(0);
			priority_int = cursor.getInt(1);
			rlookupdata[1] = jobseeker_priority.values()[priority_int].toString();
			rlookupList.add(rlookupdata);
		}
		cursor.close();
		reader.close();
		return rlookupList;
	}

	protected void GetrCard(String email)
	{
		rcardObj.FetchRCard(email);
	}

	protected void InsertrCardLookupTable(String name, String phone, String email,
			String primaryskills, String androidexp, String iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo)
	{
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		long result = InsertrCard(name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, highestdegree, otherinfo);
		if(result != -1)
		{
			ContentValues cv = new ContentValues();
			cv.put(SQLiteDBHelper.RCARDLOOKUP_NAME, name);
			cv.put(SQLiteDBHelper.RCARDLOOKUP_EMAIL, email);
			writer.insert(SQLiteDBHelper.TABLE_RCARDLOOKUP, null, cv);
		}
		writer.close();
	}

	protected long InsertrCard(String name, String phone, String email,
			String primaryskills, String androidexp, String iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo)
	{
		return rcardObj.saveRcard(name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, highestdegree, otherinfo);
	}

}
