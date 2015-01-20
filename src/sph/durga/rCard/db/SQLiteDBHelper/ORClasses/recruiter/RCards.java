package sph.durga.rCard.db.SQLiteDBHelper.ORClasses.recruiter;

import sph.durga.rCard.db.SQLiteDBHelper.SQLiteDBHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RCards 
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

	public RCards(SQLiteDBHelper dbhelper)
	{
		dbHelper = dbhelper;
	}
	
	public void FetchRCard()
	{
		SQLiteDatabase reader = dbHelper.getReadableDatabase();
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_RCARDS, new String[] { SQLiteDBHelper.RCARDS_NAME , 
				                                                         SQLiteDBHelper.RCARDS_PHONE, 
				                                                         SQLiteDBHelper.RCARDS_EMAIL, 
				                                                         SQLiteDBHelper.RCARDS_PRIMARY_SKILLS, 
				                                                         SQLiteDBHelper.RCARDS_ANDROID_EXP, 
				                                                         SQLiteDBHelper.RCARDS_IOS_EXP, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_ANDROID, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_IOS, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_OTHER, 
				                                                         SQLiteDBHelper.RCARDS_LINKEDIN_URL, 
				                                                         SQLiteDBHelper.RCARDS_RESUME_URL, 
				                                                         SQLiteDBHelper.RCARDS_HIGHEST_DEGREE, 
				                                                         SQLiteDBHelper.RCARDS_OTHER_INFO}, 
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
		String whereClause = SQLiteDBHelper.RCARDS_EMAIL + "= ?";
		String[] whereArgs = { email };
		Cursor cursor;
		cursor = reader.query(SQLiteDBHelper.TABLE_RCARDS, new String[] { SQLiteDBHelper.RCARDS_NAME , 
				                                                         SQLiteDBHelper.RCARDS_PHONE, 
				                                                         SQLiteDBHelper.RCARDS_EMAIL, 
				                                                         SQLiteDBHelper.RCARDS_PRIMARY_SKILLS, 
				                                                         SQLiteDBHelper.RCARDS_ANDROID_EXP, 
				                                                         SQLiteDBHelper.RCARDS_IOS_EXP, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_ANDROID, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_IOS, 
				                                                         SQLiteDBHelper.RCARDS_PORTFOLIO_OTHER, 
				                                                         SQLiteDBHelper.RCARDS_LINKEDIN_URL, 
				                                                         SQLiteDBHelper.RCARDS_RESUME_URL, 
				                                                         SQLiteDBHelper.RCARDS_HIGHEST_DEGREE, 
				                                                         SQLiteDBHelper.RCARDS_OTHER_INFO}, 
				                                                         whereClause, whereArgs, null, null, null);
		
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
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

	public long saveRcard(SQLiteDatabase db, String name, String phone, String email,
			String primaryskills, int androidexp, int iosexp,
			String andurl, String iosurl, String othurl, String linkurl,
			String resumeurl, String highestdegree, String otherinfo) 
	{
		String whereClause = SQLiteDBHelper.RCARDS_EMAIL + "= ?";
		String[] whereArgs = { email };
		ContentValues cv = new ContentValues();
		cv.put(SQLiteDBHelper.RCARDS_EMAIL, email);
		cv.put(SQLiteDBHelper.RCARDS_NAME, name);
		cv.put(SQLiteDBHelper.RCARDS_PHONE, phone);
		cv.put(SQLiteDBHelper.RCARDS_PRIMARY_SKILLS, primaryskills);
		cv.put(SQLiteDBHelper.RCARDS_ANDROID_EXP, androidexp);
		cv.put(SQLiteDBHelper.RCARDS_IOS_EXP, iosexp);
		cv.put(SQLiteDBHelper.RCARDS_PORTFOLIO_ANDROID, andurl);
		cv.put(SQLiteDBHelper.RCARDS_PORTFOLIO_IOS, iosurl);
		cv.put(SQLiteDBHelper.RCARDS_PORTFOLIO_OTHER, othurl);
		cv.put(SQLiteDBHelper.RCARDS_LINKEDIN_URL, linkurl);
		cv.put(SQLiteDBHelper.RCARDS_RESUME_URL, resumeurl);
		cv.put(SQLiteDBHelper.RCARDS_HIGHEST_DEGREE, highestdegree);
		cv.put(SQLiteDBHelper.RCARDS_OTHER_INFO, otherinfo);
		db.delete(SQLiteDBHelper.TABLE_RCARDS, whereClause, whereArgs);
		long result = db.insert(SQLiteDBHelper.TABLE_RCARDS, null, cv);
		//writer.close();
		return result;
	}
}
