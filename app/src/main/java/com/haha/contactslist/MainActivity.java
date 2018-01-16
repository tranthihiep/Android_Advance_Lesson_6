package com.haha.contactslist;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String phoneNumber = "";
    private ArrayList<SelectUser> mSelectUsers;
    private ListView mListView;
    private Cursor mCursor;
    private ContentResolver resolver;
    private SelectUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkPer();


    }

    private void checkPer() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS}, 4);
            return;
        }
        mSelectUsers = new ArrayList<SelectUser>();
        resolver = this.getContentResolver();
        mListView = (ListView) findViewById(R.id.contacts_list);
        try {
            mCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (mCursor != null) {
                Log.e("count", "" + mCursor.getCount());
                if (mCursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts on list.", Toast.LENGTH_LONG).show();
                }

                while (mCursor.moveToNext()) {
                    String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNumber = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    SelectUser selectUser = new SelectUser();
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    mSelectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close", "----------------");
            }
            mCursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter = new SelectUserAdapter(mSelectUsers, MainActivity.this);
            mListView.setAdapter(mAdapter);

    }


    protected void setCall() {


        Intent intentcall = new Intent(Intent.ACTION_CALL);
        intentcall.setData(Uri.parse("tel:" + phoneNumber.toString()));

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intentcall);

    }
    protected void setSend(){
        Intent intentcall2 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber.toString()));
        startActivity(intentcall2);
    }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4) {
            checkPer();
        }
    }
}
