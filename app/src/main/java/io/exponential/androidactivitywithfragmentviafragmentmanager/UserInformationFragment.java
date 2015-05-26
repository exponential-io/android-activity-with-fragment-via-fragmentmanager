package io.exponential.androidactivitywithfragmentviafragmentmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
// Include this import so we can write OnClickListener instead of View.OnClickListener.
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Callbacks} interface
 * to handle interaction events.
 * Use the {@link UserInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInformationFragment extends Fragment {
    public static final String TAG = "UIFragment:lfc";

    // Fragment initialization parameter keys. These keys are used to pass the argument values
    // via a Bundle created in the factory method which is later accessed in onCreate().
    private static final String ARG_FAVORITE_NUMBER = "io.exponential.app.favoriteNumber";
    private static final String ARG_FAVORITE_COLOR = "io.exponential.app.favoriteColor";

    private String favoriteNumber;
    private String favoriteColor;

    private Callbacks callbacks;

    // sendToActivity is not a good name. A better name would be setFirstNameLastName. However, in
    // this example the name was chosen to highlight the data flow.
    OnClickListener sendToActivity = new OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstNameEditText;
            EditText lastNameEditText;

            // Assign an empty string to both firstName and lastName
            String firstName = "";
            String lastName = "";

            View view = getView();

            if (view != null) {
                firstNameEditText = (EditText) view.findViewById(R.id.user_information_first_name);
                lastNameEditText = (EditText) view.findViewById(R.id.user_information_last_name);

                firstName = firstNameEditText.getText().toString();
                lastName = lastNameEditText.getText().toString();
            }

            if (callbacks != null) {
                callbacks.setFirstName(firstName);
                callbacks.setLastName(lastName);
            }
        }
    };

    OnClickListener sendToFragment = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (callbacks != null) {

                // Notice how all of the code to get the firstName and lastName is similar to the
                // code in the sendToActivity OnClickListener above. It would make sense to separate
                // this code into functions such as getFirstName() and getLastName().

                EditText firstNameEditText;
                EditText lastNameEditText;

                // Assign an empty string to both firstName and lastName
                String firstName = "";
                String lastName = "";
                String name = "";

                View view = getView();

                if (view != null) {
                    firstNameEditText = (EditText) view.findViewById(R.id.user_information_first_name);
                    lastNameEditText = (EditText) view.findViewById(R.id.user_information_last_name);

                    firstName = firstNameEditText.getText().toString();
                    lastName = lastNameEditText.getText().toString();

                    name = firstName + " " + lastName;
                }


                callbacks.sendToFoodFragment(name);
            }
        }
    };

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @param favoriteNumber The activity author's favorite number.
     * @param favoriteColor The activity author's favorite color.
     * @return A new instance of fragment UserInformationFragment.
     */
    public static UserInformationFragment newInstance(String favoriteNumber, String favoriteColor) {
        Log.v(TAG, "S:newInstance");

        UserInformationFragment fragment = new UserInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FAVORITE_NUMBER, favoriteNumber);
        args.putString(ARG_FAVORITE_COLOR, favoriteColor);
        fragment.setArguments(args);

        Log.v(TAG, "E:newInstance");
        return fragment;
    }

    public UserInformationFragment() {
        // Required empty public constructor
        Log.v(TAG, "S:constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "S:onCreate");

        if (getArguments() != null) {
            favoriteNumber = getArguments().getString(ARG_FAVORITE_NUMBER);
            favoriteColor = getArguments().getString(ARG_FAVORITE_COLOR);
        }

        Log.v(TAG, "E:onCreate");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        Log.v(TAG, "S:onCreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_information, container, false);

        // Note: getArguments() works in onCreateView(). However, it appears that the recommended
        //       approach is to call getArguments() in onCreate(), and to then use a member variable
        //       to store the argument values.
        //String favoriteNumber =  getArguments().getString(ARG_FAVORITE_NUMBER);

        // Display the arguments passed via the factor method in the UI
        TextView favoriteNumberTextView = (TextView) view.findViewById(R.id.user_information_favorite_number);
        favoriteNumberTextView.setText(favoriteNumber);

        TextView favoriteColorTextView = (TextView) view.findViewById(R.id.user_information_favorite_color);
        favoriteColorTextView.setText(favoriteColor);

        // Attached event handlers

        Button sendToActivityButton = (Button) view.findViewById(R.id.user_information_send_to_activity);
        sendToActivityButton.setOnClickListener(sendToActivity);

        // sendToFragment is a bad name. Actually, we can only send data to the parent Activity.
        // It is up to the Activity to send the data to the other Fragment. However, in this case
        // the name is designed to signify the overall data flow, not direct Fragment to Fragment
        // communication.
        Button sendToFragmentButton = (Button) view.findViewById(R.id.user_information_send_to_fragment);
        sendToFragmentButton.setOnClickListener(sendToFragment);

        Log.v(TAG, "E:onCreateView");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(TAG, "S:onAttach");

        try {
            callbacks = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Callbacks");
        }

        Log.v(TAG, "E:onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "S:onDetach");
        callbacks = null;
        Log.v(TAG, "E:onDetach");
    }

    // Define public methods that are used by the Activity to pass data and events into the Fragment
    public void setAge (String age) {
        View view = getView();

        if (view != null) {
            TextView ageTextView = (TextView) view.findViewById(R.id.user_information_age);
            ageTextView.setText(age);
        }
    }

    public void setFood(String favoriteFood) {
        View view = getView();

        if (view != null) {
            TextView favoriteFoodTextView = (TextView) view.findViewById(R.id.user_information_favorite_food);
            favoriteFoodTextView.setText(favoriteFood);
        }
    }

    // The interface methods, which must be implemented by the parent Activity, allow this Fragment
    // to pass data and events up to the Activity.
    public interface Callbacks {
        public void setFirstName(String firstName);
        public void setLastName(String lastName);
        public void sendToFoodFragment(String name);
    }

}
