package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Results extends AppCompatActivity {
    private Bundle message;
    private int  messageScore;
    private int messageCorrect;
    private int messageIncorrect;
    private long messageTime;
    private long messageTotalTime;
    private static String TYPE;
    private static String MODALITY;
    private TextView title, text, points, percent, correct, incorrect, blank, time, timeOff, totalTime;
    private ImageView face;
    private AdminSQLiteAPP admin = new AdminSQLiteAPP(this);
    private SQLiteDatabase bd;
    private static long ID_TESTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initializeComponents();
    }

    private void initializeComponents(){
        bd = admin.getWritableDatabase();
        message = getIntent().getExtras();
        messageScore = message.getInt("score");
        messageTime = message.getLong("timeTest");
        messageTotalTime =message.getLong("totalTime");
        messageCorrect = message.getInt("correct");
        messageIncorrect = message.getInt("incorrect");
        MODALITY = message.getString("modality");
        TYPE = message.getString("type");
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        points= (TextView) findViewById(R.id.points);
        percent = (TextView) findViewById(R.id.percent);
        correct = (TextView) findViewById(R.id.correct);
        incorrect = (TextView) findViewById(R.id.incorrect);
        blank = (TextView) findViewById(R.id.blank);
        time = (TextView) findViewById(R.id.time);
        timeOff = (TextView) findViewById(R.id.timeOff);
        totalTime = (TextView) findViewById(R.id.totalTime);
        face = (ImageView) findViewById(R.id.face);

        printResult(messageScore);
    }

    private void printResult(int score){

        int achieved = 0;
        points.setText(getString(R.string.result3) + " " + score);
        if(MODALITY.equals("special")){
            percent.setText(getString(R.string.result4) + " " + (score * 100 / 10)+"%");
            blank.setText(getString(R.string.result7) + " " + (35 - messageIncorrect - messageCorrect));
            if((score * 100 / 10) >= 87) {
                achieved = 1;
                face.setImageResource(R.drawable.face_happy);
                title.setText(getString(R.string.resultCat1));
                title.setTextColor(getResources().getColor(R.color.GreenText));
                text.setText(getResources().getString(R.string.resultCat4));
            }else{
                title.setText(getString(R.string.resultCat2));
                text.setText(getResources().getString(R.string.resultCat3));
            }
        }else{
            percent.setText(getString(R.string.result4) + " " + (score * 100 / 38)+"%");
            blank.setText(getString(R.string.result7) + " " + (35 - messageIncorrect - messageCorrect));
            if(score >33) {
                achieved = 1;
                face.setImageResource(R.drawable.face_happy);
                title.setText(getString(R.string.result1));
                title.setTextColor(getResources().getColor(R.color.GreenText));
                text.setText(getResources().getString(R.string.result11));
            }
        }
        ContentValues register = new ContentValues();
        register.put("time", messageTime);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        register.put("date", format.format(new Date()));
        register.put("modality",MODALITY);
        register.put("class", TYPE.toUpperCase());
        register.put("achieved", achieved);
        try {
            ID_TESTS = bd.insert("tests", null, register);
            Cursor auxTest = bd.rawQuery("SELECT * FROM test", null);
            if (MODALITY.equals("survival")){
                while (auxTest.moveToNext()) {
                    if(auxTest.getInt(3) == 1){
                        bd.execSQL("INSERT INTO alternatives_tests " +
                                "(right, tests_id, alternatives_id, questions_id, categories_id) VALUES " +
                                "(" + auxTest.getInt(3) + "," + ID_TESTS + "," + auxTest.getInt(2) + "," + auxTest.getInt(1) + "," + auxTest.getInt(4) + ")");
                    }
                }
            }else{
                while (auxTest.moveToNext()) {
                    bd.execSQL("INSERT INTO alternatives_tests " +
                            "(right, tests_id, alternatives_id, questions_id, categories_id) VALUES " +
                            "(" + auxTest.getInt(3) + "," + ID_TESTS + "," + auxTest.getInt(2) + "," + auxTest.getInt(1) + "," + auxTest.getInt(4) + ")");
                }
            }
        }catch (Exception e){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getString(R.string.msjeTitle11))
                    .setMessage(getString(R.string.msjeText11))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Cierra el dialogo
                        }
                    })
                    .show();
        }finally {
            bd.close();
        }



        correct.setText(getString(R.string.result5) + " " + messageCorrect);
        incorrect.setText(getString(R.string.result6) + " " + messageIncorrect);
        if(MODALITY.equals("survival")){
            time.setVisibility(View.GONE);
            timeOff.setVisibility(View.GONE);
            totalTime.setVisibility(View.GONE);
            blank.setText(getString(R.string.result15) + " " + (35 - messageIncorrect - messageCorrect));
            Button btnReview = (Button) findViewById(R.id.btnReview);
            btnReview.setVisibility(View.GONE);
        }else{
            long totalMinutes = (TimeUnit.MILLISECONDS.toMinutes(messageTotalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTotalTime)));
            long minutes = (TimeUnit.MILLISECONDS.toMinutes(messageTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTime)));
            long seconds = (TimeUnit.MILLISECONDS.toSeconds(messageTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(messageTime)));
            double secondsToMinutes = seconds /60.0 ;
            DecimalFormat df = new DecimalFormat("#0.#");
            time.setText(getString(R.string.result8) +" "+ df.format(secondsToMinutes + minutes)+ " minutos");
            timeOff.setText(getString(R.string.result9) + " " + df.format((totalMinutes) - (secondsToMinutes + minutes))+ " minutos");
            totalTime.setText(getString(R.string.result14) + " " + ((int) totalMinutes) + " minutos");
        }
    }

    public void goCheckTest (View view){
        Intent i = new Intent(Results.this, Question.class);
        i.putExtra("checkTest", true);
        startActivity(i);
    }

    public void goReDoTest(View view){
        finish();
    }


}
