var imagenes = ['./img/1.png','./img/2.png','./img/3.png'];
document.getElementById("asd").src = imagenes[2];

function getRandomInt(nimagenes){
    return Math.floor(Math.random() * nimagenes);
}

var n = getRandomInt(3);

function cambio(){
    n++;
    document.getElementById("asd").src=imagenes[n%imagenes.length]
}

onload = function(){
    cambio();
    setInterval(cambio,5000);
    setInterval(pregunta,15000)
}
var na = document.getElementsByTagName("a")

var nta = na.length

function pregunta(){
    var frase = prompt(`¿Cuantos enlaces crees que tiene esta página?`);
        if (frase==nta)
        alert(`Muy bien, has acertado tiene ${nta} enlaces`);

        else if (frase==null)
        alert(`Vale, ya lo pillo. No te apetece contestar... Otra vez será...`);

        else if (frase!=nta)
        alert(`Ups... me temo que te has equivocado hay ${nta} enlaces. Más suerte la próxima vez...`);
}

