package cl.blackbirdhq.drivit;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;


public class NavQuestion extends Fragment {
    private LinearLayout contentButtonNav;
    private int messageCurrentQuestion;
    private boolean messageCheckTest = false;
    OnSelectedQuestionListener onSelectedQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageCurrentQuestion = getArguments().getInt("currentQuestion");
        messageCheckTest = getArguments().getBoolean("checkTest", false);
        onSelectedQuestion.selectedQuestionListener(messageCurrentQuestion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_question, container, false);
        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        if(messageCheckTest){
            ImageView image1 = (ImageView) view.findViewById(R.id.icAnsweredRight);
            image1.setBackgroundResource(R.color.defaultButton);
            TextView text1 = (TextView) view.findViewById(R.id.textAnsweredRight);
            text1.setText(R.string.nav_answered_right);
            text1.setTextColor(getResources().getColor(R.color.defaultButton));
            ImageView image2 = (ImageView) view.findViewById(R.id.icNoAnsweredWrong);
            image2.setBackgroundResource(R.color.errorAnswer);
            TextView text2 = (TextView) view.findViewById(R.id.textNoAnsweredWrong);
            text2.setText(R.string.nav_answered_wrong);
            text2.setTextColor(getResources().getColor(R.color.errorAnswer));
        }
        contentButtonNav = (LinearLayout) view.findViewById(R.id.contentButtonNav);
        AdminSQLiteAPP admin = new AdminSQLiteAPP(this.getActivity());
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor questions = bd.rawQuery("select _id from questions order by _id", null);
        questions.moveToFirst();
        int cantQuestion = questions.getCount();
        int cantButtonLY = 5;
        double cantRow =Math.ceil((float)cantQuestion/(float)cantButtonLY);
        int numberQuestion = 1;
        for(int i = 0; i <cantRow; i++){
            LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.content_button_nav,contentButtonNav,false);
            contentButtonNav.addView(linearLayout);
            for(int y = 0; y < cantButtonLY; y++) {
                if(numberQuestion <= cantQuestion){
                    Cursor test = bd.rawQuery("select alternatives_id, right from test where questions_id="+questions.getInt(0),null);
                    test.moveToFirst();
                    final Button btnQuestion;
                    if(messageCheckTest){
                        if (test.getCount() == 0){
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_wrong_actual, linearLayout, false);
                            }else {
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_wrong, linearLayout, false);
                            }
                        }
                        else if(test.getInt(1) == 0){
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_wrong_actual, linearLayout, false);
                            }else {
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_wrong, linearLayout, false);
                            }
                        }else{
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_right_actual, linearLayout, false);
                            }else {
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_right, linearLayout, false);
                            }
                        }
                    }else{
                        if (test.getCount() == 0){
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered_actual, linearLayout, false);
                            }else {
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered, linearLayout, false);
                            }
                        }else if (test.getInt(0) == 0) {
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered_actual, linearLayout, false);
                            }else {
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered, linearLayout, false);
                            }
                        }else{
                            if(messageCurrentQuestion == numberQuestion){
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_answered_actual,linearLayout,false);
                            }else{
                                btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_answered,linearLayout,false);
                            }
                        }
                    }
                    btnQuestion.setText(numberQuestion + "");
                    linearLayout.addView(btnQuestion);
                    final int finalNumberQuestion = numberQuestion;
                    btnQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectedQuestion.selectedQuestionListener(finalNumberQuestion);
                            getActivity().finish();
                        }
                    });
                    questions.moveToNext();
                    numberQuestion++;
                    test.close();
                }else{
                    break;
                }
            }
        }
        questions.close();
        bd.close();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        onSelectedQuestion =(OnSelectedQuestionListener) activity;
    }

    public interface OnSelectedQuestionListener{
        public void selectedQuestionListener(int question);
    }
}
