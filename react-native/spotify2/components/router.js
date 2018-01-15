import { StackNavigator } from 'react-navigation';
import Details from "./details";
import SongList from "./list";
import Propose from "./propose";
import Login from "./login";
import Register from "./register";

const Stack = StackNavigator({
	LogIn:{
		screen: Login,
	},
	Register:{
		screen: Register,
	},
	Home: {
		screen: SongList,
	},
	Details: {
		screen: Details,
		path: 'details/:id',
	},
	Propose: {
		screen: Propose,
	},
});

export default Stack;