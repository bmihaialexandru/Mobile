import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';


export default class AsyncUtils{
	static async getAll(){
		
		global.firebaseApp.database().ref().child('songs').on('value', (snap) => {
		global.songs=[];
		snap.forEach((child) => {
			global.songs.push({
			  id: child.val().id,
			  title: child.val().title,
			  artist: child.val().artist,
			  genre: child.val().genre,
			  fireKey: child.key
		});
		global.SongList.updateList();
		})});

		
	}
	
	static async add(song){
		await AsyncStorage.setItem(song.id.toString(), JSON.stringify(song), (error) => {});
		
	}
	
	static async remove(id){
		await AsyncStorage.removeItem(id.toString(), (error) => {});
		
	}
	
	static async update(song){
		await AsyncStorage.setItem(song.id.toString(), JSON.stringify(song), (error) => {});
		
	}
}