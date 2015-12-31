package cl.blackbirdhq.drivit.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteAPP extends SQLiteOpenHelper {

    private static final String DB_NAME = "aplications.db";
    private static final int DB_VERSION = 2;

    private static final String QUESTIONS = "create table questions(" +
            "_id integer primary key autoincrement, " +
            "question text not null, " +
            "image text, " +
            "categories_id integer not null)";

    private static final String ALTERNATIVES = "create table alternatives (" +
            "_id integer primary key autoincrement," +
            " alternative text not null," +
            " right integer not null," +
            " questions_id integer not null)";

    private static final String CATEGORIES = "create table categories (" +
            "_id integer primary key autoincrement," +
            " name text not null," +
            "special integer not null)";

    private static final String QUESTIONS_TESTS = "create table questions_tests (" +
            "_id integer primary key autoincrement, " +
            "questions_id integer not null, " +
            "tests_id integer not null, " +
            "selection integer not null, " +
            "right integer not null)";

    private static final String TESTS = "create table tests (" +
            "_id integer primary key autoincrement," +
            " time integer," +
            " date text not null," +
            " achieved integer," +
            " modality text not null," +
            " type text not null)";

    private static final String DB_CREATE_TEST = "create table test (" +
            "_id integer primary key autoincrement," +
            "questions_id integer, " +
            "alternatives_id integer," +
            " right integer)";



    public AdminSQLiteAPP(Context context){
        super(context,  DB_NAME, null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUESTIONS);
        db.execSQL(ALTERNATIVES);
        db.execSQL(CATEGORIES);
        db.execSQL(QUESTIONS_TESTS);
        db.execSQL(TESTS);
        db.execSQL(DB_CREATE_TEST);

    }

    public void reloadDBTest(SQLiteDatabase db){
        db.execSQL("DELETE FROM questions");
        db.execSQL("DELETE FROM alternatives");
        db.execSQL("DELETE FROM categories");
        db.execSQL("DELETE FROM test");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 2){
            db.execSQL("DROP TABLE IF EXISTS questions");
            db.execSQL("DROP TABLE IF EXISTS alternatives");
            db.execSQL("DROP TABLE IF EXISTS test");
            db.execSQL("DROP TABLE IF EXISTS categories");
            onCreate(db);
        }

    }
}
