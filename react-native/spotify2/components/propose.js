import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import AsyncUtils from "./asyncutils";
import Styles from "./styles";

export default class Propose extends React.Component {
	
	constructor(props){
		super(props);
		this.state={title: '', artist: '', genre:0, fireKey:''}
		this.ref = global.firebaseApp.database().ref().child('songs');
		
		
	}
	
	static navigationOptions = {
    title: 'Propose A Song',
	}
	
	proposeSong(){
		var maxid=0;
		var i;
		for (i=0;i<global.songs.length;i++)
			if (global.songs[i].id>maxid)
				maxid=global.songs[i].id;
		maxid+=1;
		global.songs.push({id:maxid, title:this.state.title, artist: this.state.artist, genre:this.state.genre});
		
		AsyncUtils.add({id:maxid, title:this.state.title, artist: this.state.artist, genre:this.state.genre});
		
		global.SongList.updateList();
		
		this.ref.push({
                        id: maxid,
                        title: this.state.title,
                        artist: this.state.artist,
						genre: this.state.genre,
                    }).then(() => {});
					
		
		body=this.state.title+' - '+this.state.artist;
		Communications.email(['badila.mihaialexandru@gmail.com'],null , null, 'Propose Song', body);
		
		this.props.navigation.goBack();
	}
	
	render() {
		
	let genreItems = global.genres.map( (s,i) => {
            return <Picker.Item key={i} value={i} label={s} />
        });
		
    return (
	<View>
		<TextInput
			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
			onChangeText={(title) => this.setState({title})}
			placeholder='Title'>
		</TextInput>
		<TextInput
			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
			onChangeText={(artist) => this.setState({artist})}
			placeholder='Artist / Band'>
		</TextInput>
		<Picker
			selectedValue={this.state.genre}
			onValueChange={ (genre) => ( this.setState({genre}) ) } >

			{genreItems}

		</Picker>
		<Button style={Styles.row} onPress={()=>this.proposeSong()} title='Submit' />
	</View>
    );
	}
}