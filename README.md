# Installing the Cronos App Using Android Studio

This guide provides step-by-step instructions to install and run the Cronos app using Android Studio.

---

## Prerequisites

Before proceeding, ensure you have the following:

- **Android Studio Ladybug 2024.2.1** installed on your computer.
- The **Cronos app source code** (either cloned from a repository or downloaded as a ZIP).
- A compatible Android device or emulator (Recommended API 35).

---

## Steps to Install the App

### 1, Download the code

Please download the Cronos app using this repo. https://github.com/munsra/Cronos

### 2. Install Android Studio (Skip this step if you have already installed it)

If you don't have Android Studio installed, download and install it from the official [Android Studio website](https://developer.android.com/studio).

1. Download the installer for your operating system.
2. Follow the on-screen instructions to install Android Studio.
3. Launch Android Studio and complete the initial setup, including SDK installations.

---

### 3. Open the App Project in Android Studio

1. Open Android Studio.
2. Click on **File > Open** or **Open an Existing Project**.
3. Navigate to the folder containing the Cronos app source code and select it.
4. Allow Android Studio to sync the project files and download any required dependencies (this may take a few minutes).

---

### 3. Set Up an Emulator (Optional)

If you don't have a physical Android device, set up an emulator:

1. In Android Studio, go to **Tools > Device Manager**.
2. Click on **Create Device**.
3. Select a device profile and click **Next**.
4. Choose a System Image and click **Download** (if not already installed).
5. Finish the setup and launch the emulator.

---

### 4. Connect Your Android Device (Optional)

If you want to use a physical device:

1. Enable **Developer Options** on your Android device:
   - Go to **Settings > About Phone**.
   - Tap **Build Number** seven times to enable Developer Options.
2. Enable **USB Debugging** in Developer Options.
3. Connect your device to your computer using a USB cable.
4. Ensure the device appears in Android Studio under **Run > Select Device**.

---

### 5. Build the App

1. In Android Studio, click on **Build > Build Bundle(s)/APK(s) > Build APK(s)**.
2. Wait for the build process to complete.
3. Locate the generated APK file by clicking on the notification or navigating to the `app/build/outputs/apk/` directory.

---

### 6. Run the App

1. Click on the **Run** button in Android Studio (▶ icon) or press **Shift + F10**.
2. Select your connected device or emulator from the device list.
3. Wait for the app to compile and install on the selected device.

---

## Troubleshooting

- If the project fails to sync:
  - Check your internet connection.
  - Ensure all required SDKs and dependencies are installed.
- If the app doesn't install:
  - Verify your device/emulator is connected and detected by Android Studio.
  - Check for any build errors in the console and resolve them.

For additional help, refer to the [Android Studio Documentation](https://developer.android.com/studio/intro).

---

Congratulations! You have successfully installed the Cronos app using Android Studio.
