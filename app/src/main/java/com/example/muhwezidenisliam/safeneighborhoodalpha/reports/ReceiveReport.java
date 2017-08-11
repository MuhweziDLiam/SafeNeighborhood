package com.example.muhwezidenisliam.safeneighborhoodalpha.reports;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.muhwezidenisliam.safeneighborhoodalpha.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Muhwezi Denis Liam on 8/9/2017.
 */

public class ReceiveReport extends AppCompatActivity {

    @Bind(R.id.report_image_view)
    ImageView report_image_view;

    @Bind(R.id.descriptionView)
    TextView descriptionView;

    @Bind(R.id.DateView)
    TextView dateView;

    @Bind(R.id.timeView)
    TextView timeView;

    @Bind(R.id.layout_view_victim)
    LinearLayout layout_view_victim;

    @Bind(R.id.layout_get_directions)
    LinearLayout layout_get_directions;

    @Bind(R.id.victim_phone_number)
    TextView victim_phone_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_report);
        ButterKnife.bind(this);

        victim_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCallDialog("Muhwezi Denis",victim_phone_number.getText().toString().trim());
            }
        });

        layout_view_victim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact();
            }
        });

        layout_get_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDirections();
            }
        });

    }


    public void getDirections()
    {

    }

    public void contact()
    {
        victim_phone_number.setVisibility(View.VISIBLE);
    }

    private void showCallDialog(String user_name, final String phone_number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact " + user_name);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:" + phone_number));
                if (ActivityCompat.checkSelfPermission(ReceiveReport.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(call_intent);

            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.show();
    }
}
