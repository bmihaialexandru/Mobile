import React from 'react';
import { TextInput, ListView, StyleSheet, Text, View, Button, Picker, Alert, AsyncStorage } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';
import { Pie } from 'react-native-pathjs-charts';
import AsyncUtils from "./asyncutils";
import Stack from "./router";
import Styles from "./styles";

export default class Login extends React.Component {
	constructor(props) {
		super(props);
		
		this.state={userName: '', password: ''};
		
	}
	static navigationOptions = {
    title: 'Log In',
	}

	logIn(){
		global.firebaseApp.auth()
			.signInWithEmailAndPassword(this.state.userName, this.state.password)
			.then();
		this.props.navigation.navigate('Home');
	}
	
	register(){
		this.props.navigation.navigate('Register');
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
			<Button style={Styles.row} onPress={()=>this.logIn()} title='Submit' />
			<Button style={Styles.row} onPress={()=>this.register()	} title='Register' />
		</View>
		);
	}
}