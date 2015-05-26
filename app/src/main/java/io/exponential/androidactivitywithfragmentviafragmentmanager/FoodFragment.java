package io.exponential.androidactivitywithfragmentviafragmentmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {
    public static final String TAG = "FoodFragment:lfc";

    // Fragment initialization parameter keys. These keys are used to pass the argument values
    // via a Bundle created in the factory method which is later accessed in onCreate().
    private static final String ARG_FAVORITE_NUMBER = "io.exponential.app.favoriteNumber";
    private static final String ARG_FAVORITE_COLOR = "io.exponential.app.favoriteColor";

    private String favoriteNumber;
    private String favoriteColor;

    private Callbacks callbacks;

    // sendToActivity is not a good name. However, in this example the name was chosen to highlight
    // the data flow.
    OnClickListener sendToActivity = new OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText favoriteFoodEditText;
            Boolean forwardValueToOtherFragment = false;

            // Assign an empty string to favoriteFood so that we avoid a type error in case view is
            // null below.
            String favoriteFood = "";

            // v is the button (view) that was clicked. If the `food_send_to_fragment` button was
            // clicked then we can set sendToFragment to true.
            if (v.getId() == R.id.food_send_to_fragment) {
                forwardValueToOtherFragment = true;
            }

            View view = getView();

            if (view != null) {
                favoriteFoodEditText = (EditText) view.findViewById(R.id.food_favorite_food);
                favoriteFood = favoriteFoodEditText.getText().toString();
            }

            if (callbacks != null) {
                callbacks.setFavoriteFood(favoriteFood, forwardValueToOtherFragment);
            }
        }
    };

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @param favoriteNumber The activity author's favorite number.
     * @param favoriteColor The activity author's favorite color.
     * @return A new instance of fragment FoodFragment.
     */
    public static FoodFragment newInstance(String favoriteNumber, String favoriteColor) {
        Log.v(TAG, "S:newInstance");

        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FAVORITE_NUMBER, favoriteNumber);
        args.putString(ARG_FAVORITE_COLOR, favoriteColor);
        fragment.setArguments(args);

        Log.v(TAG, "E:newInstance");
        return fragment;
    }

    public FoodFragment() {
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
        View view = inflater.inflate(R.layout.food, container, false);

        // Note: getArguments() works in onCreateView(). However, it appears that the recommended
        //       approach is to call getArguments() in onCreate(), and to then use a member variable
        //       to store the argument values.
        //String favoriteNumber =  getArguments().getString(ARG_FAVORITE_NUMBER);

        // Display the arguments passed via the factor method in the UI
        TextView favoriteNumberTextView = (TextView) view.findViewById(R.id.food_favorite_number);
        favoriteNumberTextView.setText(favoriteNumber);

        TextView favoriteColorTextView = (TextView) view.findViewById(R.id.food_favorite_color);
        favoriteColorTextView.setText(favoriteColor);

        // Attached event handlers
        Button sendToActivityButton = (Button) view.findViewById(R.id.food_send_to_activity);
        Button sendToFragmentButton = (Button) view.findViewById(R.id.food_send_to_fragment);

        // We are going to reuse the same event handler for both sendToActivity and sendToFragment.
        // The only difference is that we'll include a Boolean to inform the Activity weather or not
        // it should forward the request to the UserInformationFragment.
        sendToActivityButton.setOnClickListener(sendToActivity);
        sendToFragmentButton.setOnClickListener(sendToActivity);

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

    // Public methods
    // A Fragment's public methods are used by the Activity to pass data and events into the
    // Fragment

    public void setAge (String age) {
        View view = getView();

        if (view != null) {
            TextView ageTextView = (TextView) view.findViewById(R.id.food_age);
            ageTextView.setText(age);
        }
    }

    public void setName(String name) {
        View view = getView();

        if (view != null) {
            //TextView nameTextView = (TextView) view.findViewById(R.id.food_name);
            //nameTextView.setText(name);

            // Same code as the 2 lines above but written without the use of the nameTextView single
            // use variable
            ((TextView) view.findViewById(R.id.food_name)).setText(name);
        }
    }

    // The interface methods, which must be implemented by the parent Activity, allow this Fragment
    // to pass data and events up to the Activity.
    public interface Callbacks {
        public void setFavoriteFood(String favoriteFood, Boolean sendValueToOtherFragment);
    }

}
