package com.flk.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEdt = findViewById(R.id.edt_name);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(v.getContext(), "name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), AnswerActivity.class);
                intent.putExtra(AnswerActivity.INTENT_NAME, name);
                startActivity(intent);
            }
        });
    }
}