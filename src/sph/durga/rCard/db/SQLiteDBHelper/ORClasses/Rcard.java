package sph.durga.rCard.db.SQLiteDBHelper.ORClasses;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Rcard 
{
	SQLiteDBHelper dbHelper;
	public Rcard(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
	}

	public boolean GetEmail()
	{
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor = reader.query(SQLiteDBHelper.TABLE_RCARD, new String[] { SQLiteDBHelper.RCARD_EMAIL}, null, null, null, null, null);
		cursor.moveToFirst();
		int cnt = cursor.getCount();
		cursor.close();
		return cnt == 1;
	}

	public void UpdateField(String key, String value)
	{
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(key, value);
		writer.update(SQLiteDBHelper.TABLE_RCARD, cv, null, null);
		writer.close();
	}

//	public void InsertField(String key, String value)
//	{
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		writer.delete(SQLiteDBHelper.TABLE_RCARD, null, null);
//		ContentValues cv = new ContentValues();
//		cv.put(key, value);
//		writer.insert(SQLiteDBHelper.TABLE_RCARD, null, cv);
//		writer.close();
//	}

	public long saveAll(String name, String phone, String email,
			String primaryskills, String androidexp, String iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo) 
	{
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(SQLiteDBHelper.RCARD_EMAIL, email);
		cv.put(SQLiteDBHelper.RCARD_NAME, name);
		cv.put(SQLiteDBHelper.RCARD_PHONE, phone);
		cv.put(SQLiteDBHelper.RCARD_PRIMARY_SKILLS, primaryskills);
		cv.put(SQLiteDBHelper.RCARD_ANDROID_EXP, androidexp);
		cv.put(SQLiteDBHelper.RCARD_IOS_EXP, iosexp);
		cv.put(SQLiteDBHelper.RCARD_PORTFOLIO_ANDROID, andurl);
		cv.put(SQLiteDBHelper.RCARD_PORTFOLIO_IOS, iosurl);
		cv.put(SQLiteDBHelper.RCARD_PORTFOLIO_OTHER, othurl);
		cv.put(SQLiteDBHelper.RCARD_LINKEDIN_URL, linkurl);
		cv.put(SQLiteDBHelper.RCARD_RESUME_URL, resumeurl);
		cv.put(SQLiteDBHelper.RCARD_HIGHEST_DEGREE, highestdegree);
		cv.put(SQLiteDBHelper.RCARD_OTHER_INFO, otherinfo);
		writer.delete(SQLiteDBHelper.TABLE_RCARD, null, null);
		long result = writer.insert(SQLiteDBHelper.TABLE_RCARD, null, cv);
		writer.close();
		return result;
	}
}
