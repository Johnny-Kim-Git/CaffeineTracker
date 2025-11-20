package com.example.caffeinetracker;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.caffeinetracker.data.AppDatabaseHelper;
import com.example.caffeinetracker.data.CaffeineDataSeeder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) INIT DB (ADD THIS)
        AppDatabaseHelper dbHelper = new AppDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        CaffeineDataSeeder.seedFromAssetsIfEmpty(this, db);

        // 2) Your UI stuff (KEEP THIS AS-IS)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
