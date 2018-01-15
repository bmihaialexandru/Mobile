import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import AsyncUtils from "./asyncutils";
import Stack from "./router";
import Styles from "./styles";

export default class Register extends React.Component {
	constructor(props) {
		super(props);
		
		this.state={userName: '', password: ''};
		
	}
	static navigationOptions = {
    title: 'Register',
	}

	register(){
		console.log('here');
		global.firebaseApp.auth()
			.createUserWithEmailAndPassword(this.state.userName, this.state.password).catch(function (error) {});
		console.log('there');
		this.props.navigation.goBack();
	}
	
	render() {
		
		return (
		<View>
			<TextInput
				style={{height: 40, borderColor: 'gray', borderWidth: 1}}
				onChangeText={(userName) => this.setState({userName})}
				placeholder='UserName'>
			</TextInput>
			<TextInput
				style={{height: 40, borderColor: 'gray', borderWidth: 1}}
				onChangeText={(password) => this.setState({password})}
				placeholder='Password'>
			</TextInput>
			<Button style={Styles.row} onPress={()=>this.register() } title='Submit' />
		</View>
		);
	}
}