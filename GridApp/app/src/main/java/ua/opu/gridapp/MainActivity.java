package ua.opu.gridapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DigitDialogue.MyDialogListener {
    private RecyclerView recyclerView;
    private CircleAdapter adapter;
    private List<Circle> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        for (int j = 0; j < 28; j++) {
            list.add(new Circle(random.nextInt(98) + 1, Color.argb(127, random.nextInt(256), random.nextInt(256), random.nextInt(256))));
        }
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new CircleAdapter(getApplicationContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int itemPosition = recyclerView.getChildLayoutPosition(v);
        int msg = list.get(itemPosition).getDigit();
        DigitDialogue dialog = DigitDialogue.newInstance(Integer.toString(msg));
        dialog.show(getSupportFragmentManager(), "dlg");
    }

    @Override
    public void onMyDialogResult(String choice) {
        Toast.makeText(this, "Выбран пункт: " + choice, Toast.LENGTH_SHORT).show();
    }
}