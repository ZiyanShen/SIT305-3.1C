package com.flk.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnswerResultActivity extends BaseActivity {
    public static final String INTENT_ALL = "all";
    public static final String INTENT_RIGHT = "right";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_result);

        Intent intent = getIntent();
        String name = intent.getStringExtra(AnswerActivity.INTENT_NAME);
        int all = intent.getIntExtra(INTENT_ALL, 0);
        int right = intent.getIntExtra(INTENT_RIGHT, 0);

        ((TextView) findViewById(R.id.tv_name)).setText("Congratulation " + name);
        ((TextView) findViewById(R.id.tv_score)).setText(right + "/" + all);

        findViewById(R.id.bt_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        findViewById(R.id.bt_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.newInstance().finishAll();
            }
        });

    }
}