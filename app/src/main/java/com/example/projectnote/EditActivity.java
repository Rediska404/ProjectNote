package com.example.projectnote;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectnote.db.MyDbManager;

public class EditActivity extends AppCompatActivity {
    private  EditText Title, Description;
    private MyDbManager myDbManager;
    private ImageView imNewImage;
    private FloatingActionButton fbAddImage;
    private ConstraintLayout imageContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    @Override
    protected void onResume(){
        super.onResume();

        myDbManager.openDb();

    }

    private  void init () {
        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.Description);
        imageContainer = findViewById(R.id.imageContainer);
        fbAddImage = findViewById(R.id.fbAddImage);
        myDbManager = new MyDbManager( this);
    }

    public void onClickSave(View view) {
        String title = Title.getText().toString();
        String description = Description.getText().toString();

        if (title.equals("") || description.equals("")) {

            Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();

        } else {

            myDbManager.insertToDb(title, description);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            finish();
            myDbManager.closeDb();
        }
    }
    public void onClickDeleteImage (View view) {
        imageContainer.setVisibility(View.GONE);
        findViewById(R.id.fbAddImage).setVisibility(View.VISIBLE);
    }

    public void onClickAddImage (View view) {
        imageContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }
}