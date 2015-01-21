package sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter;

import java.util.ArrayList;

import sph.durga.rCard.Constants.jobseeker_priority;
import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RCardsLookUp
{
	SQLiteDBHelper dbHelper;
	String name;
	jobseeker_priority priority;
	String email;
	RCards rcardsObj;

	public SQLiteDBHelper getDbHelper() 
	{
		return dbHelper;
	}

	public RCardsLookUp(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
		rcardsObj = new RCards(dbhelper);
	}

	public ArrayList<String[]> FetchrCardLookupList()
	{
		int priority_int;
		ArrayList<String[]> rlookupList = new ArrayList<String[]>();
		String[] rlookupdata;
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_RCARDSLOOKUP,  new String[] { SQLiteDBHelper.RCARDSLOOKUP_NAME, SQLiteDBHelper.RCARDSLOOKUP_PRIORITY, SQLiteDBHelper.RCARDSLOOKUP_EMAIL},  null, null, null, null, null);

		if(cursor.getCount() > 0) 
		{
			cursor.moveToFirst();
			do
			{
				rlookupdata = new String[3];
				rlookupdata[0] = cursor.getString(0);
				priority_int = cursor.getInt(1);
				rlookupdata[1] = jobseeker_priority.values()[priority_int].toString();
				rlookupdata[2] = cursor.getString(2);
				rlookupList.add(rlookupdata);
			}while(cursor.moveToNext());
		}
		reader.close();
		cursor.close();

		return rlookupList;
	}

	protected int GetrCard(String email)
	{
		return rcardsObj.FetchRCard(email);
	}

	public void InsertrCardslookuptable(String name, String phone, String email,
			String primaryskills, int androidexp, int iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo)
	{
		long i;
		String whereClause = SQLiteDBHelper.RCARDSLOOKUP_EMAIL + "= ?";
		String[] whereArgs = { email };
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		long result = InsertrCard(writer, name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, highestdegree, otherinfo);
		if(result != -1)
		{
			ContentValues cv = new ContentValues();
			cv.put(SQLiteDBHelper.RCARDSLOOKUP_NAME, name);
			cv.put(SQLiteDBHelper.RCARDSLOOKUP_EMAIL, email);
			writer.delete(SQLiteDBHelper.TABLE_RCARDSLOOKUP, whereClause, whereArgs);
			i = writer.insert(SQLiteDBHelper.TABLE_RCARDSLOOKUP, null, cv);
		}
		writer.close();
	}

	protected long InsertrCard(SQLiteDatabase writer, String name, String phone, String email,
			String primaryskills, int androidexp, int iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo)
	{
		return rcardsObj.saveRcard(writer, name, phone, email, primaryskills, androidexp, iosexp, andurl, iosurl, othurl, linkurl, resumeurl, highestdegree, otherinfo);
	}

	public void savePriority(String email, int priority)
	{
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		String whereClause = SQLiteDBHelper.RCARDSLOOKUP_EMAIL + "= ?";
		String[] whereArgs = { email };
		ContentValues cv = new ContentValues();
		cv.put(SQLiteDBHelper.RCARDSLOOKUP_PRIORITY, priority);
		cv.put(SQLiteDBHelper.RCARDSLOOKUP_EMAIL, email);
		writer.update(SQLiteDBHelper.TABLE_RCARDSLOOKUP, cv, whereClause, whereArgs);
		writer.close();
	}

}
