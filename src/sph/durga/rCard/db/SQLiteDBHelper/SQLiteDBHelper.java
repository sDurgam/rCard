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

	//MYRCARD table name for job seeker
	public static final String TABLE_MYRCARD = "myrcard";

	//MYRCARD columns
	public static final String MYRCARD_NAME = "name";
	public static final String MYRCARD_PHONE = "phone";
	public static final String MYRCARD_EMAIL = "email";
	public static final String MYRCARD_PRIMARY_SKILLS = "primary_skills";
	public static final String MYRCARD_ANDROID_EXP = "android_experience";
	public static final String MYRCARD_IOS_EXP = "ios_experience";
	public static final String MYRCARD_PORTFOLIO_ANDROID = "android_url";
	public static final String MYRCARD_PORTFOLIO_IOS = "ios_url";
	public static final String MYRCARD_PORTFOLIO_OTHER = "other_url";
	public static final String MYRCARD_LINKEDIN_URL = "linkedin_url";
	public static final String MYRCARD_RESUME_URL = "resume_url";
	public static final String MYRCARD_HIGHEST_DEGREE = "highest_degree";
	public static final String MYRCARD_OTHER_INFO = "other_info";


	//Company table name
	public static final String TABLE_COMPANY = "company";

	//company columns
	public static final String COMPANY_ID = "id";
	public static final String COMPANY_NAME = "name";
	public static final String COMPANY_CONTACT_NAME = "contact_name";
	public static final String COMPANY_EMAIL = "email";
	public static final String COMPANY_OTHER_INFO = "info";
	public static final String COMPANY_MYRCARD_SENT = "MYRCARD_sent";

	//MYRCARDLookup table for recruiters
	public static final String TABLE_RCARDSLOOKUP = "rcardslookup";

	//MYRCARDLookup columns
	public static final String RCARDSLOOKUP_ID = "id";
	public static final String RCARDSLOOKUP_NAME = "name";
	public static final String RCARDSLOOKUP_PRIORITY = "priority";
	public static final String RCARDSLOOKUP_EMAIL = "email";

	//Recruiter table name
	public static final String TABLE_RCARDS = "rcards";

	//MYRCARD columns
	public static final String RCARDS_NAME = "name";
	public static final String RCARDS_PHONE = "phone";
	public static final String RCARDS_EMAIL = "email";
	public static final String RCARDS_PRIMARY_SKILLS = "primary_skills";
	public static final String RCARDS_ANDROID_EXP = "android_experience";
	public static final String RCARDS_IOS_EXP = "ios_experience";
	public static final String RCARDS_PORTFOLIO_ANDROID = "android_url";
	public static final String RCARDS_PORTFOLIO_IOS = "ios_url";
	public static final String RCARDS_PORTFOLIO_OTHER = "other_url";
	public static final String RCARDS_LINKEDIN_URL = "linkedin_url";
	public static final String RCARDS_RESUME_URL = "resume_url";
	public static final String RCARDS_HIGHEST_DEGREE = "highest_degree";
	public static final String RCARDS_OTHER_INFO = "other_info";


	String CREATE_MYRCARD_TABLE = String.format("CREATE TABLE %s (%s TEXT NOT NULL, %s TEXT, %s TEXT PRIMARY KEY NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT)", TABLE_MYRCARD, MYRCARD_NAME , MYRCARD_PHONE, MYRCARD_EMAIL, MYRCARD_PRIMARY_SKILLS, MYRCARD_ANDROID_EXP, MYRCARD_IOS_EXP, MYRCARD_PORTFOLIO_ANDROID, MYRCARD_PORTFOLIO_IOS, MYRCARD_PORTFOLIO_OTHER, MYRCARD_LINKEDIN_URL, MYRCARD_RESUME_URL, MYRCARD_HIGHEST_DEGREE, MYRCARD_OTHER_INFO);
	String CREATE_RCARDS_TABLE = String.format("CREATE TABLE %s (%s TEXT NOT NULL, %s TEXT, %s TEXT PRIMARY KEY NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT)", TABLE_RCARDS, RCARDS_NAME , RCARDS_PHONE, RCARDS_EMAIL, RCARDS_PRIMARY_SKILLS, RCARDS_ANDROID_EXP, RCARDS_IOS_EXP, RCARDS_PORTFOLIO_ANDROID, RCARDS_PORTFOLIO_IOS, RCARDS_PORTFOLIO_OTHER, RCARDS_LINKEDIN_URL, RCARDS_RESUME_URL, RCARDS_HIGHEST_DEGREE, RCARDS_OTHER_INFO);


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
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYRCARD);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
		Cursor query = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_MYRCARD+"'", null);
		if(query.getCount() == 0)
		{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
			String CREATE_COMPANY_TABLE = String.format("CREATE TABLE  %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s BOOLEAN  DEFAULT 0)", TABLE_COMPANY, COMPANY_ID, COMPANY_NAME, COMPANY_CONTACT_NAME, COMPANY_EMAIL, COMPANY_OTHER_INFO, COMPANY_MYRCARD_SENT);
			db.execSQL(CREATE_MYRCARD_TABLE);
			db.execSQL(CREATE_COMPANY_TABLE);
		}
		query.close();
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, 1);	//update user as jobseeker
		db.update(TABLE_USER, cv, null, null);
	}

	public void CreateRecruiterTables(SQLiteDatabase db)
	{
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_RCARDS);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_RCARDSLOOKUP);
		Cursor query = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_RCARDSLOOKUP+"'", null);
		if(query.getCount() == 0)
		{
			db.execSQL(CREATE_RCARDS_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_RCARDSLOOKUP);
			//create MYRCARD lookup table
			//String CREATE_RCARDSLOOKUP_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s INTEGER DEFAULT 0, %s TEXT NOT NULL, FOREIGN KEY (%s) REFERENCES %s (%s))", TABLE_RCARDSLOOKUP, RCARDSLOOKUP_ID, RCARDSLOOKUP_NAME, RCARDSLOOKUP_PRIORITY, RCARDSLOOKUP_EMAIL, RCARDSLOOKUP_EMAIL, TABLE_RCARDS, RCARDS_EMAIL);
			String CREATE_RCARDSLOOKUP_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY NOT NULL, %s INTEGER DEFAULT 0, %s TEXT NOT NULL, FOREIGN KEY (%s) REFERENCES %s (%s))", TABLE_RCARDSLOOKUP, RCARDSLOOKUP_EMAIL, RCARDSLOOKUP_PRIORITY, RCARDSLOOKUP_NAME, RCARDSLOOKUP_EMAIL, TABLE_RCARDS, RCARDS_EMAIL);
			db.execSQL(CREATE_RCARDSLOOKUP_TABLE);
		}
		query.close();

		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, 2); //update user as recruiter
		db.update(TABLE_USER, cv, null, null);
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
}
