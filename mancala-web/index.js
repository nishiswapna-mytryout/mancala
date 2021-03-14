var gameid = "";
var player_id_1 = "";
var player_id_2 = "";
var player_turn = "";
var active = false;

var base_url = "http://localhost:8099/mancala/";
var player_url = "player"
var start_url = "game";
var sow_url = "game/";
var status = "game/status/";

var pit_number_mapping = JSON.parse('{"B1": "00", "B2": "01", "B3": "02", "B4": "03", "B5": "04", "B6": "05", "BL": "06", "A1": "07", "A2": "08", "A3": "09", "A4": "10", "A5": "11", "A6": "12", "AL": "13"}');
var number_pit_mapping = JSON.parse('{"00": "B1", "01": "B2", "02": "B3", "03": "B4", "04": "B5", "05": "B6", "06": "BL", "07": "A1", "08": "A2", "09": "A3", "10": "A4", "11": "A5", "12": "A6", "13": "AL"}');

var token_player_mapping = {};

function start_game() {
	console.log("start game");
	player_id_1_first = document.getElementById("player_id_1_first").value;
	player_id_2_first = document.getElementById("player_id_2_first").value;

	player_id_1_last = document.getElementById("player_id_1_last").value;
	player_id_2_last = document.getElementById("player_id_2_last").value;

	create_player(player_id_1_first,player_id_1_last);
	create_player(player_id_2_first,player_id_2_last);

	call_start_api(player_id_1, player_id_2);
}

function create_player(player_first_name, player_last_name) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === XMLHttpRequest.DONE && this.status == 201) {
			token_player_mapping[JSON.parse(this.responseText).playerId]=player_first_name;
		} else {
			document.getElementById("player_turn").innerHTML = "unable to create player";
		}
	}
	var url = base_url + player_url;
	console.log(url);
	var data = {};
	data.firstName = player_first_name;
	data.lastName = player_last_name;
	var json = JSON.stringify(data);
	xhttp.open("POST", url, false);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send(json);

}

function call_start_api(player_id_1, player_id_2) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
			process_active_game_response(this.responseText, true);
			register_event_handler_on_cell();
			active = true;
		} else {
			document.getElementById("player_turn").innerHTML = "unable to start the game";
		}
	};
	var url = base_url + start_url;
	console.log(url);
	var data = {};
	var tokens = Object.keys(token_player_mapping);
	data.playerIdA = tokens[0];
	data.playerIdB = tokens[1];
	var json = JSON.stringify(data);
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-Type", "application/json");
	xhttp.send(json);
}

function process_active_game_response(response, render) {
	console.log(response);
	json = JSON.parse(response);
	gameid = json.gameId;
	player_turn = json.playerIdTurn;
	if (render == true) {
		render_board(json.pitState);
	}
}

function process_game_status_response(response) {
	console.log(response);
	json = JSON.parse(response);
	render_board(json.pitState);
	if (json.hasGameEnded == true) {
		var playerScores = json.playerScores;
		var player_1_score = playerScores[0];
		var msg = "" + token_player_mapping[playerScores[0].playerId] + ": " + playerScores[0].bigPitStoneCount + "---" + token_player_mapping[playerScores[1].playerId] + ": " + playerScores[1].bigPitStoneCount;
		document.getElementById("player_turn").innerHTML = msg;
		deregister_event_handler_on_cell();
		console.log("end game");
	}
}

function render_board(board_json) {
	var len = Object.keys(number_pit_mapping).length;
	var i;
	for (i = 0; i < len; i++) {
		var pit = board_json[i].pitPosition;
		document.getElementById("cell-" + pit_number_mapping[pit]).innerHTML = board_json[i].currentStoneCount;
	}
	document.getElementById("player_turn").innerHTML = "Player " + token_player_mapping[player_turn] + " turn";
}

function register_event_handler_on_cell() {
	var len = Object.keys(number_pit_mapping).length;
	var keys = Object.keys(number_pit_mapping);
	for (i = 0; i < len; i++) {
		document.getElementById("cell-" + keys[i]).addEventListener("click", function () {
			on_click_handler();
		});
	}
}

function deregister_event_handler_on_cell() {
	var len = Object.keys(number_pit_mapping).length;
	var keys = Object.keys(number_pit_mapping);
	for (i = 0; i < len; i++) {
		document.getElementById("cell-" + keys[i]).removeEventListener("click", function () {
			on_click_handler();
		});
	}
}

function on_click_handler() {
	if (active === true) {
		active = false;
		sow();
		status_check();
		active = true;
	}
}

function sow() {
	var pit_location = event.target.id.substring(5);
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState == 4 && this.status == 200) {
			process_active_game_response(this.responseText, false);
		} else {
			document.getElementById("player_turn").innerHTML = "sow api error";
		}
	};
	var url = base_url + sow_url + gameid + "/pits/" + number_pit_mapping[pit_location] + "/player/" + player_turn;
	console.log(url);
	xhttp.open("GET", url, true);
	xhttp.send();
}


function status_check() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState == 4 && this.status == 200) {
			process_game_status_response(this.responseText);
		} else {
			document.getElementById("player_turn").innerHTML = "status api error";
		}
	}
	var url = base_url + status + gameid;
	console.log(url);
	xhttp.open("GET", url, true);
	xhttp.send();
}

function init() {
	document.getElementById("player_turn").innerHTML = "Please provide user names and start.";
	document.getElementById("start_button").addEventListener("click", function () {
		start_game();
	});
}

init();