# Nailytics

Nailytics is an Android health-monitoring prototype that helps users analyze color changes from an artificial microfluidic nail sensor. The app is designed around a Nix sensor workflow: the user selects an analyte, reviews or edits the analyte color chart, connects to a Nix device, analyzes the detected color, and views the resulting health interpretation.

The project is built as a Kotlin Android app using XML layouts. The UI screens are stored in `app/src/main/res/layout`, while screen behavior, navigation, analyte chart logic, and popup/dropdown behavior are implemented in `app/src/main/java/com/example/nailytics`.

## App Flow

1. **Home screen**: Introduces Nailytics and lets the user start a health check.
2. **Searching for Nix screen**: Displays the selected analyte and color chart while looking for a Nix sensor device.
3. **Edit color screen**: Allows the user to view/edit target color values for the analyte chart.
4. **Choose Nix device screen**: Lets the user select one of the available Nix sensor devices.
5. **Analyze color screen**: Shows the connected device and lets the user analyze the biosensor color.
6. **Results screen**: Displays the measured analyte result, interpretation, and recommended actions.
7. **Summary screens**: Show average analyte trends and pattern explanations.
8. **Profile screens**: Show sign-in, user information, and profile settings.

## Important Project Locations

```text
Nailytics/
└── app/
    └── src/
        └── main/
            ├── AndroidManifest.xml
            ├── java/com/example/nailytics/
            └── res/layout/
```

## Layout Files

The files in `Nailytics > app > src > main > res > layout` define the visual structure of each app screen, popup, and dropdown.

| File | Purpose |
|---|---|
| `main1.xml` | Home/landing screen. Introduces the Nailytics app and includes the Start button, color wheel container, and bottom navigation tabs. |
| `main2_searching_for_nix.xml` | Main health-check screen for searching for a Nix sensor. Includes the analyte dropdown, four-value color chart, edit colors button, loading image, back button, and bottom navigation. |
| `main3_changing_colors.xml` | Edit color screen/modal-style page. Shows the selected analyte chart, update target color section, color picker preview, RGB/HEX fields, Save button, and Cancel button. |
| `main4_choosing_nix_device.xml` | Nix device selection screen. Shows the selected analyte chart and lists available Nix devices with Connect buttons. |
| `main5_analyze_color.xml` | Analyze screen after a Nix device is connected. Shows the selected analyte chart, connected device status, Change Device option, and Analyze button. |
| `main_results.xml` | Results screen. Displays the selected analyte chart, connected Nix info, measured result color, result interpretation, recommended actions, and Learn More button. |
| `analyte_dropdown_menu.xml` | Popup dropdown menu for choosing an analyte. Includes options for pH, glucose, and nitrate across blood, urine, and saliva, with checkmarks for selected items. |
| `analyte_value_dropdown_menu.xml` | Dropdown menu for choosing a specific pH value, such as pH 5, pH 6, pH 7, and pH 8. This appears intended for choosing which analyte value/color to edit. |
| `summary_main.xml` | Main Summary screen. Shows average analyte result cards, a chart section, daily/weekly/monthly/yearly controls, and summary descriptions. |
| `summary_pattern.xml` | Pattern detail screen. Shows a medical disclaimer, pattern description, graph image, explanation section, Learn More button, and Ask Your Doctor section. |
| `profile_login.xml` | Sign-in screen. Includes email/username input, password input, Continue button, and Register Now prompt. |
| `profile_main.xml` | Main Profile screen. Displays profile photo, personal information rows, health information rows, a Log out button, and the logout dimming overlay/popup anchor. |
| `logout_message_popup.xml` | Popup confirmation layout for logging out. Includes Cancel and Log out buttons. |
| `profile_name.xml` | Profile edit screen for first and last name. Includes Save, back button, and bottom navigation. |
| `profile_bday.xml` | Profile edit screen for birthday. Includes a birthday row, date wheel area, Save, back button, and bottom navigation. |
| `profile_gender.xml` | Profile edit screen for selecting gender. Includes gender options such as Female, Male, Non-binary, Transgender female, Transgender male, Prefer not to say, and Prefer to self-describe. |
| `profile_gender_2.xml` | Extended gender edit screen that includes the same gender options plus a self-describe text input field. |
| `profile_email.xml` | Profile edit screen for email. Includes an email input row, Save, back button, and bottom navigation. |
| `profile_bmi.xml` | Profile edit screen for height, weight, and BMI. Includes rows for height, weight, BMI, Save, back button, and bottom navigation. |

## Kotlin Source Files

The files in `Nailytics > app > src > main > java > com > example > nailytics` control screen behavior, navigation, dropdowns, analyte color charts, and user interaction.

| File | Purpose |
|---|---|
| `Main1_Activity.kt` | Launcher/home Activity. Loads `main1.xml`, sends the user to the Nix search screen when they tap Start or the color wheel container, and sets up bottom navigation to Profile and Summary. |
| `Main2_Searching_For_Nix_Activity.kt` | Loads `main2_searching_for_nix.xml`. Updates the analyte color chart, opens the analyte dropdown, navigates to the Edit Colors screen, and moves to device selection when the loading image is tapped. |
| `Main3_Changing_Colors_Activity.kt` | Loads `main3_changing_colors.xml`. Displays the selected analyte chart and dropdown. The back, cancel, and save buttons currently finish the Activity and return to the previous screen. |
| `Main4_Choosing_Nix_Device_Activity.kt` | Loads `main4_choosing_nix_device.xml`. Displays the selected analyte chart, opens the analyte dropdown, navigates to Edit Colors, and moves to Analyze Color after the user taps either Nix device Connect button. |
| `Main5_Analyze_Color_Activity.kt` | Loads `main5_analyze_color.xml`. Displays the selected analyte chart, allows editing colors, changes the Nix device by returning to the Nix search screen, and moves to the Results screen when Analyze is tapped. |
| `Main6_Results_Activity.kt` | Loads `main_results.xml`. Displays the selected analyte chart and result screen. Also allows the user to change the Nix device and use shared navigation/back behavior. |
| `Summary_Main_Activity.kt` | Loads `summary_main.xml`. Sets up the analyte dropdown and navigates to the pattern detail screen when the summary pattern description is tapped. |
| `Summary_Pattern_Activity.kt` | Loads `summary_pattern.xml`. Displays detailed pattern information and sets up shared Home, Profile, Summary, and back navigation. |
| `Profile_Login_Activity.kt` | Loads `profile_login.xml`. Sends the user to the main app/home screen when Continue is tapped. Text input behavior is marked as a TODO. |
| `Profile_Main_Activity.kt` | Loads `profile_main.xml`. Displays the profile screen and shows a logout confirmation popup with a dimming overlay when the logout button is tapped. |
| `NavBar_Helper.kt` | Shared navigation helper. Adds click listeners for Home, Profile, Summary, and back navigation. Uses nullable `findViewById` calls so screens without a specific tab/back button do not immediately crash from missing IDs. |
| `AnalyteChartUIHelper.kt` | Shared UI helper for analyte chart behavior. Stores the currently selected analyte, updates the four color swatches and labels, opens the analyte dropdown popup, updates checkmarks, and refreshes the chart after a selection. |
| `AnalyteColorChartManager.kt` | Central data manager for analyte color charts. Stores color/value lists for saliva, blood, and urine pH, glucose, and nitrate. Provides `getChart()` and `updateColor()` methods. |
| `ColorChartValue.kt` | Simple Kotlin data class representing one color chart entry, with a `label` and mutable `color` value. |

## Shared Analyte Color Chart Logic

Nailytics uses a shared analyte chart system across the main health-check screens. The selected analyte is stored globally using:

```kotlin
selectedAnalyteId
selectedAnalyteName
```

The actual chart values are stored in `AnalyteColorChartManager.kt` as a map from analyte item ID to a mutable list of `ColorChartValue` objects. Each analyte currently has four values. For example:

- pH charts use `pH 5`, `pH 6`, `pH 7`, and `pH 8`
- glucose charts use `40 mM`, `80 mM`, `120 mM`, and `160 mM`
- nitrate charts use `2.5 mM`, `5.0 mM`, `7.5 mM`, and `10.0 mM`

`AnalyteChartUIHelper.updateColorChart(activity)` refreshes the visible chart by updating:

```text
item0_color, item1_color, item2_color, item3_color
item0_label, item1_label, item2_label, item3_label
analyte_type
```

This lets multiple screens show the same currently selected analyte without duplicating chart-update code in every Activity.

## Dropdown and Popup Behavior

`AnalyteChartUIHelper.showAnalyteDropdown()` inflates `analyte_dropdown_menu.xml` into a `PopupWindow`. It also uses `screen_dim_overlay` when available to dim the screen behind the dropdown. When an analyte is selected, the helper updates the selected analyte ID/name, refreshes the checkmark, dismisses the popup, hides the overlay, and updates the chart.

`Profile_Main_Activity.showLogoutPopup()` inflates `logout_message_popup.xml` into a popup and uses the same dimming-overlay idea for logout confirmation.

## Navigation Behavior

Most screen-to-screen navigation is handled inside each Activity using `Intent`. Shared tab navigation is handled by `NavBar_Helper.kt`:

- `moveToHome(activity)` opens `Main1_Activity`
- `moveToProfile(activity)` opens `Profile_Main_Activity`
- `moveToSummary(activity)` opens `Summary_Main_Activity`
- `moveToPreviousScreen(activity)` calls `finish()` when a back button exists

## Android Manifest

`AndroidManifest.xml` registers the main screens as Activities. `Main1_Activity` is the launcher Activity, meaning it is the first screen opened when the app starts.

Registered Activities include:

- `Main1_Activity`
- `Main2_Searching_For_Nix_Activity`
- `Main3_Changing_Colors_Activity`
- `Main4_Choosing_Nix_Device_Activity`
- `Main5_Analyze_Color_Activity`
- `Main6_Results_Activity`
- `Summary_Main_Activity`
- `Summary_Pattern_Activity`
- `Profile_Main_Activity`
- `Profile_Login_Activity`

Some profile edit layouts exist in `res/layout`, but their corresponding Activities are not currently registered in the manifest.

## Current Notes / TODOs

- Real Nix sensor connection logic is not implemented yet. The app currently moves forward by tapping placeholder UI elements, such as the loading image or device buttons.
- Profile login input behavior is marked as a TODO.
- The color edit screen currently closes on Save, Cancel, or Back, but the actual user-entered color update workflow still needs to be connected to `AnalyteColorChartManager.updateColor()`.
- The logout popup currently uses `showAsDropDown()`, so it appears relative to the logout button instead of being centered on the screen.
- Several profile edit XML screens exist, but they are not yet connected to Activity classes or manifest entries.

## Build Information

- Language: Kotlin
- UI approach: XML layouts
- Android namespace/application ID: `com.example.nailytics`
- Minimum SDK: 24
- Target SDK: 36
- Compile SDK: 36
- Key dependencies: AppCompat, ConstraintLayout, Material Components

## Summary

Nailytics is currently a working Android prototype focused on the front-end flow for a biosensor-based nail health monitoring app. The strongest implemented parts are the screen flow, XML-based interface, shared bottom navigation, analyte dropdown menu, and reusable analyte color chart system. The next major development steps are connecting the Nix sensing workflow, making color editing fully functional, saving user/profile data, and wiring profile edit screens into the app flow.
