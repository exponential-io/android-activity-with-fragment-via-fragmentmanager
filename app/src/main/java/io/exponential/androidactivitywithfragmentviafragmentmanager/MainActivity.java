package io.exponential.androidactivitywithfragmentviafragmentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity
        extends AppCompatActivity
        implements UserInformationFragment.Callbacks {

    // Event handlers
    OnClickListener setAge = new OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText ageEditText = (EditText) findViewById(R.id.main_activity_age);
            String age = ageEditText.getText().toString();

            // Get a reference to the existing UserInformationFragment instance
            UserInformationFragment userInformationFragment =
                    (UserInformationFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.user_information_container);

            // Pass age to the Fragment via a public method
            userInformationFragment.setAge(age);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the intent here and extract any data passed as extras. Also, these extras can be
        // passed to each Fragment via its newInstance() factory method.
        //Intent intent = getIntent();
        //String someString = intent.getStringExtra(SomeActivity.EXTRA_SOME_KEY);

        // In production, an Activity will use multiple layouts. Specifically, an Activity will use
        // different layouts for different device sizes and orientations. Therefore, we need to
        // write our logic such that logic for specific Fragment only runs when that Fragment is
        // present.
        if (findViewById(R.id.user_information_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            String favoriteNumber = "9";
            String favoriteColor = "green";

            // Create an instance of UserInformationFragment via the Fragment's factory method. We
            // pass both favoriteNumber and favoriteColor from this Activity into the Fragment when
            // it is created. This mechanism can be used to pass information that the Fragment must
            // have in order to function properly.
            UserInformationFragment userInformationFragment = UserInformationFragment
                    .newInstance(favoriteNumber, favoriteColor);

            // Add the fragment to the `user_information_container` FrameLayout
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            // A better way of writing the two lines above is as a single line. There is not need
            // to create the `fm` variable.
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.add(R.id.user_information_container, userInformationFragment).commit();
        }

        // TODO: WHERE TO PUT EVENT HANDLERS
        // TODO: WHERE TO PUT EVENT HANDLERS
        // TODO: WHERE TO PUT EVENT HANDLERS
        // TODO: WHERE TO PUT EVENT HANDLERS
        // Attach event handlers. This is a bad location to attach event handlers as the Activity's
        // onCreate() method is not called again after a screen rotation.
        Button setAgeButton = (Button) findViewById(R.id.activity_main_set_age);
        setAgeButton.setOnClickListener(setAge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFirstName(String firstName) {
        TextView firstNameTextView = (TextView) findViewById(R.id.activity_main_first_name);
        firstNameTextView.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        TextView lastNameTextView = (TextView) findViewById(R.id.activity_main_last_name);
        lastNameTextView.setText(lastName);
    }

    @Override
    public void sayHi(String greeting) {
        // Pass greeting down to the other Fragment

    }
}
