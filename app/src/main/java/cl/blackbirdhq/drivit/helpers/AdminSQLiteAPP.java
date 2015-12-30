package cl.blackbirdhq.drivit.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteAPP extends SQLiteOpenHelper {

    private static final String DB_NAME = "aplications.db";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE_QUESTION = "create table questions (_id integer primary key autoincrement," +
            "question text not null, image text, categories_id integer not null)";
    private static final String DB_CREATE_ALTERNATIVE = "create table alternatives (_id integer primary key autoincrement," +
            "alternative text not null, right integer, questions_id integer )";
    private static final String DB_CREATE_CATEGORY = "create table categories (_id integer primary key autoincrement," +
            "name text not null, special integer not null)";
    private static final String DB_CREATE_TEST = "create table test (_id integer primary key autoincrement," +
            "questions_id integer, alternatives_id integer, right integer)";



    public AdminSQLiteAPP(Context context){
        super(context,  DB_NAME, null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DB_CREATE_QUESTION);
        db.execSQL(DB_CREATE_ALTERNATIVE);
        db.execSQL(DB_CREATE_CATEGORY);
        db.execSQL(DB_CREATE_TEST);

    }

    public void reloadDBTest(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS alternatives");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        /*db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS alternatives");
        db.execSQL("DROP TABLE IF EXISTS tests");
        onCreate(db);*/
    }
}
