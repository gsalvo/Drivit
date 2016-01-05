package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
    private ProgressDialog mDialog;
    private LoadQuestion loadQuestion;
    private AlertDialog.Builder alertDialog;

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

    class LoadQuestion extends AsyncTask<String, String, String> {
        Cursor cursor [] = new Cursor[12];
        @Override
        protected void onPreExecute (){
            mDialog.setMessage(getString(R.string.msjeText13));
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            String result = "stop";
            try {
                bd = admin.getReadableDatabase();

                result = "go";
            }catch (Exception e){
                System.out.println(e);
            }finally {
                bd.close();
                return result;
            }

        }
        @Override
        protected void onPostExecute(String result){
            if(result.equals("go")){
                mDialog.dismiss();
                text1_1 = (TextView) findViewById(R.id.text1_1);
                text1_1.setText(getString(R.string.progress2) + " " + (cursor[0].getInt(0) + cursor[1].getInt(0)));

                text1_2 = (TextView) findViewById(R.id.text1_2);
                text1_2.setText(getString(R.string.progress3) + " " + cursor[0].getInt(0));

                text1_3 = (TextView) findViewById(R.id.text1_3);
                text1_3.setText(getString(R.string.progress4) + " " + cursor[1].getInt(0));

                text1_4 = (TextView) findViewById(R.id.text1_4);
                int totalAux = 0;
                if(cursor[0].getInt(0) != 0){
                    totalAux = cursor[0].getInt(0) * 100 / (cursor[0].getInt(0) + cursor[1].getInt(0));
                }
                text1_4.setText(totalAux + "%");
                if(totalAux <= 50){
                    text1_4.setTextColor(getResources().getColor(R.color.errorAnswer));
                }
                /*tiempo*/
                if(cursor[3].getCount() > 0) {
                    if(cursor[3].getInt(0)!=0) {
                        text2_1 = (TextView) findViewById(R.id.text2_1);
                        long minutes = (TimeUnit.MILLISECONDS.toMinutes(cursor[3].getInt(0)) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cursor[3].getInt(0))));
                        long seconds = (TimeUnit.MILLISECONDS.toSeconds(cursor[3].getInt(0)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cursor[3].getInt(0))));
                        double secondsToMinutes = seconds / 60.0;
                        DecimalFormat df = new DecimalFormat("#0.#");
                        text2_1.setText(getString(R.string.progress7) + " " + df.format(secondsToMinutes + minutes) + " " + getString(R.string.progress9));

                        text2_2 = (TextView) findViewById(R.id.text2_2);
                        minutes = (TimeUnit.MILLISECONDS.toMinutes(cursor[2].getInt(0)) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cursor[2].getInt(0))));
                        seconds = (TimeUnit.MILLISECONDS.toSeconds(cursor[2].getInt(0)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cursor[2].getInt(0))));
                        secondsToMinutes = seconds / 60.0;
                        text2_2.setText(getString(R.string.progress8) + " " + df.format(secondsToMinutes + minutes) + " " + getString(R.string.progress9));
                    }
                }

                /*Conocimientos Legales y reglamentarios*/
                text3_1 = (TextView) findViewById(R.id.text3_1);
                text3_2 = (TextView) findViewById(R.id.text3_2);
                text3_3 = (TextView) findViewById(R.id.text3_3);
                calcPercent(cursor[4], cursor[5], text3_1, text3_2, text3_3);
                /*Conducta vial */
                text4_1 = (TextView) findViewById(R.id.text4_1);
                text4_2 = (TextView) findViewById(R.id.text4_2);
                text4_3 = (TextView) findViewById(R.id.text4_3);
                calcPercent(cursor[6],cursor[7], text4_1, text4_2, text4_3);
                /* Conocimientos mecánica básica*/
                text5_1 = (TextView) findViewById(R.id.text5_1);
                text5_2 = (TextView) findViewById(R.id.text5_2);
                text5_3 = (TextView) findViewById(R.id.text5_3);
                calcPercent(cursor[8], cursor[9], text5_1, text5_2, text5_3);
                /*Seguridad Vial*/
                text6_1 = (TextView) findViewById(R.id.text6_1);
                text6_2 = (TextView) findViewById(R.id.text6_2);
                text6_3 = (TextView) findViewById(R.id.text6_3);
                calcPercent(cursor[10], cursor[11], text6_1, text6_2, text6_3);
            }else{
                mDialog.dismiss();
                alertDialog.setTitle(getString(R.string.msjeTitle12))
                        .setMessage(getString(R.string.msjeText12))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Progress.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}
