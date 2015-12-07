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
    private TextView viewScore, text;
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
        viewScore = (TextView) findViewById(R.id.viewScore);
        text = (TextView) findViewById(R.id.text);
        face = (ImageView) findViewById(R.id.face);
        printResult(messageScore);
    }

    private void printResult(int score){
        String viewScoreAux = getResources().getString(R.string.text4) + " " +score+ " puntos en un tiempo de "+
                (TimeUnit.MILLISECONDS.toMinutes(messageTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(messageTime))) +" minutos y "+
                (TimeUnit.MILLISECONDS.toSeconds(messageTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(messageTime)))+
                " segundos.";
        if(score <7){
            face.setImageResource(R.drawable.face_sad);
            viewScore.setText(viewScoreAux);
            text.setText(getResources().getString(R.string.text5));

        }else{
            face.setImageResource(R.drawable.face_happy);
            viewScore.setText(viewScoreAux);
            text.setText(R.string.text6);
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
