package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Question extends AppCompatActivity implements StructureQuestion.OnSelectedAlternativeListener, StructureQuestion.OnChangeQuestionListener {
    //Variables de punteo
    private float x1, x2;
    static final int MIN_DISTANCE = 200;

    //Variables de la base de datos
    private SQLiteDatabase bd;
    private Cursor question;
    private AdminSQLiteAPP admin = new AdminSQLiteAPP(this);
    private boolean checkTest = false;

    //Variables de la transición de preguntas
    ImageButton btnPrev, btnNext;
    TextView time;
    private CountDownTimer timer;
    private long timeTest;
    private static long TOTAL_TIME = 2700000;
    private int number = 0;
    private int goToPosition = 1;
    private boolean flagForward = false;
    private int alternativeSelected = 0;
    private static int FINAL_QUESTION;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Bundle message = new Bundle();
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initializeComponents();

    }

    public void initializeComponents() {

        checkTest = getIntent().getBooleanExtra("checkTest", false);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        time = (TextView) findViewById(R.id.time);
        bd = admin.getWritableDatabase();
        question = bd.rawQuery("Select * from questions order by _id", null);
        FINAL_QUESTION = question.getCount();
        addQuestion();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number < FINAL_QUESTION) {
                    if (!checkTest) {
                        saveQuestion();
                    }
                    goToPosition = number + 1;
                    flagForward = true;
                    goToQuestion(goToPosition - number);
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 1) {
                    if (!checkTest) {
                        saveQuestion();
                    }
                    goToPosition = number - 1;
                    popQuestion();
                }
            }
        });
        if (!checkTest) {

            timer = new CountDownTimer(TOTAL_TIME, 1000) {
                public void onTick(long millisUntilFinished) {
                    timeTest = millisUntilFinished;
                    time.setText("" + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish() {
                    saveQuestion();
                    Intent i = new Intent(Question.this, Results.class);
                    i.putExtra("score", calcRegularResult());
                    i.putExtra("timeTest", TOTAL_TIME - timeTest);
                    question.close();
                    bd.close();
                    startActivity(i);
                    finish();
                }
            }.start();
        }else{
            time.setVisibility(View.GONE);
        }
    }

    private void saveQuestion() {
        Cursor alternative;
        ContentValues register = new ContentValues();
        register.put("_id", number);
        register.put("questions_id", question.getInt(0));
        register.put("alternatives_id", alternativeSelected);
        System.out.println(alternativeSelected);
        if (alternativeSelected != 0) {
            alternative = bd.rawQuery("select right from alternatives where _id = " + alternativeSelected, null);
            alternative.moveToFirst();
            register.put("right", alternative.getInt(0));
            if (bd.rawQuery("Select _id from test where _id = " + number, null).getCount() > 0) {
                bd.update("test", register, "_id = " + number, null);
            } else {
                bd.insert("test", null, register);
            }
            alternative.close();
        } else {
            register.put("right", 0);
            if (bd.rawQuery("Select _id from test where _id = " + number, null).getCount() > 0) {
                bd.update("test", register, "_id = " + number, null);
            } else {
                bd.insert("test", null, register);
            }
        }

    }

    private void addQuestion() {
        question.moveToNext();
        number++;
        StructureQuestion sq = new StructureQuestion();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        message.putInt("numberQuestion", number);
        message.putString("id_question", question.getString(0));
        message.putString("question", question.getString(1));
        message.putString("image", question.getString(2));
        message.putBoolean("checkTest", checkTest);
        message.putInt("position", number);
        message.putInt("goToPosition", goToPosition);
        sq.setArguments(message);

        transaction.replace(R.id.contentFragment, sq);
        transaction.addToBackStack(null);
        transaction.commit();
        btnState();
    }

    private void popQuestion() {
        question.moveToPrevious();
        number--;
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.commit();
        btnState();
    }

    private void btnState() {
        if (number == FINAL_QUESTION) {
            btnNext.setEnabled(false);
            btnNext.setImageResource(R.drawable.ic_navigate_next_white_36dp_dis);
        } else if (number == 1) {
            btnPrev.setEnabled(false);
            btnPrev.setImageResource(R.drawable.ic_navigate_before_white_36dp_dis);
        } else {
            btnNext.setEnabled(true);
            btnNext.setImageResource(R.drawable.ic_navigate_next_white_36dp);
            btnPrev.setEnabled(true);
            btnPrev.setImageResource(R.drawable.ic_navigate_before_white_36dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);
        if(checkTest){
            MenuItem item = menu.findItem(R.id.btnClose);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnNav:
                saveQuestion();
                goNavTest();
                return true;
            case R.id.btnClose:
                saveQuestion();
                goResult();
                return true;
            case android.R.id.home:
                if(!checkTest){
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.dialogTitleExitTest)
                            .setMessage(R.string.dialogExitTest)
                            .setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    question.close();
                                    bd.close();
                                    timer.cancel();
                                    finish();

                                }
                            })
                            .setNegativeButton("NO", null)
                            .show();
                }else{
                    question.close();
                    bd.close();
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goNavTest() {
        Intent i = new Intent(Question.this, NavQuestionContent.class);
        i.putExtra("currentQuestion", number);
        i.putExtra("checkTest", checkTest);
        startActivityForResult(i, 1);
    }

    public void goResult() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialogTitleCloseTest)
                .setMessage(R.string.dialogCloseTest)
                .setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Question.this, Results.class);
                        i.putExtra("score", calcRegularResult());
                        i.putExtra("timeTest", TOTAL_TIME - timeTest);
                        question.close();
                        bd.close();
                        startActivity(i);
                        timer.cancel();
                        finish();

                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    public int calcRegularResult() {
        Cursor specialScore;
        Cursor test = bd.rawQuery("Select * from test", null);
        int score = 0;
        int specialQuestion = 0;
        while (test.moveToNext()) {
            specialScore = bd.rawQuery("SELECT special, name FROM categories AS c JOIN questions AS q ON q.categories_id = c._id WHERE q._id =" + test.getInt(1), null);
            specialScore.moveToFirst();
            if (specialQuestion < 3 && specialScore.getInt(0) == 1) {
                score += test.getInt(3) * 2;
                specialQuestion++;
            } else {
                score += test.getInt(3);
            }
            specialScore.close();
        }
        test.close();
        return score;
    }

    @Override
    public void onBackPressed() {
        if (!checkTest){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialogTitleExitTest)
                    .setMessage(R.string.dialogExitTest)
                    .setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            question.close();
                            bd.close();
                            timer.cancel();
                            finish();

                        }
                    })
                    .setNegativeButton("NO", null)
                    .show();
        }else{
            question.close();
            bd.close();
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                goToPosition = data.getIntExtra("selectedQuestion", number);
                if (goToPosition > number) {
                    flagForward = true;
                    goToQuestion(goToPosition - number);
                } else if (goToPosition < number) {
                    while (goToPosition < number) {
                        popQuestion();
                    }
                }
            }
        }
    }

    @Override
    public void selectedAlternative(int alternative) {
        alternativeSelected = alternative;
    }

    @Override
    public void goToQuestion(int position) {
        if (flagForward == true && position > 0) {
            addQuestion();
        } else {
            flagForward = false;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float auxX = x2 - x1;
                if(auxX > MIN_DISTANCE ){
                    //left
                    if(number > 1){
                        saveQuestion();
                        popQuestion();
                        return true;
                    }
                }else if(auxX < -MIN_DISTANCE ){
                    //Right
                    if(number < FINAL_QUESTION){
                        saveQuestion();
                        addQuestion();
                        return true;
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }



}
