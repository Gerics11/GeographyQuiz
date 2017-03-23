package com.example.android.geographyquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String QUESTIONNUMBER = "CURRENTQUESTION";
    private static final String NAME = "USERNAME";
    private int currentQuestion = 0;
    private String userName;
    private ArrayList<String> userInput = new ArrayList<>(20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 20; i++){  //generate empty list to .set to work
            userInput.add(i, "no data");
        }
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt(QUESTIONNUMBER);
            userInput=savedInstanceState.getStringArrayList("savedList");
            userName=savedInstanceState.getString("USERNAME");
        }
        if (currentQuestion == 0){
            setContentView(R.layout.activity_main);
        } else if (currentQuestion > 10) {
            setContentView(R.layout.activity_results);
        } else {
            display(currentQuestion);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("savedList", userInput);
        outState.putString(NAME, userName);
        outState.putInt(QUESTIONNUMBER, currentQuestion);
    }

    /**
     * Displays the proper layout according to question type, sets string question and answers
     * @param questionNumber int value, number of the question we want to display
     */
    private void display (int questionNumber) {
        if (questionNumber == 1 ||questionNumber == 4 || questionNumber == 5 || questionNumber == 8) {
            setContentView(R.layout.activity_radiobutton);
            RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);
            RadioButton radio2 = (RadioButton)findViewById(R.id.radio2);
            RadioButton radio3 = (RadioButton)findViewById(R.id.radio3);
            RadioButton radio4 = (RadioButton)findViewById(R.id.radio4);
            TextView questionText1 = (TextView)findViewById(R.id.questionText);
            if (questionNumber == 1){
                questionText1.setText(R.string.q1);
                radio1.setText(R.string.q1a1);
                radio2.setText(R.string.q1a2);
                radio3.setText(R.string.q1a3);
                radio4.setText(R.string.q1a4);
            } else if (questionNumber == 4) {
                questionText1.setText(R.string.q4);
                radio1.setText(R.string.q4a1);
                radio2.setText(R.string.q4a2);
                radio3.setText(R.string.q4a3);
                radio4.setText(R.string.q4a4);
            } else if (questionNumber == 5) {
                questionText1.setText(R.string.q5);
                radio1.setText(R.string.q5a1);
                radio2.setText(R.string.q5a2);
                radio3.setText(R.string.q5a3);
                radio4.setText(R.string.q5a4);
            } else  {
                questionText1.setText(R.string.q8);
                radio1.setText(R.string.q8a1);
                radio2.setText(R.string.q8a2);
                radio3.setText(R.string.q8a3);
                radio4.setText(R.string.q8a4);
            }
        } else if (questionNumber == 2 || questionNumber == 6 || questionNumber == 9) {
            setContentView(R.layout.activity_edittext);
            TextView questionText1 = (TextView)findViewById(R.id.questionText);
            if (questionNumber == 2) {
                questionText1.setText(R.string.q2);
            } else if (questionNumber == 6) {
                questionText1.setText(R.string.q6);
            } else  {
                questionText1.setText(R.string.q9);
            }

        } else if (questionNumber == 3 || questionNumber == 7 || questionNumber == 10){
            setContentView(R.layout.activity_checkbox);
            CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
            CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
            CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
            CheckBox checkBox4 = (CheckBox)findViewById(R.id.checkbox4);
            TextView questionText1 = (TextView)findViewById(R.id.questionText);
            if ( questionNumber == 3) {
                questionText1.setText(R.string.q3);
                checkBox1.setText(R.string.q3a1);
                checkBox2.setText(R.string.q3a2);
                checkBox3.setText(R.string.q3a3);
                checkBox4.setText(R.string.q3a4);
            } else if ( questionNumber == 7) {
                questionText1.setText(R.string.q7);
                checkBox1.setText(R.string.q7a1);
                checkBox2.setText(R.string.q7a2);
                checkBox3.setText(R.string.q7a3);
                checkBox4.setText(R.string.q7a4);
            } else {
                questionText1.setText(R.string.q10);
                checkBox1.setText(R.string.q10a1);
                checkBox2.setText(R.string.q10a2);
                checkBox3.setText(R.string.q10a3);
                checkBox4.setText(R.string.q10a4);
            }
        }
    }

    /**
     * Method called by Start Quiz button, reads username into variable, displays first question
     */
    public void startQuiz (View view) {
        currentQuestion = currentQuestion + 1;
        EditText et = (EditText)findViewById(R.id.nameEditText);
        userName = et.getText().toString();
        if (userName.equals("")) {
            Toast.makeText(this, getString(R.string.needname), Toast.LENGTH_LONG).show();
        } else {
            display(1);
        }
    }

    /**
     * Calls for method to read the given answers and displays the next question after that
     */
    public void next (View view) {
        if (currentQuestion == 3 || currentQuestion == 7 || currentQuestion == 10) {
            checkBox();
        } else if ( currentQuestion == 2 || currentQuestion == 6 || currentQuestion == 9) {
            editText();
        }
        currentQuestion = currentQuestion + 1;
        if (currentQuestion > 10) {
            setContentView(R.layout.activity_results);
        }
        display(currentQuestion);
    }

    /**
     * Accessible by user at the end of the quiz, resets userInput list
     */
    public void tryAgain (View view) {
        currentQuestion = 0;
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 20; i++){
            userInput.set(i, "no data");
        }
    }

    /**
     * Method to read answer from radiobutton activity, saving into userInput list
     * @param view method called by checking any radio button
     */
    public void radioButton (View view) {
        RadioGroup rg = (RadioGroup)findViewById(R.id.radiogroup);
        int checkedButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(checkedButtonID);
        int index = rg.indexOfChild(radioButton);
        if (currentQuestion == 1) {
            userInput.set(1, Integer.toString(index));
        } else if (currentQuestion == 4) {
            userInput.set(7, Integer.toString(index));
        } else if (currentQuestion == 5) {
            userInput.set(8, Integer.toString(index));
        } else if (currentQuestion == 8) {
            userInput.set(14, Integer.toString(index));
        }
    }

    /**
     * Passes true/false string value to the userInput list based on checkbox state
     * Method called every time next button is pressed in checkbox activity
     */
    private void checkBox () {
        CheckBox answer1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox answer2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox answer3 = (CheckBox) findViewById(R.id.checkbox3);
        CheckBox answer4 = (CheckBox) findViewById(R.id.checkbox4);
        if (currentQuestion == 3) {
            if (answer1.isChecked()) {userInput.set(3, "True");
            } else {userInput.set(3, "False");}
            if (answer2.isChecked()) {userInput.set(4, "True");
            } else {userInput.set(4, "False");}
            if (answer3.isChecked()) {userInput.set(5, "True");
            } else {userInput.set(5, "False");}
            if (answer4.isChecked()) {userInput.set(6, "True");
            } else {userInput.set(6, "False");}
        } else if (currentQuestion == 7) {
            if (answer1.isChecked()) {userInput.set(10, "True");
            } else {userInput.set(10, "False");}
            if (answer2.isChecked()) {userInput.set(11, "True");
            } else {userInput.set(11, "False");}
            if (answer3.isChecked()) {userInput.set(12, "True");
            } else {userInput.set(12, "False");}
            if (answer4.isChecked()) {userInput.set(13, "True");
            } else {userInput.set(13, "False");}
        } else {
            if (answer1.isChecked()) {userInput.set(16, "True");
            } else {userInput.set(16, "False");}
            if (answer2.isChecked()) {userInput.set(17, "True");
            } else {userInput.set(17, "False");}
            if (answer3.isChecked()) {userInput.set(18, "True");
            } else {userInput.set(18, "False");}
            if (answer4.isChecked()) {userInput.set(19, "True");
            } else {userInput.set(19, "False");}
        }
    }

    /**
     * Passes the string value taken from EditText field to the userInput list
     * Method called every time next button is pressed in editText activity
     */
    private void editText () {
        EditText et = (EditText) findViewById(R.id.answerEditText);
        String input = et.getText().toString();
        if (currentQuestion == 2) {
            userInput.set(2, input);
        } else if (currentQuestion == 6) {
            userInput.set(9, input);
        } else if (currentQuestion == 9) {
            userInput.set(15, input);
        }
    }

    /**
     * Gives evaluation of the answers entered by user, and sets summary textview to open link
     * Toast is shown according to users score
     */
    public void submit (View view) {
        Button tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
        Button showAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        Button submitButton = (Button)findViewById(R.id.submitButton) ;
        TextView summary = (TextView)findViewById(R.id.summaryTextView);
        tryAgainButton.setVisibility(View.VISIBLE);
        showAnswerButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);
        summary.setEnabled(true);
        int score = evaluateAnswers();
        String str1 = Integer.toString(score);

        summary.setText(userName + getString(R.string.summary1) + " " + str1 + getString(R.string.summary2)
                + getString(R.string.summary3) + getString(R.string.summary4));
        if (score == 10) {
            Toast.makeText(this, getString(R.string.toast1), Toast.LENGTH_LONG ).show();
        } else if (score > 6) {
            Toast.makeText(this, (getString(R.string.toast21) + " " + str1 + getString(R.string.toast22)), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, (getString(R.string.toast31) + " " + str1 + getString(R.string.toast32)), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Reads the userinput list and compares its elements to calculate the final score
     * @return Returns accumulated score
     */
    private int evaluateAnswers() {
        int score = 0;
        if ( userInput.get(1).equals("1")){ score++; }
        if ( userInput.get(2).toLowerCase().equals(getString(R.string.q2answer).toLowerCase())) {score++;}
        if (    userInput.get(3).equals("True")
                && userInput.get(4).equals("True")
                && userInput.get(5).equals("False")
                && userInput.get(6).equals("True")) { score++; }
        if ( userInput.get(7).equals("2")) { score++; }
        if ( userInput.get(8).equals("3")) { score++; }
        if ( userInput.get(9).equals("50")
                || userInput.get(9).toLowerCase().equals(getString(R.string.q6answer).toLowerCase())) { score++; }
        if (    userInput.get(10).equals("True")
                && userInput.get(11).equals("False")
                && userInput.get(12).equals("True")
                && userInput.get(13).equals("True")) { score++; }
        if ( userInput.get(14).equals("2")) { score++; }
        if ( userInput.get(15).toLowerCase().equals(getString(R.string.q9answer1).toLowerCase())
                || userInput.get(15).toLowerCase().equals(getString(R.string.q9answer2).toLowerCase())
                || userInput.get(15).toLowerCase().equals(getString(R.string.q9answer3).toLowerCase())) { score++; }
        if (    userInput.get(16).equals("False")
                && userInput.get(17).equals("True")
                && userInput.get(18).equals("True")
                && userInput.get(19).equals("False")) { score++; }
        return score;
    }

    /**
     * When called opens triviaplaza.com in browser
     * @param view summary TextView
     */
    public void openLink (View view) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://triviaplaza.com"));
        startActivity(i);
    }

    /**
     * Replaces summary text with the right answers provided in strings.xml
     */
    public void showAnswers (View view) {
        TextView summary = (TextView)findViewById(R.id.summaryTextView);
        summary.setText(getString(R.string.answerlist));
    }
}




















