import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import AsyncUtils from "./components/asyncutils";
import Stack from "./components/router";
import * as firebase from 'firebase';

global.genres=['Rock', 'Rap', 'Techno', 'Pop']

global.songs=[];


global.firebaseConfig = {
	apiKey: "AIzaSyC61wurWRXqjq5963rP4eMG1DWcb1MFPrs",
	authDomain: "spotify2-ede95.firebaseapp.com",
	databaseURL: "https://spotify2-ede95.firebaseio.com",
	projectId: "spotify2-ede95",
	storageBucket: "spotify2-ede95.appspot.com",
	messagingSenderId: "903726680527"
};
global.firebaseApp = firebase.initializeApp(global.firebaseConfig);




export default class App extends React.Component {
	render() {
        return <Stack/>
    }
}






