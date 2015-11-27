package cl.blackbirdhq.drivit;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
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
        System.out.println("la alternativa seleccionada es: " + alternativeSelected);
        if(alternativeSelected != 0){
            System.out.println("estoy en el lado bueno");
            alternative = bd.rawQuery("select right from alternatives where _id = " + alternativeSelected, null);
            alternative.moveToFirst();
            System.out.println(alternative.getInt(0));
            register.put("right", 1);
            if(bd.rawQuery("Select _id from test where _id = " + number,null).getCount() > 0){
                bd.update("test", register, "_id = " + number, null);
                System.out.println(String.format("Se actualiza la id de la pregunta %d , la id de la alternativa %d y si es correcta: %d", question.getInt(0), alternativeSelected, alternative.getInt(0)));
            }else{
                bd.insert("test",null,register);
                System.out.println(String.format("Se guarda la id de la pregunta %d , la id de la alternativa %d y si es correcta: %d", question.getInt(0), alternativeSelected, alternative.getInt(0)));
            }
        }else{
            register.put("right", 0);
            if(bd.rawQuery("Select _id from test where _id = " + number,null).getCount() > 0){
                bd.update("test", register, "_id = " + number, null);
                System.out.println(String.format("Se actualiza la id de la pregunta %d , la id de la alternativa %d y si es correcta: %d", question.getInt(0), alternativeSelected, 0));
            }else{
                bd.insert("test",null,register);
                System.out.println(String.format("Se guarda la id de la pregunta %d , la id de la alternativa %d y si es correcta: %d", question.getInt(0), alternativeSelected, 0));
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedAlternative(int alternative) {
        alternativeSelected = alternative;
    }
}
