package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Results extends AppCompatActivity {
    private Bundle message;
    private int  messageScore;
    private int messageCorrect;
    private int messageIncorrect;
    private long messageTime;
    private long messageTotalTime;
    private TextView title, text, points, percent, correct, incorrect, blank, time, timeOff, totalTime;
    private ImageView face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initializeComponents();
    }

    private void initializeComponents(){
        message = getIntent().getExtras();
        messageScore = message.getInt("score");
        messageTime = message.getLong("timeTest");
        messageTotalTime =message.getLong("totalTime");
        messageCorrect = message.getInt("correct");
        messageIncorrect = message.getInt("incorrect");

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
        points.setText(getString(R.string.result3) + " "+ score);
        percent.setText(getString(R.string.result4) + " " + (score * 100 / 38)+"%");
        correct.setText(getString(R.string.result5) + " " + messageCorrect);
        incorrect.setText(getString(R.string.result6) + " " + messageIncorrect);
        blank.setText(getString(R.string.result7) + " " + (35 - messageIncorrect - messageCorrect));
        long totalMinutes = (TimeUnit.MILLISECONDS.toMinutes(messageTotalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTotalTime)));
        long minutes = (TimeUnit.MILLISECONDS.toMinutes(messageTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTime)));
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(messageTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(messageTime)));
        System.out.println("segundos:" + seconds);
        double secondsToMinutes = seconds /60.0 ;
        System.out.println("segundos minutes:" + secondsToMinutes);
        DecimalFormat df = new DecimalFormat("#0.#");

        time.setText(getString(R.string.result8) +" "+ df.format(secondsToMinutes + minutes)+ " minutos");
        timeOff.setText(getString(R.string.result9) + " " + df.format((totalMinutes) - (secondsToMinutes + minutes))+ " minutos");
        totalTime.setText(getString(R.string.result14) + " " + ((int) totalMinutes) + " minutos");


        if(score >33) {
            face.setImageResource(R.drawable.face_happy);
            title.setText(getString(R.string.result1));
            title.setTextColor(getResources().getColor(R.color.GreenText));
            text.setText(getResources().getString(R.string.result11));
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
