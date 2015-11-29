package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Question extends AppCompatActivity implements StructureQuestion.OnSelectedAlternativeListener {
    //Variables de la base de datos
    private SQLiteDatabase bd;
    private Cursor question;
    private Cursor test;
    private AdminSQLiteAPP admin = new AdminSQLiteAPP(this);

    //Variables de la transición de preguntas
    ImageButton btnPrev, btnNext;
    TextView numberQuestion;
    private int number = 1;
    private int alternativeSelected = 0;
    private static int FINAL_QUESTION;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Bundle message = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initializeComponents();
    }

    public void initializeComponents(){
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        numberQuestion = (TextView) findViewById(R.id.numberQuestion);
        bd = admin.getWritableDatabase();
        question = bd.rawQuery("Select * from questions", null);

        FINAL_QUESTION = question.getCount();
        question.moveToFirst();
        addQuestion();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number < FINAL_QUESTION) {
                    saveQuestion();
                    number++;
                    question.moveToNext();
                    addQuestion();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 1) {
                    saveQuestion();
                    number--;
                    question.moveToPrevious();
                    popQuestion();
                }
            }
        });
    }
    private void saveQuestion(){
        Cursor alternative;
        ContentValues register = new ContentValues();
        register.put("_id", number);
        register.put("questions_id", question.getInt(0));
        register.put("alternatives_id", alternativeSelected);
        if(alternativeSelected != 0){
            alternative = bd.rawQuery("select right from alternatives where _id = " + alternativeSelected, null);
            alternative.moveToFirst();
            register.put("right", alternative.getInt(0));
            if(bd.rawQuery("Select _id from test where _id = " + number,null).getCount() > 0){
                bd.update("test", register, "_id = " + number, null);
            }else{
                bd.insert("test", null, register);
            }
        }else{
            register.put("right", 0);
            if(bd.rawQuery("Select _id from test where _id = " + number,null).getCount() > 0){
                bd.update("test", register, "_id = " + number, null);
            }else{
                bd.insert("test", null, register);
            }
        }
    }

    private void addQuestion(){
        StructureQuestion sq = new StructureQuestion();
        message.putString("id_question",question.getString(0));
        message.putString("question", question.getString(1));
        message.putString("image", question.getString(2));
        sq.setArguments(message);
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFragment, sq);
        transaction.addToBackStack(null);
        transaction.commit();
        numberQuestion.setText(number + "");
        btnState();
    }
    private void popQuestion(){
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.commit();
        numberQuestion.setText(number + "");
        btnState();
    }

    private void btnState(){
        if(number == FINAL_QUESTION){
            btnNext.setEnabled(false);
        }
        else if(number == 1){
            btnPrev.setEnabled(false);
        }else{
            btnNext.setEnabled(true);
            btnPrev.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnNav:
                goNavTest();
                return true;
            case R.id.btnClose:
                goResult();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goNavTest(){
        saveQuestion();
        Intent i = new Intent(Question.this, NavQuestionContent.class);
        startActivity(i);
    }

    public void goResult(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setTitle(R.string.dialogTitleCloseTest)
                .setMessage(R.string.dialogCloseTest)
                .setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Question.this, Results.class);
                        i.putExtra("score", calcRegularResult());
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }
    public int calcRegularResult(){
        saveQuestion();
        Cursor specialScore;
        test = bd.rawQuery("Select * from test", null);
        int score = 0;
        int specialQuestion = 0;
        while(test.moveToNext()){
            specialScore = bd.rawQuery("SELECT special, name FROM categories AS c JOIN questions AS q ON q.categories_id = c._id WHERE q._id =" + test.getInt(1), null);
            specialScore.moveToFirst();
            if(specialQuestion < 3 && specialScore.getInt(0)==1){
                score += test.getInt(3) * 2;
                specialQuestion ++;
            }else{
                score += test.getInt(3);
            }
        }
        return score;
    }


    @Override
    public void selectedAlternative(int alternative) {
        alternativeSelected = alternative;
    }
}
