# 🚀 HybridApp — React Native + Native Android (Kotlin) Integration POC

## HybridApp is a proof-of-concept demonstrating seamless integration between React Native and native Android (Kotlin) screens in a single mobile app.

It allows React Native to:

- Render the first screen (React side)

- Collect user input (name and age)

- Send that data to a fully native Kotlin Activity

- Display the passed data on the native screen

This architecture is perfect for teams that want to combine the flexibility of React Native with the performance and power of native code.

## 🧩 Features

- 🟦 React Native entry screen for collecting input

- 🤖 Native Android Activity rendered entirely in Kotlin

- 🔄 Data passing from React Native → Native (via Intents)

- ⏪ Smooth back navigation to React Native

- ⚙️ Clean, modular bridge setup (NativeModules)

## 📁 Folder Structure

```
HybridApp/
├── App.js # React Native entry screen (with inputs)
├── android/
│ └── app/src/main/java/com/hybridapp/
│ ├── NativeScreenActivity.kt # Native Android screen
│ ├── NativeScreenModule.kt # Bridge module
│ ├── NativeScreenPackage.kt # Bridge package registration
│ ├── MainActivity.kt # Default RN Activity
│ ├── MainApplication.kt # Registers custom packages
│ └── AndroidManifest.xml # Activity declaration
└── ios/
├── NativeScreenViewController.swift # (Optional) Native iOS screen
├── NativeScreenModule.swift # (Optional) Bridge for iOS
└── HybridApp-Bridging-Header.h # Swift bridge header
```

## 🛠️ Setup Instructions

### 1. Create a new React Native project

> npx @react-native-community/cli@latest init HybridApp

> cd HybridApp

### 2. Create a Kotlin Activity (Native Screen)

> File: android/app/src/main/java/com/hybridapp/NativeScreenActivity.kt

```
package com.hybridapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.\*

class NativeScreenActivity : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name") ?: "Guest"
        val age = intent.getIntExtra("age", 0)

        val textView = TextView(this)
        textView.text = "Hello $name, age $age — from Native Android!"
        textView.textSize = 20f
        textView.setPadding(100, 200, 100, 100)

        val button = Button(this)
        button.text = "Back to React Native"
        button.setOnClickListener { finish() }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(textView)
        layout.addView(button)

        setContentView(layout)
    }

}
```

### 3. Create a Native Module (Bridge)

> File: android/app/src/main/java/com/hybridapp/NativeScreenModule.kt

```
package com.hybridapp

import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class NativeScreenModule(private val reactContext: ReactApplicationContext) :
ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "NativeScreen"

    @ReactMethod
    fun openNativeScreen(name: String, age: Int) {
        val intent = Intent(reactContext, NativeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("name", name)
        intent.putExtra("age", age)
        reactContext.startActivity(intent)
    }

}
```

### 4. Register the Module

> File: android/app/src/main/java/com/hybridapp/NativeScreenPackage.kt

```
package com.hybridapp

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class NativeScreenPackage : ReactPackage {
override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
return listOf(NativeScreenModule(reactContext))
}

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }

}
```

#### Then add this to your MainApplication.kt:

```
packages.add(NativeScreenPackage())
```

### 5. Declare the Activity in AndroidManifest.xml

> File: android/app/src/main/AndroidManifest.xml

```
<activity
  android:name=".NativeScreenActivity"
  android:exported="false"
  android:theme="@style/Theme.AppCompat.Light.NoActionBar" />
```

6. Update the React Native UI

> File: ReactNativeScreen.tsx

```
import React, { useState } from 'react';
import {
View,
Text,
TextInput,
Button,
NativeModules,
Alert,
} from 'react-native';
const { NativeScreen } = NativeModules;

export default function ReactNativeScreen() {
    const [name, setName] = useState('');
    const [age, setAge] = useState('');

    const openNative = () => {
    if (!name || !age) {
    Alert.alert('Please enter both name and age');
    return;
    }
    NativeScreen.openNativeScreen(name, parseInt(age));
    };

    return (
    <View
    style={{
            justifyContent: 'center',
            alignItems: 'center',
            padding: 20,
        }} >
    <Text style={{ fontSize: 24, marginBottom: 20 }}>
    React Native Screen
    </Text>

        <TextInput
            placeholder="Enter name"
            value={name}
            onChangeText={setName}
            style={{
            borderWidth: 1,
            borderColor: '#ccc',
            width: '80%',
            padding: 10,
            marginBottom: 10,
            borderRadius: 8,
            }}
        />
        <TextInput
            placeholder="Enter age"
            value={age}
            onChangeText={setAge}
            keyboardType="numeric"
            style={{
            borderWidth: 1,
            borderColor: '#ccc',
            width: '80%',
            padding: 10,
            marginBottom: 20,
            borderRadius: 8,
            }}
        />

        <Button title="Open Native Screen" onPress={openNative} />
        </View>

    );
}
```

### 7. Run the app

> npx react-native run-android

✅ Expected Flow

- React Native screen opens with two inputs — Name and Age.

- User enters details and presses “Open Native Screen.”

- The app navigates to the native Kotlin screen, showing the message:

- Hello {Name}, age {age} — from Native Android!

- Pressing “Back to React Native” closes the native screen and returns to React Native.

## 💡 Optional iOS Implementation

- To make this cross-platform:

- Create NativeScreenViewController.swift

- Create NativeScreenModule.swift

- Expose it using a bridging header

- Call NativeScreen.openNativeScreen(name, age) the same way from React Native.

## 📚 Learnings

- How to invoke native Activities/ViewControllers from React Native.

- How to pass data across the JS ↔ Native bridge.

- How to embed hybrid navigation in a modular architecture.

- Useful pattern for apps migrating from native → React Native.

## 🧠 Future Enhancements

- Pass data back from native → React Native (using DeviceEventEmitter or ActivityEventListener).

- Add React Navigation integration for unified hybrid navigation.

- Create shared Kotlin/Swift base bridges for scalability.

- Extend native UI with a more complex layout or external SDK integration.
