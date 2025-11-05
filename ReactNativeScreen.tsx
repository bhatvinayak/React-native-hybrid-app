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
      }}
    >
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
