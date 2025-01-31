
function summonMonster() {
	const resultElement = document.getElementById('result');
	resultElement.innerHTML = "";
    const player1Id = Math.floor(Math.random() * 1020) + 1;
    const player2Id = Math.floor(Math.random() * 1020) + 1;

    Promise.all([fetchPokemon(player1Id), fetchPokemon(player2Id)])
        .then(([player1Pokemon, player2Pokemon]) => {
            displayPokemon(player1Pokemon, 'player1');
            displayPokemon(player2Pokemon, 'player2');
			sessionStorage.setItem('player1Pokemon', JSON.stringify(player1Pokemon));
            sessionStorage.setItem('player2Pokemon', JSON.stringify(player2Pokemon));
            //determineWinner(player1Pokemon, player2Pokemon);
        });
}

async function fetchPokemon(id) {	
    const response = await fetch(`/pokemon?id=${id}`);
	//console.log(response);
    return await response.json();
}

async function calcDamage(t1,t2) {	
    const response = await fetch(`/damage?from=${t1}&to=${t2}`);
	//console.log(await response.json());
    return await response.json();
}

async function getIcon(t1) {	
    const response = await fetch(`/type?type=${t1}`);
	//console.log(response.blob);
    return await response.text();
}

async function displayPokemon(pokemon, player) {
	
    document.getElementById(`${player}-img`).src = pokemon.sprites.front_default;
    document.getElementById(`${player}-name`).textContent = `なまえ: ${pokemon.name}`;
    //document.getElementById(`${player}-height`).textContent = `しんちょう: ${pokemon.height}`;
	document.getElementById(`${player}-hp`).textContent = `たいりょく: ${pokemon.stats[0].base_stat}`;
	document.getElementById(`${player}-attack`).textContent = `こうげきりょく: ${pokemon.stats[1].base_stat}`;
	document.getElementById(`${player}-defence`).textContent = `しゅびりょく: ${pokemon.stats[2].base_stat}`;
    //document.getElementById(`${player}-type`).textContent = `タイプ: ${pokemon.types[0].type.name}`
	document.getElementById(`${player}-type`).innerHTML = `タイプ: <img id ="${player}-type1img" src="` + await getIcon(pokemon.types[0].type.name) +'"></img>'
	//document.getElementById(`${player}-type1img`).src = await getIcon(pokemon.types[0].type.name);
	if(pokemon.types.length>=2){
		//document.getElementById(`${player}-type2`).textContent = `タイプ: ${pokemon.types[1].type.name}`;
		document.getElementById(`${player}-type2`).innerHTML = `タイプ: <img id ="${player}-type2img" src="` + await getIcon(pokemon.types[1].type.name) +'"></img>'
		//document.getElementById(`${player}-type2img`).src = await getIcon(pokemon.types[1].type.name);
	}else{
		document.getElementById(`${player}-type2`).innerHTML =""
	}
}

function startBattle() {
    const player1Pokemon = JSON.parse(sessionStorage.getItem('player1Pokemon'));
    const player2Pokemon = JSON.parse(sessionStorage.getItem('player2Pokemon'));

    if (player1Pokemon && player2Pokemon) {
        determineWinner(player1Pokemon, player2Pokemon);
    } else {
        alert('まずはポケモンを呼んでもらいましょうかね');
    }
}

async function determineWinner(player1Pokemon, player2Pokemon) {
    const resultElement = document.getElementById('result');
	resultElement.innerHTML = "";
	var hp1 = player1Pokemon.stats[0].base_stat;
	var hp2 = player2Pokemon.stats[0].base_stat;
	var at1 = player1Pokemon.stats[1].base_stat;
	var at2 = player2Pokemon.stats[1].base_stat;
	var de1 = player1Pokemon.stats[2].base_stat;
	var de2 = player2Pokemon.stats[2].base_stat;	
	var critical = 1;
	var p1p2 = 1;
	var p2p1 = 1;
	var output = "";
	
	p1p2 = await calcDamage(player1Pokemon.types[0].type.name,player2Pokemon.types[0].type.name)
	if(player2Pokemon.types.length>=2){
		p1p2 *= await calcDamage(player1Pokemon.types[0].type.name,player2Pokemon.types[1].type.name)
	}
	
	p2p1 = await calcDamage(player2Pokemon.types[0].type.name,player1Pokemon.types[0].type.name)
	if(player1Pokemon.types.length>=2){
		p2p1 *= await calcDamage(player2Pokemon.types[0].type.name,player1Pokemon.types[1].type.name)
	}
	//console.log(p1p2)
	//console.log(p2p1)
	while(hp1 > 0 && hp2>0){
		critical = isCritical()
		output = ""
		output += player2Pokemon.name +"のこうげき！<br>"
		if(at2>de1){
			hp1 -= (5 + ((at2 -de1) * p2p1 * critical)) 
		}else{
			hp1 -= 5 * p2p1 * critical
		}
		
		if (critical == 2){
			output  +=　"きゅうしょにあたった！<br>"
		}
		if (p2p1 > 1){
			output  +=　"こうかはばつぐんだ！<br>"
		}else if(p2p1 < 1 && p2p1 > 0){
			output  +=　"こうかはいまひとつのようだ・・・<br>"
		}else if(p2p1 == 0){
			output +=　"こうかはないようだ・・・<br>"
		}
		output  += player1Pokemon.name + "のたいりょく のこり:" + hp1 + '</font><br>'
		
		resultElement.innerHTML  += '<font color="red">' + output
		output = ""
		
		critical = isCritical()
		
		output  +=　player1Pokemon.name +"のこうげき！<br>"
		if(at1>de2){
			hp2 -= 5 + ((at1-de2) * p1p2 * critical)
		}else{
			hp2 -= 5 * p1p2 * critical
		}
		
		if (critical == 2){
			output +=　"きゅうしょにあたった！<br>"
		}
		if (p1p2 > 1){
			output +=　"こうかはばつぐんだ！<br>"
		}else if(p1p2 < 1 && p1p2>0){
			output  +=　"こうかはいまひとつのようだ・・・<br>"
		}else if(p1p2 == 0){
			output  +=　"こうかはないようだ・・・<br>"
		}
		output  +=  player2Pokemon.name + "のたいりょく のこり:" + hp2 + '</font><br>'
		resultElement.innerHTML  += '<font color="blue">' + output
	}
	
	
	
	if (hp1 > hp2) {
        resultElement.innerHTML  += '<font color="blue">' + player1Pokemon.name + 'のかち!';
		
    } else if (hp1 < hp2) {
        resultElement.innerHTML  += '<font color="red">' + player2Pokemon.name + 'のかち!';
    } else {
        resultElement.innerHTML  += '引き分けー';
    }
}

function isCritical() {
    return Math.random() < 0.1 ? 2 : 1;
}