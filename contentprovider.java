

package com.example.temp.cpexam;

import android.Manifest;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showContacts();
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            //Uri allcontacts = Uri.parse("content://contacts/people");
            Cursor c = managedQuery(ContactsContract.Contacts.CONTENT_URI
                    , null, null, null, null);

            String[] columns = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID
            };
            int[] views = new int[]{R.id.contactName, R.id.contactID};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_main, c, columns, views);
            this.setListAdapter(adapter);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


