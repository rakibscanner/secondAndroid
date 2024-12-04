package com.rakib.secondandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText id, name, email;

    Button save,show;

    TextView details;

    MyOpenHelper openHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        id=findViewById(R.id.id);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        save=findViewById(R.id.save);
        show=findViewById(R.id.showall);
        details = findViewById(R.id.details);
        openHelper=new MyOpenHelper(getApplicationContext());
    }

    public void save(View v){
        database = openHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",Integer.parseInt(id.getText().toString()));
        cv.put("name",name.getText().toString());
        cv.put("email",email.getText().toString());
        database.insert("student", null, cv);
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        database.close();

    }


    public void showAll(View view){
        List<Student> stList  = new ArrayList<>();
        String allStudents="";
        database = openHelper.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM student", null);
        while (c.moveToNext()){
            Student s = new Student(c.getInt(0),c.getString(1),c.getString((2)));
            stList.add(s);
            allStudents+=s+"\n";

        }

        //----------showing data------------
//        details.setText(allStudents);
//        return  stList;
//        Toast.makeText(this, allStudents, Toast.LENGTH_SHORT).show();


        //showing data one page to another page
        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra("list", allStudents);
        startActivity(i);
    }
}