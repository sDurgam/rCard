package sph.durga.rCard.db.SQLiteDBHelper.ORClasses.jobseeker;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyRcard 
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

	public MyRcard(SQLiteDBHelper dbhelper)
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
		cursor = reader.query(SQLiteDBHelper.TABLE_MYRCARD, new String[] { SQLiteDBHelper.MYRCARD_NAME , 
				                                                         SQLiteDBHelper.MYRCARD_PHONE, 
				                                                         SQLiteDBHelper.MYRCARD_EMAIL, 
				                                                         SQLiteDBHelper.MYRCARD_PRIMARY_SKILLS, 
				                                                         SQLiteDBHelper.MYRCARD_ANDROID_EXP, 
				                                                         SQLiteDBHelper.MYRCARD_IOS_EXP, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_ANDROID, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_IOS, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_OTHER, 
				                                                         SQLiteDBHelper.MYRCARD_LINKEDIN_URL, 
				                                                         SQLiteDBHelper.MYRCARD_RESUME_URL, 
				                                                         SQLiteDBHelper.MYRCARD_HIGHEST_DEGREE, 
				                                                         SQLiteDBHelper.MYRCARD_OTHER_INFO}, 
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
	
	public void FetchRCard(String email)
	{
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		String whereClause = SQLiteDBHelper.MYRCARD_EMAIL + "= ?";
		String[] whereArgs = { email };
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_MYRCARD, new String[] { SQLiteDBHelper.MYRCARD_NAME , 
				                                                         SQLiteDBHelper.MYRCARD_PHONE, 
				                                                         SQLiteDBHelper.MYRCARD_EMAIL, 
				                                                         SQLiteDBHelper.MYRCARD_PRIMARY_SKILLS, 
				                                                         SQLiteDBHelper.MYRCARD_ANDROID_EXP, 
				                                                         SQLiteDBHelper.MYRCARD_IOS_EXP, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_ANDROID, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_IOS, 
				                                                         SQLiteDBHelper.MYRCARD_PORTFOLIO_OTHER, 
				                                                         SQLiteDBHelper.MYRCARD_LINKEDIN_URL, 
				                                                         SQLiteDBHelper.MYRCARD_RESUME_URL, 
				                                                         SQLiteDBHelper.MYRCARD_HIGHEST_DEGREE, 
				                                                         SQLiteDBHelper.MYRCARD_OTHER_INFO}, 
				                                                         whereClause, whereArgs, null, null, null);
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

	public long saveRcard(String name, String phone, String email,
			String primaryskills, int androidexp2, int iosexp2,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo) 
	{
		SQLiteDatabase writer = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(SQLiteDBHelper.MYRCARD_EMAIL, email);
		cv.put(SQLiteDBHelper.MYRCARD_NAME, name);
		cv.put(SQLiteDBHelper.MYRCARD_PHONE, phone);
		cv.put(SQLiteDBHelper.MYRCARD_PRIMARY_SKILLS, primaryskills);
		cv.put(SQLiteDBHelper.MYRCARD_ANDROID_EXP, androidexp2);
		cv.put(SQLiteDBHelper.MYRCARD_IOS_EXP, iosexp2);
		cv.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_ANDROID, andurl);
		cv.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_IOS, iosurl);
		cv.put(SQLiteDBHelper.MYRCARD_PORTFOLIO_OTHER, othurl);
		cv.put(SQLiteDBHelper.MYRCARD_LINKEDIN_URL, linkurl);
		cv.put(SQLiteDBHelper.MYRCARD_RESUME_URL, resumeurl);
		cv.put(SQLiteDBHelper.MYRCARD_HIGHEST_DEGREE, highestdegree);
		cv.put(SQLiteDBHelper.MYRCARD_OTHER_INFO, otherinfo);
		writer.delete(SQLiteDBHelper.TABLE_MYRCARD, null, null);
		long result = writer.insert(SQLiteDBHelper.TABLE_MYRCARD, null, cv);
		writer.close();
		return result;
	}
}
