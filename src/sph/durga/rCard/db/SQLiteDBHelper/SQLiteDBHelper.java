package sph.durga.rCard.db.SQLiteDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper
{
	public SQLiteDBHelper(Context context, String name, CursorFactory factory,
			int version)
	{
		super(context, name, factory, version);
	}
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "resumeshare.db";
	
	// User table name
	public static final String TABLE_USER = "user";
	
	//User columns
	private static final String KEY_ID = "id";
	public static final String KEY_CATEGORY = "category";
	
	//Rcard table name
	public static final String TABLE_RCARD = "rcard";
	
	//Rcard columns
	public static final String RCARD_NAME = "name";
	public static final String RCARD_PHONE = "phone";
	public static final String RCARD_EMAIL = "email";
	public static final String RCARD_PRIMARY_SKILLS = "primary_skills";
	public static final String RCARD_ANDROID_EXP = "android_experience";
	public static final String RCARD_IOS_EXP = "ios_experience";
	public static final String RCARD_PORTFOLIO_ANDROID = "android_url";
	public static final String RCARD_PORTFOLIO_IOS = "ios_url";
	public static final String RCARD_PORTFOLIO_OTHER = "other_url";
	public static final String RCARD_LINKEDIN_URL = "linkedin_url";
	public static final String RCARD_RESUME_URL = "resume_url";
	public static final String RCARD_HIGHEST_DEGREE = "highest_degree";
	public static final String RCARD_OTHER_INFO = "other_info";
	
	
	//Company table name
	public static final String TABLE_COMPANY = "company";
	
	//company columns
	public static final String COMPANY_ID = "id";
	public static final String COMPANY_NAME = "name";
	public static final String COMPANY_CONTACT_NAME = "contact_name";
	public static final String COMPANY_EMAIL = "email";
	public static final String COMPANY_OTHER_INFO = "info";
	public static final String COMPANY_RCARD_SENT = "rcard_sent";
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		String CREATE_USER_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER DEFAULT -1)", TABLE_USER, KEY_ID, KEY_CATEGORY);
		db.execSQL(CREATE_USER_TABLE);
		InsertUserTable(db);
		
	}
	
	public SQLiteDBHelper (Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		onCreate(db);
	}
	
	public void CreateJobSeekerTables(SQLiteDatabase db)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, 1);
		db.update(TABLE_USER, cv, null, null);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RCARD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
		String CREATE_RCARD_TABLE = String.format("CREATE TABLE %s (%s TEXT NOT NULL, %s TEXT, %s TEXT PRIMARY KEY NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT)", TABLE_RCARD, RCARD_NAME , RCARD_PHONE, RCARD_EMAIL, RCARD_PRIMARY_SKILLS, RCARD_ANDROID_EXP, RCARD_IOS_EXP, RCARD_PORTFOLIO_ANDROID, RCARD_PORTFOLIO_IOS, RCARD_PORTFOLIO_OTHER, RCARD_LINKEDIN_URL, RCARD_RESUME_URL, RCARD_HIGHEST_DEGREE, RCARD_OTHER_INFO);
		db.execSQL(CREATE_RCARD_TABLE);
		String CREATE_COMPANY_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s BOOLEAN  DEFAULT 0)", TABLE_COMPANY, COMPANY_ID, COMPANY_NAME, COMPANY_CONTACT_NAME, COMPANY_EMAIL, COMPANY_OTHER_INFO, COMPANY_RCARD_SENT);
		db.execSQL(CREATE_COMPANY_TABLE);
	}
	
	//by default -1 for user category
	private void InsertUserTable(SQLiteDatabase db)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, -1);
		db.insert(TABLE_USER, null, cv);
	}
	
	public void UpdateUserTable(SQLiteDatabase db, String category)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, category);
		db.update(TABLE_USER, cv, null, null) ;
		db.close();
	}
	
	public int GetUserCategory(SQLiteDatabase db)
	{
		Cursor cursor = db.query(TABLE_USER, new String[]{ KEY_CATEGORY }, null, null, null, null, null);
		cursor.moveToFirst();
		int category = cursor.getInt(0);
		cursor.close();
		return category;
	}
//	//summary table column names
//	private static final String SUMMARY_ID = "id";
//	public static final String SUMMARY_NAME="name";
//	public static final String SUMMARY_ANDROID = "skills";
//	public static final String SUMMARY_YEARS_OF_EXPERIENCE = "experience";
}
