package io.exponential.androidactivitywithfragmentviafragmentmanager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements UserInformationFragment.Callbacks, FoodFragment.Callbacks {

    public static final String TAG = "MainActivity:lfc";

    // Event handlers

    // Pass age to both the UserInformationFragment and the FoodFragment.
    OnClickListener setAge = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String age = ((EditText) findViewById(R.id.main_activity_age)).getText().toString();

            // Get a reference to the existing UserInformationFragment instance
            FragmentManager fm = getSupportFragmentManager();

            UserInformationFragment userInformationFragment =
                    (UserInformationFragment) fm.findFragmentById(R.id.user_information_container);

            // Pass age to the Fragment via a public method
            userInformationFragment.setAge(age);

            // Get a reference to the existing FoodFragment instance
            FoodFragment foodFragment = (FoodFragment) fm.findFragmentById(R.id.food_container);

            // Pass age to the Fragment via a public method
            foodFragment.setAge(age);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "S:onCreate");

        String favoriteNumber = "9";
        String favoriteColor = "green";

        // Get the intent here and extract any data passed as extras. Also, these extras can be
        // passed to each Fragment via its newInstance() factory method.
        //Intent intent = getIntent();
        //String someString = intent.getStringExtra(SomeActivity.EXTRA_SOME_KEY);

        // Attach event handlers. You must attach the event handlers before creating the Fragment
        // because `if (savedInstanceState != null)` returns immediately if the Fragment is being
        // restored from a previous state (which is true when the screen is rotated).

        Button setAgeButton = (Button) findViewById(R.id.activity_main_set_age);
        setAgeButton.setOnClickListener(setAge);

        // In production, an Activity will use multiple layouts. Specifically, an Activity will use
        // different layouts for different device sizes and orientations. Therefore, we need to
        // write our logic such that logic for specific Fragment only runs when that Fragment is
        // present.

        if (findViewById(R.id.user_information_container) != null
                && findViewById(R.id.food_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of UserInformationFragment via the Fragment's factory method. We
            // pass both favoriteNumber and favoriteColor from this Activity into the Fragment when
            // it is created. This mechanism can be used to pass information that the Fragment must
            // have in order to function properly.
            UserInformationFragment userInformationFragment = UserInformationFragment
                    .newInstance(favoriteNumber, favoriteColor);

            // Create an instance of the FoodFragment
            FoodFragment foodFragment = FoodFragment.newInstance(favoriteNumber, favoriteColor);

            // Get a reference to the FragmentManager and begin a FragmentTransaction
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            // A better way of writing the two lines above is as a single line. There is no need
            // to create the `fm` variable.
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            // Insert both Fragments into the Activity's layout
            ft.add(R.id.user_information_container, userInformationFragment);
            ft.add(R.id.food_container, foodFragment);

            ft.commit();
        }

        Log.v(TAG, "E:onCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
    public void sendToFoodFragment(String name) {
        // Pass name down to FoodFragment
        // Get a reference to the existing UserInformationFragment instance
        FoodFragment foodFragment =
                (FoodFragment) getSupportFragmentManager().findFragmentById(R.id.food_container);

        // Pass age to the Fragment via a public method
        foodFragment.setName(name);
    }

    @Override
    public void setFavoriteFood(String favoriteFood, Boolean sendValueToOtherFragment) {
        if (sendValueToOtherFragment) {
            // Pass favorite food to UserInformationFragment by calling a public method

            // Get a reference to the existing UserInformationFragment instance
            UserInformationFragment userInformationFragment =
                    (UserInformationFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.user_information_container);

            // Pass age to the Fragment via a public method
            userInformationFragment.setFood(favoriteFood);
        } else {
            // Display favoriteFood in this Activity's layout

            TextView favoriteFoodTextView = (TextView) findViewById(R.id.activity_main_favorite_food);
            favoriteFoodTextView.setText(favoriteFood);
        }
    }
}
