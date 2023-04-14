package com.flk.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends BaseActivity {
    public static final String INTENT_NAME = "name";

    private String mTestName;

    private TextView mNameTv;
    private TextView mProgressTv;
    private ProgressBar mBar;
    private TextView mQuestionTitle;
    private TextView mQuestionContent;
    private RadioGroup mQuestionRG;
    private final RadioButton[] mQuestionRBs = new RadioButton[3];
    private Button mSubmitTv;

    private final List<Question> mAllQuestion = initAllQuestion();
    private int mRightCount;
    private int mCurAnswerPos;
    private boolean isAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_activiy);
        mTestName = getIntent().getStringExtra(INTENT_NAME);
        if (TextUtils.isEmpty(mTestName)) {
            Toast.makeText(this, "name is empty!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initView();
        initListener();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        mNameTv.setText("Welcome " + mTestName);
        mBar.setMax(mAllQuestion.size());
        resetTest();
    }

    @SuppressLint("SetTextI18n")
    private void showQuestionData() {
        int progress = mCurAnswerPos + 1;
        mProgressTv.setText(progress + "/" + mAllQuestion.size());
        mBar.setProgress(progress);
        Question question = mAllQuestion.get(mCurAnswerPos);
        mQuestionTitle.setText(question.title);
        mQuestionContent.setText(question.content);
        mQuestionRG.clearCheck();
        for (int i = 0; i < mQuestionRBs.length; i++) {
            RadioButton b = mQuestionRBs[i];
            b.setChecked(false);
            b.setBackgroundColor(Color.WHITE);
            b.setText(question.answers[i]);
            b.setEnabled(true);
        }
        isAnswer = false;
        mSubmitTv.setText("Submit");
    }

    /**
     * 从新答题
     */
    private void resetTest() {
        mCurAnswerPos = 0;
        mRightCount = 0;
        isAnswer = false;
        showQuestionData();
    }

    private void initListener() {
        mSubmitTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAnswer) {
                    nextQuestion();
                } else {
                    checkAnswer();
                }
            }
        });
    }

    private void nextQuestion() {
        if (mCurAnswerPos >= mAllQuestion.size()) {
            Intent intent = new Intent(this, AnswerResultActivity.class);
            intent.putExtra(INTENT_NAME, mTestName);
            intent.putExtra(AnswerResultActivity.INTENT_ALL, mAllQuestion.size());
            intent.putExtra(AnswerResultActivity.INTENT_RIGHT, mRightCount);
            startActivityForResult(intent, 99);
            return;
        }
        showQuestionData();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                resetTest();
            }
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    private void checkAnswer() {
        int radioButtonId = mQuestionRG.getCheckedRadioButtonId();
        int pos;
        switch (radioButtonId) {
            case R.id.rb_answer1:
                pos = 0;
                break;
            case R.id.rb_answer2:
                pos = 1;
                break;
            case R.id.rb_answer3:
                pos = 2;
                break;
            default:
                Toast.makeText(this, "please select a answer", Toast.LENGTH_SHORT).show();
                return;

        }
        Question question = mAllQuestion.get(mCurAnswerPos);
        mQuestionRBs[question.rightPos].setBackgroundResource(R.color.answer_right);
        if (question.rightPos != pos) {
            mQuestionRBs[pos].setBackgroundResource(R.color.answer_error);
        } else {
            mRightCount++;
        }
        mCurAnswerPos++;
        isAnswer = true;
        for (RadioButton b : mQuestionRBs) {
            b.setEnabled(false);
        }
        mSubmitTv.setText("Next");
    }

    private void initView() {
        mNameTv = findViewById(R.id.tv_test_name);
        mProgressTv = findViewById(R.id.tv_progress);
        mBar = findViewById(R.id.pb);
        mQuestionTitle = findViewById(R.id.tv_question_title);
        mQuestionContent = findViewById(R.id.tv_question_content);
        mQuestionRG = findViewById(R.id.rg_answer);
        mQuestionRBs[0] = findViewById(R.id.rb_answer1);
        mQuestionRBs[1] = findViewById(R.id.rb_answer2);
        mQuestionRBs[2] = findViewById(R.id.rb_answer3);
        mSubmitTv = findViewById(R.id.bt_next);
    }

    private List<Question> initAllQuestion() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("java Fundamentals", "1、The Java variable naming convention states the following correctly", 1,
                new String[]{"Variables cannot start with a number",
                        "Variables of different types can have the same name",
                        "Variables can consist of any letter, underscore, number, or symbol"}));
        list.add(new Question("java Fundamentals", "2、Which of the following data types is not the base data type of java", 1,
                new String[]{"char", "string", "boolean"}));
        list.add(new Question("java Advanced", "3、ArrayList, LinkedList and other List collection classes compare objects by which method to compare", 1,
                new String[]{"toString", "equals", "hashCode"}));
        list.add(new Question("java Advanced", "4、The Synchronized keyword cannot be modified in which position", 1,
                new String[]{"Modifying blocks of code", "Modifying properties", "Modifying static methods"}));
        list.add(new Question("Android Fundamentals", "5、Which of the following is not an Activity start method", 1,
                new String[]{"singleTop", "singleBottom", "singleTask"}));
        return list;
    }

}