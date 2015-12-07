package cl.blackbirdhq.drivit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class NavQuestionContent extends AppCompatActivity implements NavQuestion.OnSelectedQuestionListener{
    private int selectedQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_question_content);
        initializeComponents();
    }

    private void initializeComponents() {
        FragmentManager manager;
        FragmentTransaction transaction;
        Bundle message = getIntent().getExtras();
        selectedQuestion = message.getInt("currentQuestion");
        NavQuestion navQuestion = new NavQuestion();
        navQuestion.setArguments(message);
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFragment, navQuestion);
        transaction.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void selectedQuestionListener(int question) {
        selectedQuestion = question;
        Intent intent = new Intent();
        intent.putExtra("selectedQuestion", selectedQuestion);
        setResult(RESULT_OK, intent);
    }
}
