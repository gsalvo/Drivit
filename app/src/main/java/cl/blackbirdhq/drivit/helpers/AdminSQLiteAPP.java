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

    private static final String ALTERNATIVES_TESTS = "create table alternatives_tests (" +
            "_id integer primary key autoincrement, " +
            "questions_id integer not null, " +
            "tests_id integer, " +
            "alternatives_id integer not null, " +
            "categories_id integer not null,"+
            "right integer not null)";

    private static final String TESTS = "create table tests (" +
            "_id integer primary key autoincrement," +
            " time integer," +
            " date text not null," +
            " achieved integer," +
            " modality text not null," +
            " class text not null)";

    private  static final String QUESTIONS_TYPES = "create table questions_types(" +
            "_id integer primary key autoincrement," +
            "questions_id integer not null," +
            "class text not null)";
    private static final String OFFLINE_QUESTIONS = "create table offline_questions(" +
            "_id integer primary key autoincrement," +
            "question text not null," +
            "image text not null," +
            "categories_id integer not null)";
    private static final String OFFLINE_ALTERNATIVES = "create table offline_alternatives(" +
            "_id integer primary key autoincrement," +
            "alternative text not null," +
            "right integer not null," +
            "questions_id integer not null)";


    private static final String TEST = "create table test (" +
            "_id integer primary key autoincrement," +
            "questions_id integer not null, " +
            "alternatives_id integer not null," +
            "right integer not null," +
            "categories_id integer not null)";







    public AdminSQLiteAPP(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUESTIONS);
        db.execSQL(ALTERNATIVES);
        db.execSQL(CATEGORIES);
        db.execSQL(ALTERNATIVES_TESTS);
        db.execSQL(TESTS);
        db.execSQL(QUESTIONS_TYPES);
        db.execSQL(OFFLINE_ALTERNATIVES);
        db.execSQL(OFFLINE_QUESTIONS);
        db.execSQL(TEST);
        db.execSQL("INSERT INTO categories(_id, name, special) values (1,'conocimientos legales y reglamentarias', 0)");
        db.execSQL("INSERT INTO categories(_id, name, special) values (2,'conducta vial', 0)");
        db.execSQL("INSERT INTO categories(_id, name, special) values (3,'conocimientos mecánica básica', 0)");
        db.execSQL("INSERT INTO categories(_id, name, special) values (4,'seguridad vial', 1)");
    }

    public void resetData(SQLiteDatabase db){
        db.execSQL("DELETE FROM offline_questions");
        db.execSQL("DELETE FROM offline_alternatives");
        db.execSQL("DELETE FROM questions_types");
    }


    public void reloadDBTest(SQLiteDatabase db){
        db.execSQL("DELETE FROM questions");
        db.execSQL("DELETE FROM alternatives");
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
