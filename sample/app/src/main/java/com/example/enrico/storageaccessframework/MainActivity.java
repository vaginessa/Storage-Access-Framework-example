package com.example.enrico.storageaccessframework;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSDCardAccess(MainActivity.this);
            }
        });
    }

    private void saveSDCardUri(Activity activity, String uri) {

        SharedPreferences preferenceSD;
        preferenceSD = activity.getSharedPreferences("sdUri", Context.MODE_PRIVATE);


        preferenceSD.edit()
                .clear()
                .apply();

        preferenceSD.edit()
                .putString("selectedSD", uri)
                .apply();
    }

    private String getSDCardUri(Activity activity) {

        SharedPreferences preferenceSD = activity.getSharedPreferences("sdUri", Context.MODE_PRIVATE);

        return preferenceSD.getString("selectedSD", "");
    }

    private void askSDCardAccess(final Activity activity) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getSDCardUri(activity).isEmpty()) {

                    rationaleDialog(activity);

                }
            }
        });

    }

    public static void rationaleDialog(final Activity activity) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        alertDialogBuilder.setTitle(activity.getString(R.string.sdcard));


        alertDialogBuilder.setMessage(activity.getString(R.string.sdcardContent));

        alertDialogBuilder
                .setCancelable(false)

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

                        activity.startActivityForResult(intent, 1);

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {

                    saveSDCardUri(this, String.valueOf(data.getData()));

                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT)
                            .show();

                }
                break;
        }
    }
}
