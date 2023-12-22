package com.example.database;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DB_sql databaseFile = new DB_sql(this);
    EditText NAME, LNAME, ID;
    ListView TheList;
    Button orderButton;
    ImageView productImage;
    Button add;
    Uri universal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NAME = (EditText) findViewById(R.id.Name);
        LNAME = (EditText) findViewById(R.id.Lname);
        ID = (EditText) findViewById(R.id.ID);
        TheList = (ListView) findViewById(R.id.TheList);
        orderButton = (Button) findViewById(R.id.button5);
        productImage = (ImageView) findViewById(R.id.imageView);
        add = (Button) findViewById(R.id.imageProduct);
    }

    public void SaveData(View view) {
        String name = NAME.getText().toString();
        String Lname = LNAME.getText().toString();
        String THEID = ID.getText().toString();
        boolean RES = databaseFile.insertData(name, Lname, THEID);
        if (RES == true) {
            Toast.makeText(MainActivity.this, "ok saved", Toast.LENGTH_SHORT).show();
            NAME.setText("");
            LNAME.setText("");
            ID.setText("");
        } else {
            Toast.makeText(MainActivity.this, "not saved", Toast.LENGTH_SHORT).show();
            NAME.setText("");
            LNAME.setText("");
            ID.setText("");
        }

    }

    public void ShowData(View view) {
        ArrayList<String> listData = databaseFile.getRecords();
        ArrayAdapter arrA = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);
        TheList.setAdapter(arrA);
    }

    public void Delete(View view) {
        String THEID = ID.getText().toString();
        Integer res = databaseFile.deleteTable(THEID);
        if (res > 1) {
            Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        }
        NAME.setText("");
        LNAME.setText("");
        ID.setText("");
    }

    public void update(View view) {
        String na = NAME.getText().toString();
        String Lnm = LNAME.getText().toString();
        String THEID = ID.getText().toString();
        Boolean rs = databaseFile.updateTable(na, Lnm, THEID);
        if (rs == true) {
            Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();

        }
        NAME.setText("");
        LNAME.setText("");
        ID.setText("");
    }

    public void order(View view) {
        String number = "78827104";
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url))
                .setType("image/*")
                        .putExtra(Intent.EXTRA_STREAM, universal);
        startActivity(i);
    }

    public void addImage(View view) {
        Intent addImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(addImage, 24);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 24 && resultCode == RESULT_OK) {
            Uri universal = data.getData();
            productImage.setImageURI(universal);
        }
    }

    public void delete(View view) {
        productImage.setImageBitmap(null);
    }
}
