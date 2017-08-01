package com.dian.a15017591.p11knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String msg;

    ArrayList<String> alQuestions = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        alQuestions.add(0, "Singapore National Day is on 9 Aug");
        alQuestions.add(1, "Singapore is 52 years old");
        alQuestions.add(2, "Theme is #OneNationTogether");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alQuestions);
        lv.setAdapter(adapter);



        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.dialog, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editTextCode);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Access Code")
                .setView(passPhrase)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String input = etPassphrase.getText().toString();

                        if (input.equals("738964")) {
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect access code, quitting application",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.itemQuit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);


                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.itemSend) {
            String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend?")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Look at this");
                                String msg = alQuestions.get(0) + "\n" + alQuestions.get(1) + "\n" + alQuestions.get(2) + "\n";

                                emailIntent.setType("plain/text");
                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);

                                startActivity(emailIntent);


                            } else if (item == 1) {
                                int pCheck = PermissionChecker.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS);

                                if (pCheck != PermissionChecker.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 0);
                                } else {
                                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                                    String msg = alQuestions.get(0) + "\n" + alQuestions.get(1) + "\n" + alQuestions.get(2) + "\n";


                                    smsIntent.setType("vnd.android-dir/mms-sms");
                                    smsIntent.putExtra("sms_body", msg);
                                    startActivity(smsIntent);
                                }
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 0: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
}
