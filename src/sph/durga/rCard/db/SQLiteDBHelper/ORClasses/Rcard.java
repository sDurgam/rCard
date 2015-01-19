package sph.durga.rCard.db.SQLiteDBHelper.ORClasses;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Rcard 
{
	SQLiteDBHelper dbHelper;
	String name = "";
	String phone = "";
	String email = "";
	String primaryskills = "";
	Integer androidexp = 0;
	Integer iosexp = 0;
	String andurl = "";
	String iosurl = "";
	String othurl = "";
	String linkurl = "";
	String resumeurl = "";
	String otherinfo = "";
	String degree = "";
	
	
	
	
	public SQLiteDBHelper getDbHelper() {
		return dbHelper;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getPrimaryskills() {
		return primaryskills;
	}

	public Integer getAndroidexp() {
		return androidexp;
	}

	public Integer getIosexp() {
		return iosexp;
	}

	public String getAndurl() {
		return andurl;
	}

	public String getIosurl() {
		return iosurl;
	}

	public String getOthurl() {
		return othurl;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public String getResumeurl() {
		return resumeurl;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public String getDegree() {
		return degree;
	}

	public Rcard(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
	}
	
//	public Rcard()
//	{
//		
//	}

	public void FetchRCard()
	{
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_RCARD, new String[] { SQLiteDBHelper.RCARD_NAME , 
				                                                         SQLiteDBHelper.RCARD_PHONE, 
				                                                         SQLiteDBHelper.RCARD_EMAIL, 
				                                                         SQLiteDBHelper.RCARD_PRIMARY_SKILLS, 
				                                                         SQLiteDBHelper.RCARD_ANDROID_EXP, 
				                                                         SQLiteDBHelper.RCARD_IOS_EXP, 
				                                                         SQLiteDBHelper.RCARD_PORTFOLIO_ANDROID, 
				                                                         SQLiteDBHelper.RCARD_PORTFOLIO_IOS, 
				                                                         SQLiteDBHelper.RCARD_PORTFOLIO_OTHER, 
				                                                         SQLiteDBHelper.RCARD_LINKEDIN_URL, 
				                                                         SQLiteDBHelper.RCARD_RESUME_URL, 
				                                                         SQLiteDBHelper.RCARD_HIGHEST_DEGREE, 
				                                                         SQLiteDBHelper.RCARD_OTHER_INFO}, 
				                                                         null, null, null, null, null);
		cursor.moveToFirst();
		if(cursor.getCount() > 0) {
			name = cursor.getString(0);
			phone = cursor.getString(1);
			email = cursor.getString(2);
			primaryskills = cursor.getString(3);
			androidexp = cursor.getInt(4);
			iosexp = cursor.getInt(5);
			andurl = cursor.getString(6);
			iosurl = cursor.getString(7);
			othurl = cursor.getString(8);
			linkurl = cursor.getString(9);
			resumeurl = cursor.getString(10);
			otherinfo = cursor.getString(11);
			degree = cursor.getString(12);
		}
		cursor.close();
	}
	
//	public void UpdateField(String key, String value)
//	{
//		SQLiteDatabase writer = dbHelper.getWritableDatabase();
//		ContentValues cv = new ContentValues();
//		cv.put(key, value);
//		writer.update(SQLiteDBHelper.TABLE_RCARD, cv, null, null);
//		writer.close();
//	}

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
