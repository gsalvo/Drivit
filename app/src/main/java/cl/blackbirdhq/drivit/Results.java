package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Results extends AppCompatActivity {
    private Bundle message;
    private int messageScore;
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
        viewScore = (TextView) findViewById(R.id.viewScore);
        text = (TextView) findViewById(R.id.text);
        face = (ImageView) findViewById(R.id.face);
        printResult(messageScore);
    }

    private void printResult(int score){
        String viewScoreAux = getResources().getString(R.string.text4) + " " +score+ " puntos en un tiempo de xx minutos." ;
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


}
