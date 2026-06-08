# Nailytics

Nailytics is an Android health-monitoring prototype that helps users analyze color changes from an artificial microfluidic nail sensor. The app is built around a Nix sensor workflow: the user selects an analyte, reviews or edits the analyte color chart, connects to a Nix device, analyzes the detected color, and views an interpreted health-style result.

The project is built as a Kotlin Android app using XML layouts. UI screens, popups, drawables, and image assets are stored in `app/src/main/res`, while screen behavior, navigation, analyte chart logic, color editing, Nix connection logic, and measurement interpretation are implemented in `app/src/main/java/com/example/nailytics`.

> **Prototype note:** Nailytics is a research/prototype app. The result text is for demonstration and interpretation flow only; it is not medical advice or a diagnostic tool.

## App Flow

1. **Home screen**: Introduces Nailytics and lets the user start a health check.
2. **Searching for Nix screen**: Displays the selected analyte and color chart, requests Bluetooth permissions when needed, scans for a nearby Nix device, and automatically connects to the first discovered device.
3. **Edit color screen**: Lets the user choose an analyte value, enter a new color using RGB or HEX, automatically sync RGB/HEX fields, and save the new color into the shared analyte chart.
4. **Analyze color screen**: Shows the connected device, selected analyte chart, edit-color option, Change Device option, and Analyze button.
5. **Results screen**: Displays the measured Nix color, matched analyte label, interpretation text, recommended action-style explanation, and LOW/HIGH out-of-range messaging.
6. **Summary screens**: Show average analyte trends and pattern explanations.
7. **Profile screens**: Show login, profile details, editable personal-information screens, and a logout popup.

## Important Project Locations

```text
Nailytics/
└── app/
    └── src/
        └── main/
            ├── AndroidManifest.xml
            ├── java/com/example/nailytics/
            └── res/
                ├── layout/
                ├── drawable/
                ├── mipmap-*/
                ├── values/
                ├── anim/
                └── xml/
```

## Layout Files

The files in `Nailytics > app > src > main > res > layout` define the visual structure of each app screen, popup, and dropdown.

| File | Purpose |
|---|---|
| `main1.xml` | Home/landing screen. Introduces Nailytics and includes the Start button, color wheel container, and bottom navigation tabs. |
| `main2_searching_for_nix.xml` | Nix search/connect screen. Shows the selected analyte chart, analyte dropdown, Edit Colors button, loading spinner, back button, and bottom navigation. |
| `main3_changing_colors.xml` | Edit-color screen. Shows the selected analyte chart, analyte-value dropdown, color preview/edit section, RGB fields, HEX field, Save button, Cancel button, and navigation controls. |
| `main4_choosing_nix_device.xml` | Older/manual Nix device selection screen. Shows the selected analyte chart and device-connect UI. The current flow now connects automatically from Main2. |
| `main5_analyze_color.xml` | Analyze screen after a Nix device connects. Shows the selected analyte chart, connected-device status, Change Device option, Edit Colors option, and Analyze button. |
| `main_results.xml` | Results screen. Displays the selected analyte chart, connected-device info, measured result color, interpreted result title, result description, recommended actions, and Learn More button. |
| `analyte_dropdown_menu.xml` | Popup dropdown for choosing an analyte type and sample type: pH, glucose, and nitrate across blood, urine, and saliva. Includes checkmarks for selected items. |
| `analyte_value_dropdown_menu.xml` | Popup dropdown for choosing which analyte value/color to edit. The labels are populated dynamically from the currently selected analyte chart. |
| `summary_main.xml` | Main Summary screen. Shows average analyte result cards, chart section, daily/weekly/monthly/yearly controls, and summary descriptions. |
| `summary_pattern.xml` | Pattern detail screen. Shows a medical disclaimer, pattern description, graph, explanation section, Learn More button, and Ask Your Doctor section. |
| `profile_login.xml` | Sign-in screen. Includes email/username input, password input, Continue button, and Register Now prompt. |
| `profile_main.xml` | Main Profile screen. Displays profile photo, personal information rows, health information rows, logout button, and logout dimming overlay/popup anchor. |
| `logout_message_popup.xml` | Popup confirmation layout for logging out. Includes Cancel and Log out buttons. |
| `profile_name.xml` | Profile edit screen for first and last name. Includes Save, back button, and bottom navigation. |
| `profile_bday.xml` | Profile edit screen for birthday. Includes a birthday row, date wheel area, Save, back button, and bottom navigation. |
| `profile_gender.xml` | Profile edit screen for selecting gender. Includes options such as Female, Male, Non-binary, Transgender female, Transgender male, Prefer not to say, and Prefer to self-describe. |
| `profile_gender_2.xml` | Extended gender edit screen with the same gender options plus a self-describe text input field. |
| `profile_email.xml` | Profile edit screen for email. Includes an email input row, Save, back button, and bottom navigation. |
| `profile_bmi.xml` | Profile edit screen for height, weight, and BMI. Includes rows for height, weight, BMI, Save, back button, and bottom navigation. |

## Kotlin Source Files

The files in `Nailytics > app > src > main > java > com > example > nailytics` control screen behavior, navigation, dropdowns, analyte color charts, Nix sensor logic, and user interaction.

| File | Purpose |
|---|---|
| `NailyticsApp.kt` | Global `Application` class. Activates the Nix SDK license at app startup using `BuildConfig.NIX_LICENSE_OPTIONS` and `BuildConfig.NIX_LICENSE_SIGNATURE`, running activation on a background thread. |
| `Main1_Activity.kt` | Launcher/home Activity. Loads `main1.xml`, sends the user to the Nix search screen when they tap Start or the color wheel container, and sets up navigation to Profile and Summary. |
| `Main2_Searching_For_Nix_Activity.kt` | Loads `main2_searching_for_nix.xml`. Displays the selected analyte chart, requests Nix Bluetooth permissions, starts scanning, automatically connects to a nearby Nix device, shows loading/toast states, and moves to Main5 after connection succeeds. |
| `Main3_Changing_Colors_Activity.kt` | Loads `main3_changing_colors.xml`. Displays the selected analyte chart, opens analyte/analyte-value dropdowns, syncs RGB and HEX inputs, validates user color input, saves edited colors through `AnalyteColorChartManager.updateColor()`, and returns to the previous screen. |
| `Main4_Choosing_Nix_Device_Activity.kt` | Loads `main4_choosing_nix_device.xml`. Supports the older/manual device-choice flow and navigation to the Analyze Color screen. |
| `Main5_Analyze_Color_Activity.kt` | Loads `main5_analyze_color.xml`. Shows connected Nix device info, lets the user edit colors or change devices, calls `NixSensorManager.measureAndMatchCurrentAnalyte()`, and sends measured color/matched label data to Main6. |
| `Main6_Results_Activity.kt` | Loads `main_results.xml`. Displays the measured color, connected device, selected analyte chart, and analyte-specific interpretation text for pH, glucose, and nitrate, including LOW/HIGH out-of-range cases. |
| `NixSensorManager.kt` | Central Nix sensor manager. Handles Bluetooth permission helpers, scanning, connection, disconnection, M2 measurement, RGB extraction, color matching, outlier handling, and result packaging. |
| `NixColorMatchResult` | Data class returned from `NixSensorManager` to Main5. Stores the measured color, closest matched label, and closest chart color. |
| `ColorMatchResult` | Internal/private data class used while comparing chart colors. Stores RGB distance, LAB distance, and combined score for debugging/matching. |
| `Summary_Main_Activity.kt` | Loads `summary_main.xml`. Sets up shared navigation and opens the pattern detail screen when the summary pattern description is tapped. |
| `Summary_Pattern_Activity.kt` | Loads `summary_pattern.xml`. Displays detailed pattern information and shared Home/Profile/Summary/back navigation. |
| `Profile1_Main_Activity.kt` | Loads `profile_main.xml`. Opens editable profile sub-screens, sets up shared navigation, and shows a logout confirmation popup with dim overlay. |
| `Profile2_Login_Activity.kt` | Loads `profile_login.xml`. Sends the user to the main app/home screen when Continue is tapped. |
| `Profile3_Name_Activity.kt` | Loads `profile_name.xml`. Saves/returns from the name edit screen. |
| `Profile4_Bday_Activity.kt` | Loads `profile_bday.xml`. Saves/returns from the birthday edit screen. |
| `Profile5_Gender_Activity.kt` | Loads `profile_gender.xml`. Handles gender option checkmarks and opens the self-describe gender screen when needed. |
| `Profile6_Gender_Self_Describe_Activity.kt` | Loads `profile_gender_2.xml`. Supports the self-describe gender field and return/back behavior. |
| `Profile7_Email_Activity.kt` | Loads `profile_email.xml`. Saves/returns from the email edit screen. |
| `Profile8_BMI_Activity.kt` | Loads `profile_bmi.xml`. Saves/returns from the height, weight, and BMI edit screen. |
| `NavBar_Helper.kt` | Shared navigation helper. Adds click listeners for Home, Profile, Summary, and back navigation using nullable view lookups so screens without a certain tab/back button do not crash. |
| `AnalyteChartUIHelper.kt` | Shared UI helper for analyte chart behavior. Stores the currently selected analyte type and analyte value, updates the four color swatches and labels, opens dropdown popups, updates checkmarks, and resets the selected value when the analyte changes. |
| `AnalyteColorChartManager.kt` | Central data manager for analyte color charts. Stores color/value lists for saliva, blood, and urine pH, glucose, and nitrate. Provides `getChart()` and `updateColor()` methods. |
| `ColorChartValue.kt` | Simple Kotlin data class representing one color chart entry, with a `label` and mutable `color` value. |
| `ui/theme/Color.kt`, `Theme.kt`, `Type.kt` | Theme files generated by the Android project template. |

## Nix (Spectrophotometer) Sensor Integration

Nailytics includes a Nix (Spectrophotometer) SDK workflow.

### Startup/license activation

`NailyticsApp.kt` activates the Nix SDK license when the app launches. The license values are read from generated `BuildConfig` fields:

```kotlin
BuildConfig.NIX_LICENSE_OPTIONS
BuildConfig.NIX_LICENSE_SIGNATURE
```

The app class is registered in `AndroidManifest.xml` using:

```xml
android:name=".NailyticsApp"
```

### Permissions

`AndroidManifest.xml` includes Nix/Bluetooth-related permissions:

```xml
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

`Main2_Searching_For_Nix_Activity.kt` uses `ActivityResultContracts.RequestMultiplePermissions()` to request the permissions returned by:

```kotlin
NixSensorManager.requiredBluetoothPermissions()
```

### Scan/connect flow

`Main2_Searching_For_Nix_Activity.kt` starts the permission flow and then calls:

```kotlin
NixSensorManager.startScan(...)
```

The current behavior is:

1. Clear any stale Nix connection.
2. Show the loading spinner.
3. Start Bluetooth scanning.
4. Stop scanning after the first nearby Nix device is found.
5. Connect after a short delay.
6. Store the connected device in `NixSensorManager.connectedDevice`.
7. Pass device name/id to `Main5_Analyze_Color_Activity`.
8. Remove Main2 from the back stack after successful connection.

### Measurement mode

`NixSensorManager.kt` measures with:

```kotlin
private val measurementMode = ScanMode.M2
```

M2 is used because it excludes UV, which is intended to reduce unwanted optical effects from acrylic/resin nail material.

### RGB extraction

After measurement, the code extracts color data using:

```kotlin
Illuminant.D50
Observer.CIE1931
```

The measured RGB array is converted to an Android `Color` int and passed into the color-matching system.

## Color Matching Logic

`NixSensorManager.measureAndMatchCurrentAnalyte()` measures the current color and calls `findClosestColorMatch()` to compare the measured color against the currently selected analyte chart.

The current matching score combines four distance features:

| Feature | Why it is used |
|---|---|
| RGB Euclidean distance | Compares raw sensor RGB similarity. |
| LAB distance | Compares perceptual color similarity using AndroidX `ColorUtils.RGBToLAB()`. |
| Chromaticity distance | Compares brightness-normalized RGB ratios, helping when Nix readings are darker or lighter than expected. |
| Opponent-channel distance | Compares channel relationships such as R-G, R-B, and G-B to catch subtle shifts between close dark colors. |

The combined score is currently weighted as:

```kotlin
val combinedDistanceScore =
    (0.25 * normalizedRgbDistance) +
    (0.30 * normalizedLabDistance) +
    (0.25 * normalizedChromaticityDistance) +
    (0.20 * normalizedOpponentDistance)
```

Lower score means a closer match. Logcat debug messages print the measured RGB value, every chart color, the RGB/LAB/chromaticity/opponent distances, and the final combined score.

## Outlier / Out-of-Range Logic

After the closest match is found, `calculateForOutliers()` checks whether the measured color should be reported as outside the calibrated analyte range.

The active function in the uploaded source uses edge-based LAB thresholds:

- LOW can be returned when the closest match is the first chart value **and** the measured color is unusually far from that first value.
- HIGH can be returned when the closest match is the last chart value **and** the measured color is unusually far from that last value.
- Otherwise, the normal closest match is kept.

## Shared Analyte Color Chart Logic

Nailytics uses a shared analyte chart system across Main2, Main3, Main5, Main6, and Summary screens.

The selected analyte type is stored globally using:

```kotlin
selectedAnalyteId
selectedAnalyteName
```

The selected analyte value being edited is stored using:

```kotlin
selectedAnalyteValueIndex
selectedAnalyteValueName
```

The actual chart values are stored in `AnalyteColorChartManager.kt` as a map from analyte item ID to a mutable list of `ColorChartValue` objects.

Current chart groups include:

| Analyte group | Values |
|---|---|
| pH | `pH 5`, `pH 6`, `pH 7`, `pH 8` |
| Glucose | `Glucose 40 mM`, `Glucose 80 mM`, `Glucose 120 mM`, `Glucose 160 mM` |
| Nitrate | `Nitrate 2.5 mM`, `Nitrate 5.0 mM`, `Nitrate 7.5 mM`, `Nitrate 10.0 mM` |

The pH charts now use realistic lab-measured colors, along with glucose and nitrate.

`AnalyteChartUIHelper.updateColorChart(activity)` refreshes the visible chart by updating:

```text
item0_color, item1_color, item2_color, item3_color
item0_label, item1_label, item2_label, item3_label
analyte_type
```

On Main3, it also resets the selected analyte value so the edit-value dropdown matches the selected analyte type.

## Editing Analyte Colors

`Main3_Changing_Colors_Activity.kt` supports functional color editing.

The user can:

1. Choose the analyte type using the analyte dropdown.
2. Choose the specific analyte value using the analyte-value dropdown.
3. Enter RGB values or a HEX value.
4. Let the app auto-sync RGB to HEX or HEX to RGB.
5. Save the new color into the selected `ColorChartValue`.
6. Return to the previous screen with the updated chart visible.

Input validation includes:

- RGB values must be integers from 0 to 255.
- HEX values must match `#RRGGBB` or `#AARRGGBB`.

## Results Interpretation

`Main6_Results_Activity.kt` receives these values from Main5:

```kotlin
measured_color
closest_label
closest_color
device_name
```

The measured color is displayed on the result screen. The result text is selected based on analyte type and matched label.

Supported result cases include:

| Analyte | Supported labels |
|---|---|
| pH | `LOW`, `pH 5`, `pH 6`, `pH 7`, `pH 8`, `HIGH` |
| Glucose | `LOW`, `Glucose 40 mM`, `Glucose 80 mM`, `Glucose 120 mM`, `Glucose 160 mM`, `HIGH` |
| Nitrate | `LOW`, `Nitrate 2.5 mM`, `Nitrate 5.0 mM`, `Nitrate 7.5 mM`, `Nitrate 10.0 mM`, `HIGH` |

LOW/HIGH examples:

- pH LOW displays as `pH 4-: Highly Acidic`.
- pH HIGH displays as `pH 9+: Highly Alkaline`.
- Glucose LOW displays as `Glucose < 40 mM: Very Low`.
- Glucose HIGH displays as `Glucose > 160 mM: Very High`.
- Nitrate LOW displays as `Nitrate < 2.5 mM: Very Low`.
- Nitrate HIGH displays as `Nitrate > 10.0 mM: Very High`.

## Dropdown and Popup Behavior

`AnalyteChartUIHelper.showAnalyteDropdown()` inflates `analyte_dropdown_menu.xml` into a `PopupWindow`. It uses `screen_dim_overlay` when available to dim the screen behind the dropdown. When an analyte is selected, the helper updates the selected analyte ID/name, refreshes the checkmark, dismisses the popup, hides the overlay, and updates the chart.

`AnalyteChartUIHelper.showAnalyteValueDropdown()` inflates `analyte_value_dropdown_menu.xml` into a `PopupWindow`. It dynamically fills the four rows from the selected analyte chart, so Main3 can edit pH, glucose, or nitrate values without a separate hard-coded dropdown for each analyte.

`Profile1_Main_Activity.showLogoutPopup()` inflates `logout_message_popup.xml` into a popup and uses the same dimming-overlay pattern for logout confirmation.

## Navigation Behavior

Most screen-to-screen navigation is handled inside each Activity using `Intent`. Shared tab navigation is handled by `NavBar_Helper.kt`:

- `moveToHome(activity)` opens `Main1_Activity`
- `moveToProfile(activity)` opens `Profile1_Main_Activity`
- `moveToSummary(activity)` opens `Summary_Main_Activity`
- `moveToPreviousScreen(activity)` calls `finish()` when a back button exists

Main2 has custom back behavior that disconnects from Nix and returns to Main1 using `FLAG_ACTIVITY_CLEAR_TOP` and `FLAG_ACTIVITY_SINGLE_TOP`.

Main5 and Main6 have Change Device behavior that disconnects the current Nix device and returns to Main2 to scan/connect again.

## Android Manifest

`AndroidManifest.xml` registers the main app class and Activities.

Application class:

- `NailyticsApp`

Registered Activities include:

- `Main1_Activity`
- `Main2_Searching_For_Nix_Activity`
- `Main3_Changing_Colors_Activity`
- `Main4_Choosing_Nix_Device_Activity`
- `Main5_Analyze_Color_Activity`
- `Main6_Results_Activity`
- `Summary_Main_Activity`
- `Summary_Pattern_Activity`
- `Profile1_Main_Activity`
- `Profile2_Login_Activity`
- `Profile3_Name_Activity`
- `Profile4_Bday_Activity`
- `Profile5_Gender_Activity`
- `Profile6_Gender_Self_Describe_Activity`
- `Profile7_Email_Activity`
- `Profile8_BMI_Activity`

where `Main1_Activity` is the launcher Activity.

## Build Information

- Language: Kotlin
- UI approach: XML layouts
- Android namespace/application ID: `com.example.nailytics`
- Minimum SDK: 24
- Target SDK: 36
- Compile SDK: 36
- Key dependencies: AppCompat, ConstraintLayout, Material Components, AndroidX Core graphics utilities, and the Nix Universal SDK
- Nix license configuration: expected through generated `BuildConfig` fields

## Current Notes / TODOs
- Color matching is actively being tuned. The current formula combines RGB, LAB, chromaticity, and opponent-channel distances, but very dark and very similar chart colors may still be difficult to separate reliably. Using more sensory channels from the Nix Spectro device can help increase the color-to-analyte value accuracy.
- Main4 still exists as an older/manual device selection screen, but the current Main2 flow automatically connects to the first discovered Nix device and moves directly to Main5. For future work, Main 4 can be reimplemented to include scanning for multiple Nix devices, and letting the user choose which Nix device to connect to.
- Profile edit screens are now registered and navigable, but they mostly return to the profile screen rather than persisting long-term profile data.
- Profile login input behavior is still prototype-level.
- User-entered analyte colors update the in-memory chart during the app session; persistent storage has not been added yet.
- The color modal portion of `Main3_Changing_Colors_Activity` is also still-prototype level; implementing a dynamic color modal screen as the user inputs RGB or HEX values will be a future work.

## Summary

Nailytics has grown from a Figma front-end prototype into a more complete Nix-connected Android prototype. The app now supports Bluetooth permission handling, Nix SDK license activation, automatic device scanning/connection, M2 color measurement, multi-feature color matching, analyte color editing with RGB/HEX sync, analyte-specific result interpretation, and navigable profile edit screens. The next major development steps are stabilizing the outlier classification logic, improving repeated-measurement accuracy, persisting edited chart, profile, and summary data, and continuing validation with real lab color readings.
