import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import AsyncUtils from "./asyncutils";
import Stack from "./router";
import Styles from "./styles";

const extractKey=({id}) => id

export default class SongList extends React.Component {
	constructor(props) {
		super(props);
		
		global.SongList=this;
		AsyncUtils.getAll();
		
		const ds = new ListView.DataSource({
        rowHasChanged: (r1, r2) => true,
        sectionHeaderHasChanged: (s1, s2) => true
		});
		this.state={
			data: ds.cloneWithRowsAndSections(this.convertSongsArrayToMap()),
			};
			
		this.uid=global.firebaseApp.auth().currentUser.uid;
		this.isPremium=global.firebaseApp.database().ref().child('users')
			.child(this.uid).child('premiumUser').on('value', (snap) => {
														this.isPremium = snap.val();
        });
		
		
	}
	static navigationOptions = {
    title: 'Spotify2',
	}
	
	convertSongsArrayToMap= function() {
		var songMap = {};
		global.songs.forEach(function(song) {
		
			songMap[song.fireKey] = [];
		
		
			songMap[song.fireKey].push(song);
     
	});
  
  return songMap;
  
}
	
	editSong(sid){
		this.props.navigation.navigate('Details', {'id': sid});
	}
	
	proposeSong(){
		this.props.navigation.navigate('Propose');	
	}
	
	updateList(){
		this.setState({data:this.state.data.cloneWithRows(global.songs)});
	}
	
	
	renderItem = (item) => {
    return (
      <Button style={Styles.row} onPress={()=>this.editSong(item.id)}
		title={item.title+' - '+item.artist+' - '+global.genres[Number(item.genre)]}>
        {item.artist} - {item.artist} - {global.genres[item.genre]}
      </Button>
    )
  }
	
  render() {
    return (
	<View style={Styles.container}>
      <ListView
        style={Styles.container}
        dataSource={this.state.data}
        renderRow={this.renderItem}
      />
	  <Button style={Styles.row} onPress={()=>{if (this.isPremium) this.proposeSong()}}
		title='Propose A Song'>
      </Button>
	 </View>
    );
  }
}