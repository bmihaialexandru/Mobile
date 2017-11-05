import React from 'react';
import { TextInput, FlatList, StyleSheet, Text, View, Button } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';

let songs=[
	{id:0, title:'a', artist:'a'},
	{id:1, title:'b', artist:'b'},
	{id:2, title:'c', artist:'c'},
]

const extractKey=({id}) => id

class App extends React.Component {
	
	static navigationOptions = {
    title: 'Spotify2',
	}
	
	editSong(sid){
		this.props.navigation.navigate('Details', {'id': sid});
	}
	
	proposeSong(){
		this.props.navigation.navigate('Propose');	
	}
	
	renderItem = ({item}) => {
    return (
      <Button style={styles.row} onPress={()=>this.editSong(item.id)}
		title={item.title+' - '+item.artist}>
        {item.title} - {item.artist}
      </Button>
    )
  }
	
  render() {
    return (
	<View style={styles.container}>
      <FlatList
        style={styles.container}
        data={songs}
        renderItem={this.renderItem}
        keyExtractor={extractKey}
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
		this.state={title: '', artist: ''}
		
		for(i=0;i<songs.length;i++){
			if(this.props.navigation.state.params.id==songs[i].id){
				this.state.title=songs[i].title;
				this.state.artist=songs[i].artist;
			}
		}
	}
	
	static navigationOptions = {
    title: 'Details',
	}
	
	saveSong(){
		for(i=0;i<songs.length;i++){
			if(this.props.navigation.state.params.id==songs[i].id){
				songs[i].title=this.state.title;
				songs[i].artist=this.state.artist;
			}
		}
		this.props.navigation.goBack();
	}
	
	render() {
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
		<Button style={styles.row} onPress={()=>this.saveSong()}
		title='Save' />
	</View>
    );
  }
}

class Propose extends React.Component {
	
	constructor(props){
		super(props);
		this.state={title: '', artist: ''}
		
		
	}
	
	static navigationOptions = {
    title: 'Propose A Song',
	}
	
	proposeSong(){
		body=this.state.title+' - '+this.state.artist;
		Communications.email(['badila.mihaialexandru@gmail.com'],null , null, 'Propose Song', body);
		
		this.props.navigation.goBack();
	}
	
	render() {
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
		<Button style={styles.row} onPress={()=>this.proposeSong()}
		title='Submit' />
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