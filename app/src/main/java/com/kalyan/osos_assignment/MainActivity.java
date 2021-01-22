package com.kalyan.osos_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ArrayList<String> Titles = new ArrayList<String>();
    ArrayList<Uri> imagesList =new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission() == false){
            requestPermission();
        }









        floatingActionButton = (FloatingActionButton)findViewById(R.id.Floating);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final SampleAdapter sampleadpater = new SampleAdapter(MainActivity.this,Titles,imagesList);
        sampleadpater.setOnItemClickListener(new SampleAdapter.OnItemClickListener() {
            @Override
            public void ImageClick(int position) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Dialog dialog = new Dialog(MainActivity.this); // Context, this, etc.
                dialog.setContentView(R.layout.dialog_demo);
                final EditText Title = (EditText)dialog.findViewById(R.id.title);
                Button Ok = (Button)dialog.findViewById(R.id.ok);
                final Button Cancel = (Button)dialog.findViewById(R.id.cancel);
                Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,Title.getText().toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        Titles.add(Title.getText().toString());
                        SampleAdapter sampleAdapter = new SampleAdapter(MainActivity.this,Titles,imagesList);
                        recyclerView.setAdapter(sampleadpater);
                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,Title.getText().toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imagesList.add(imageUri);
                        Toast.makeText(this,imageUri.toString(),Toast.LENGTH_LONG).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
                    SampleAdapter sampleAdapter = new SampleAdapter(this,Titles,imagesList);
                    recyclerView.setAdapter(sampleAdapter);
                    sampleAdapter.notifyDataSetChanged();
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
    }
}
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}