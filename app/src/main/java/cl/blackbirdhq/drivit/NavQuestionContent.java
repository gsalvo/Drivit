package cl.blackbirdhq.drivit;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_question_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnClose:
                goResult();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goResult(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setTitle(R.string.dialogTitleCloseTest)
                .setMessage(R.string.dialogCloseTest)
                .setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(NavQuestionContent.this , Results.class);
                        i.putExtra("score", calcRegularResult());
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    public int calcRegularResult(){
        SQLiteDatabase bd;
        AdminSQLiteAPP admin;
        admin = new AdminSQLiteAPP(this);
        bd = admin.getReadableDatabase();
        Cursor specialScore = null ;
        Cursor test = bd.rawQuery("Select * from test", null);
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
            specialScore.close();
        }
        test.close();
        bd.close();
        return score;
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
