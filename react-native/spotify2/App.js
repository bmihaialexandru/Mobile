import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';


global.genres=['Rock', 'Rap', 'Techno']

global.songs=[];

class AsyncUtils{
	static async getAll(){
		await AsyncStorage.getAllKeys((error, keys) => {
			for (let i = 0; i < keys.length; i++) {
				AsyncStorage.getItem(keys[i], (error, result) => {
					global.songs.push(JSON.parse(result));
					global.SongList.updateList();
				});
			}
			
		 });
		 
	}
	
	static async add(song){
		await AsyncStorage.setItem(song.id.toString(), JSON.stringify(song), (error) => {});
		
	}
	
	static async remove(id){
		await AsyncStorage.removeItem(id, (error) => {});
		
	}
	
	static async update(song){
		await AsyncStorage.setItem(song.id.toString(), JSON.stringify(song), (error) => {});
		
	}
}

const extractKey=({id}) => id

class App extends React.Component {
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
	}
	static navigationOptions = {
    title: 'Spotify2',
	}
	
	convertSongsArrayToMap= function() {
		var songMap = {};
		global.songs.forEach(function(song) {
		
			songMap[song.id] = [];
		
		
			songMap[song.id].push(song);
     
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
      <Button style={styles.row} onPress={()=>this.editSong(item.id)}
		title={item.title+' - '+item.artist+' - '+global.genres[item.genre]}>
        {item.artist} - {item.artist} - {global.genres[item.genre]}
      </Button>
    )
  }
	
  render() {
    return (
	<View style={styles.container}>
      <ListView
        style={styles.container}
        dataSource={this.state.data}
        renderRow={this.renderItem}
      />
	  <Button style={styles.row} onPress={()=>this.proposeSong()}
		title='Propose A Song'>
      </Button>
	 </View>
    );
  }
}

class Details extends React.Component {
	constructor(props) {
		super(props);
		this.state={title: '', artist: '', genre:0}
		
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
		<Button style={styles.row} onPress={()=>this.saveSong()} title='Save' />
		<Button style={styles.row} onPress={()=>this.deleteSong()} title='Delete' />
		
		<Pie
            data={data}
          options={options}
          accessorKey="noSongs"
          />
	</View>
    );
  }
}

class Propose extends React.Component {
	
	constructor(props){
		super(props);
		this.state={title: '', artist: '', genre:0}
		
		
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
		<Button style={styles.row} onPress={()=>this.proposeSong()} title='Submit' />
	</View>
    );
	}
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
	padding: 50,
  },
  row: {
    padding: 15,
    marginBottom: 5,
	marginTop: 20,
    backgroundColor: 'skyblue',
  },
});

const stack = StackNavigator({
  Home: {
    screen: App,
  },
  Details: {
    screen: Details,
	path: 'details/:id',
  },
  Propose: {
	  screen: Propose,
  },
});

export default stack;