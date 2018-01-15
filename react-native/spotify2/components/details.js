import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import Styles from "./styles";
import AsyncUtils from "./asyncutils";

export default class Details extends React.Component {
	constructor(props) {
		super(props);
		this.state={title: '', artist: '', genre:0}
		
		this.ref = global.firebaseApp.database().ref().child('songs');
		
		for(i=0;i<global.songs.length;i++){
			if(this.props.navigation.state.params.id==global.songs[i].id){
				this.state.title=global.songs[i].title;
				this.state.artist=global.songs[i].artist;
				this.state.genre=global.songs[i].genre;
			}
		}
	}
	
	static navigationOptions = {
    title: 'Details',
	}
	
	saveSong(){
		for(i=0;i<global.songs.length;i++){
			if(this.props.navigation.state.params.id==global.songs[i].id){
				global.songs[i].title=this.state.title;
				global.songs[i].artist=this.state.artist;
				global.songs[i].genre=this.state.genre;
				console.log(global.songs[i].artist);
				
				this.ref.child(global.songs[i].fireKey).set({
                        id: global.songs[i].id,
                        title: global.songs[i].title,
                        artist: global.songs[i].artist,
						genre: global.songs[i].genre,
						fireKey: global.songs[i].fireKey,});
			}
		}
		
		
		AsyncUtils.update({id:this.props.navigation.state.params.id, title:this.state.title, artist: this.state.artist, genre:this.state.genre});
		
		global.SongList.updateList();
		this.props.navigation.goBack();
	}
	
	deleteSong(){
		Alert.alert( 'Delete', 'Do you want to delete this song?', [ {text: 'Cancel', onPress: () =>{} }, {text: 'Delete', onPress: () => this.delete_confirmed()}, ], { cancelable: false } )
	}
	
	delete_confirmed(){
		for(i=0;i<global.songs.length;i++){
			if(this.props.navigation.state.params.id==global.songs[i].id){
				this.ref.child(global.songs[i].fireKey).remove();
				
				global.songs.splice(i,1);
				break;
			}
		}
		
		AsyncUtils.remove(this.props.navigation.state.params.id);
		
		global.SongList.updateList();
		this.props.navigation.goBack();
	}
	
	render() {
	var i=0;
	var cnt=0;
	for (i=0;i<global.songs.length;i++){
		if(this.state.artist==global.songs[i].artist){
			cnt+=1;
		}
	}
		
	let data = [{
      "name": this.state.artist,
      "noSongs": cnt
    }, {
      "name": "Others",
      "noSongs": global.songs.length-cnt
    }]

    let options = {
      margin: {
        top: 20,
        left: 20,
        right: 20,
        bottom: 20
      },
      width: 350,
      height: 350,
      color: '#2980B9',
      r: 50,
      R: 150,
      legendPosition: 'topLeft',
      animate: {
        type: 'oneByOne',
        duration: 200,
        fillTransition: 3
      },
      label: {
        fontFamily: 'Arial',
        fontSize: 12,
        fontWeight: true,
        color: '#ECF0F1'
      }
    }
	
	let genreItems = global.genres.map( (s,i) => {
            return <Picker.Item key={i} value={i} label={s} />
        });
		
    return (
	<View>
		<TextInput
			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
			onChangeText={(title) => this.setState({title})}
			editable={true}
			value={this.state.title}>
		</TextInput>
		<TextInput
			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
			onChangeText={(artist) => this.setState({artist})}
			editable={true}
			value={this.state.artist}>
		</TextInput>
		<Picker
			selectedValue={this.state.genre}
			onValueChange={ (genre) => ( this.setState({genre}) ) } >

			{genreItems}

		</Picker>
		<Button style={Styles.row} onPress={()=>this.saveSong()} title='Save' />
		<Button style={Styles.row} onPress={()=>this.deleteSong()} title='Delete' />
		
		<Pie
            data={data}
          options={options}
          accessorKey="noSongs"
          />
	</View>
    );
  }
}