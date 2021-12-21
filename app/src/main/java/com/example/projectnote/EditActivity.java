package com.example.projectnote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.example.projectnote.db.AppExecuter;
import com.example.projectnote.db.MyConstants;
import com.example.projectnote.db.MyDbManager;

public class EditActivity extends AppCompatActivity {
    private  EditText Title, Description;
    private MyDbManager myDbManager;
    private ImageView imNewImage;
    private ImageButton imEditImage, imDeleteImage;
    private FloatingActionButton fbAddImage;
    private ConstraintLayout imageContainer;
    private final int PICK_IMAGE_CODE = 123;
    private  String tempUri = "empty";
    private boolean isEditState = true;
    private  ListItem item;


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
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private  void init () {
        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.Description);
        imageContainer = findViewById(R.id.imageContainer);
        fbAddImage = findViewById(R.id.fbAddImage);
        myDbManager = new MyDbManager( this);
        imNewImage = findViewById(R.id.imNewImage);
        imEditImage = findViewById(R.id.imEditImage);
        imDeleteImage = findViewById(R.id.imDeleteImage);
    }

    private void getMyIntents() {
        Intent i = getIntent();
        if(i != null) {

            item = (ListItem) i.getSerializableExtra(MyConstants.LIST_ITEM_INTENT);

            isEditState = i.getBooleanExtra(MyConstants.EDIT_STATE, true);

            if(!isEditState) {

                Title.setText(item.getTitle());
                Description.setText(item.getDesc());

                if (!item.getUri().equals("empty")){
                    tempUri = item.getUri();
                    imageContainer.setVisibility(View.VISIBLE);
                    imNewImage.setImageURI(Uri.parse(item.getUri()));
                    imDeleteImage.setVisibility(View.GONE);
                    imEditImage.setVisibility(View.GONE);
                }
            }
        }
    }

    public void onClickSave(View view) {
        final String title = Title.getText().toString();
        final String description = Description.getText().toString();

        if (title.equals("") || description.equals("")) {

            Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();

        } else {

            if(isEditState) {

                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDbManager.insertToDb(title, description, tempUri);
                    }
                });

                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();

            } else {

                myDbManager.updateItem(title, description, tempUri, item.getId());

                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();

            }
            myDbManager.closeDb();
            finish();
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

        Intent chooser = new Intent (Intent.ACTION_OPEN_DOCUMENT);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }
}