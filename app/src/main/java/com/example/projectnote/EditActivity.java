package com.example.projectnote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectnote.adapter.ListItem;
import com.example.projectnote.db.MyConstants;
import com.example.projectnote.db.MyDbManager;

public class EditActivity extends AppCompatActivity {
    private  EditText Title, Description;
    private MyDbManager myDbManager;
    private ImageView imNewImage;
    //private ImageButton imEditImage, imDeleteImage;
    private FloatingActionButton fbAddImage;
    private ConstraintLayout imageContainer;
    private final int PICK_IMAGE_CODE = 123;
    private  String tempUri = "empty";
    private boolean isEditState = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntents();
    }

    @Override
    protected void onResume(){
        super.onResume();

        myDbManager.openDb();

    }

    protected void OnActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null) {

            tempUri = data.getData().toString();
            imNewImage.setImageURI(data.getData());
        }
    }

    private  void init () {
        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.Description);
        imageContainer = findViewById(R.id.imageContainer);
        fbAddImage = findViewById(R.id.fbAddImage);
        myDbManager = new MyDbManager( this);
        imNewImage = findViewById(R.id.imNewImage);
    }

    private void getMyIntents() {
        Intent i = getIntent();
        if(i != null) {

            ListItem item = (ListItem) i.getSerializableExtra(MyConstants.LIST_ITEM_INTENT);
            isEditState = i.getBooleanExtra(MyConstants.EDIT_STATE, true);

            if(!isEditState) {

                Title.setText(item.getTitle());
                Description.setText(item.getDesc());
            }
        }
    }

    public void onClickSave(View view) {
        String title = Title.getText().toString();
        String description = Description.getText().toString();

        if (title.equals("") || description.equals("")) {

            Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();

        } else {

            myDbManager.insertToDb(title, description, tempUri);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            finish();
            myDbManager.closeDb();
        }
    }
    public void onClickDeleteImage (View view) {
        imNewImage.setImageResource(R.drawable.ic_image_def);
        tempUri = "empty";
        imageContainer.setVisibility(View.GONE);
        findViewById(R.id.fbAddImage).setVisibility(View.VISIBLE);
    }

    public void onClickAddImage (View view) {
        imageContainer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    public void onClickChooseImage (View view) {

        Intent chooser = new Intent (Intent.ACTION_PICK);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }
}