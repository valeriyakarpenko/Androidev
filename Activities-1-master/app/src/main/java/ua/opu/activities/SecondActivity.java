package ua.opu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mSubjectEditText;
    private EditText mMessageEditText;

    private Button mConfirmButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mEmailEditText = findViewById(R.id.email_et);
        mSubjectEditText = findViewById(R.id.subject_et);
        mMessageEditText = findViewById(R.id.message_et);

        mConfirmButton = findViewById(R.id.button_confirm);
        mConfirmButton.setOnClickListener(v -> {

            // 1. Получаем значения полей ввода
            String email = mEmailEditText.getText().toString();
            String subject = mSubjectEditText.getText().toString();
            String message = mMessageEditText.getText().toString();

            // 2. Формируем Intent со значениями
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_EMAIL, email);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, message);

            // 3. Устанавливаем результат работы окна
            setResult(RESULT_OK, i);

            // 4. Закрываем окно
            finish();
        });

        mCancelButton = findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}