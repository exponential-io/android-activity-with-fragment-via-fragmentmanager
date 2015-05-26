# Android Activity with Fragments via FragmentManager / FragmentTransaction

Example Android project for learning how to use Activites with Fragments that are associated via
the FragmentManger / FragmentTransaction. The example code in this repo includes communication flows
from Fragment to Fragment, Fragment to Activity, Activity to Fragment, and Activity to Activity.

> If the Android Studio layout preview shows an error by default, then edit `res/values/styles.xml` 
> as follows:
> change: &lt;style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar"&gt;
> to:     &lt;style name="AppTheme" parent="Base.Theme.AppCompat.Light.DarkActionBar"&gt;


## Workflow

### 1. Create the Fragment's layout

1. Create a layout.
2. Design the UI in the layout.

### 2. Create a Fragment

1. Create a Fragment, then update the Fragment's logic.
2. Parameters
    - Parameters allow the Activity to pass data to the Fragment during Fragment creation.
    - Update the parameter list and all associated variables/constants
3. Layout
    - Inflate the layout in `onCreateView()`.
    - Modify the layout, such as calling `setText()` etc,  in `onCreateView()`.
4. Interface
    - Interface methods allow the Fragment to pass data to the Activity.
    - Rename the Interface to `Callbacks`.
    - Rename the member instance of `Callbacks` to `callbacks`.
    - Define the Interface callback method signatures in the `Callbacks` Interface.
5. Event handlers
    - Define event handling methods after Interface because the event handlers will often call an
      Interface method.
    - Attach event handlers to views in `onCreateView()`.
6. Public methods
    - Public methods allow the Activity to pass data to the Fragment.
    - Define any required public methods.

### 3. Update the Activity's layout

1. Design the portion of the UI that is Activity specific in the Activity's layout.
2. Add a ViewGroup, such as LinearLayout or FrameLayout, for each Fragment that you want to insert
   into the Activity.

### 4. Update the Activity

1. Change `ActionBarActivity` to `AppCompatActivity`.
2. Create an instance of each Fragment in `onCreate()`.
3. Add the Fragment instance to the Activity via FragmentManager, FragmentTransaction, and
   `fragmentTransaction.add()`.
4. Add 'implements FragmentName.Callbacks` to the Activity's class declaration for each Fragment.
5. Override and implement the required interface methods.
6. Where necessary (often in event handlers within the Activity), call the Fragment's public methods
   to pass data into the Fragment.
