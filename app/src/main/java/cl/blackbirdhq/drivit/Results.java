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

import java.util.concurrent.TimeUnit;

public class Results extends AppCompatActivity {
    private Bundle message;
    private int  messageScore;
    private long messageTime;
    private TextView title, text, points, percent, correct, incorrect, blank, time, timeOff;
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
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        points= (TextView) findViewById(R.id.points);
        percent = (TextView) findViewById(R.id.percent);
        correct = (TextView) findViewById(R.id.correct);
        incorrect = (TextView) findViewById(R.id.incorrect);
        blank = (TextView) findViewById(R.id.blank);
        time = (TextView) findViewById(R.id.time);
        timeOff = (TextView) findViewById(R.id.timeOff);
        face = (ImageView) findViewById(R.id.face);
        printResult(messageScore);
    }

    private void printResult(int score){
        points.setText(getString(R.string.result3) + " 18");
        percent.setText(getString(R.string.result4) + " 47%");
        correct.setText(getString(R.string.result5) + " 15");
        incorrect.setText(getString(R.string.result6) + " 15%");
        blank.setText(getString(R.string.result7) + " 5%");
        time.setText(getString(R.string.result8) + " 25 minutos");
        timeOff.setText(getString(R.string.result9) + " 10 minutos");
            /*    String viewScoreAux = getResources().getString(R.string.text4) + " " +score+ " puntos en un tiempo de "+
                (TimeUnit.MILLISECONDS.toMinutes(messageTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTime))) +" minutos y "+
                (TimeUnit.MILLISECONDS.toSeconds(messageTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(messageTime)))+
                " segundos.";*/
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
