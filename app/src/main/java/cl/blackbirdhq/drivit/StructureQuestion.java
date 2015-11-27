package cl.blackbirdhq.drivit;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;


public class StructureQuestion extends Fragment {
    private SQLiteDatabase bd;
    private Cursor questions;
    private Cursor alternative;
    private AdminSQLiteAPP admin;
    private  ImageView imageQuestion;
    private  TextView question;
    private  RadioGroup groupAlternatives;
    private  RadioButton alternative1, alternative2, alternative3;

    public StructureQuestion() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_structure_question, container, false);

        initializeComponents(view);
        return view;
    }


    public void initializeComponents(View view){
        admin = new AdminSQLiteAPP(this.getActivity());
        bd = admin.getWritableDatabase();
        imageQuestion = (ImageView) view.findViewById(R.id.imageQuestion);
        question = (TextView) view.findViewById(R.id.question);
        groupAlternatives = (RadioGroup) view.findViewById(R.id.groupAlternatives);
        alternative1 = (RadioButton) view.findViewById(R.id.alternative1);
        alternative2 = (RadioButton) view.findViewById(R.id.alternative2);
        alternative3 = (RadioButton) view.findViewById(R.id.alternative3);

        question.setText(getArguments().getString("question"));
        if(!getArguments().getString("image").isEmpty()){
            //code  for image
        }
        questions = bd.rawQuery("Select * from alternatives where questions_id = " + getArguments().getString("id_question"), null);
        questions.moveToFirst();
        alternative1.setText(questions.getString(1)); questions.moveToNext();
        alternative2.setText(questions.getString(1)); questions.moveToNext();
        alternative3.setText(questions.getString(1)); questions.moveToNext();
    }




}
