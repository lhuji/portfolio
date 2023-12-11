// Obtener el elemento canvas y su contexto
var lienzo = document.getElementById("miCanvas");
var contexto = lienzo.getContext("2d");

// Definir las variables de la bola
var radioBola = 10;
var xBola = lienzo.width / 2;
var yBola = lienzo.height - 30;
var velocidadXBola = 2;
var velocidadYBola = -2;

// Definir las variables de la raqueta
var alturaRaqueta = 10;
var anchoRaqueta = 75;
var xRaqueta = (lienzo.width - anchoRaqueta) / 2;
var derechaPresionada = false;
var izquierdaPresionada = false;

// Definir la velocidad inicial de la bola
var velocidadInicialXBola = 2;
var velocidadInicialYBola = -2;
var velocidadXBola = velocidadInicialXBola;
var velocidadYBola = velocidadInicialYBola;

// Definir las variables de los ladrillos
var filasLadrillos = 3;
var columnasLadrillos = 7; // Cambiando la cantidad de columnas de ladrillos a 7
var paddingLadrillo = 10;
var margenSuperiorLadrillos = 30;
var margenIzquierdoLadrillos = 30;

// Calcular el ancho del ladrillo después de definir margenIzquierdoLadrillos y paddingLadrillo
var anchoLadrillo = (lienzo.width - 2 * margenIzquierdoLadrillos - (columnasLadrillos - 1) * paddingLadrillo) / columnasLadrillos;
var alturaLadrillo = 20;

// Puntuación y vidas
var puntuacion = 0;
var vidas = 3;

// Crear matriz para almacenar los ladrillos
var ladrillos = [];
for (var columna = 0; columna < columnasLadrillos; columna++) {
  ladrillos[columna] = [];
  for (var fila = 0; fila < filasLadrillos; fila++) {
    var xLadrillo = columna * (anchoLadrillo + paddingLadrillo) + margenIzquierdoLadrillos;
    var yLadrillo = fila * (alturaLadrillo + paddingLadrillo) + margenSuperiorLadrillos;
    ladrillos[columna][fila] = { x: xLadrillo, y: yLadrillo, estado: 1 };
  }
}

// Detectar eventos de teclado y ratón
document.addEventListener("keydown", presionarTecla, false);
document.addEventListener("keyup", soltarTecla, false);
document.addEventListener("mousemove", moverRaqueta, false);

// Función para manejar la pulsación de teclas
function presionarTecla(evento) {
    if (evento.key == "Right" || evento.key == "ArrowRight") {
        derechaPresionada = true;
    } else if (evento.key == "Left" || evento.key == "ArrowLeft") {
        izquierdaPresionada = true;
    }
}

// Función para manejar la liberación de teclas
function soltarTecla(evento) {
    if (evento.key == "Right" || evento.key == "ArrowRight") {
        derechaPresionada = false;
    } else if (evento.key == "Left" || evento.key == "ArrowLeft") {
        izquierdaPresionada = false;
    }
}

// Función para mover la raqueta con el ratón
function moverRaqueta(evento) {
  var posicionRatonEnCanvas = evento.clientX - lienzo.offsetLeft;
  if (posicionRatonEnCanvas > 0 && posicionRatonEnCanvas < lienzo.width) {
    xRaqueta = posicionRatonEnCanvas - anchoRaqueta / 2;
  }
}

// Declarar una variable booleana para el seguimiento de colisiones
var colisionDetectada = false;

// Declarar una variable para el color de la pelota
var colorPelota = "red"; // Inicialmente, establece un color fijo

// Función para detectar colisiones y actualizar la velocidad de la bola
function detectarColision() {
  for (var columna = 0; columna < columnasLadrillos; columna++) {
    for (var fila = 0; fila < filasLadrillos; fila++) {
      var ladrillo = ladrillos[columna][fila];
      if (ladrillo.estado == 1) {
        if (
          xBola > ladrillo.x &&
          xBola < ladrillo.x + anchoLadrillo &&
          yBola > ladrillo.y &&
          yBola < ladrillo.y + alturaLadrillo
        ) {
          velocidadYBola = -velocidadYBola;
          ladrillo.estado = 0; // Marcar el ladrillo como golpeado
          puntuacion++;

          // Cambiar el color de la pelota al chocar con un ladrillo
          var colorR = Math.floor(Math.random() * 256);
          var colorG = Math.floor(Math.random() * 256);
          var colorB = Math.floor(Math.random() * 256);
          colorPelota = "rgb(" + colorR + "," + colorG + "," + colorB + ")";

          // Aumentar la velocidad de la bola después de cada rebote
          velocidadXBola *= 1.1; // Aumenta la velocidad en un 10%
          velocidadYBola *= 1.1; // Aumenta la velocidad en un 10%

          if (puntuacion == filasLadrillos * columnasLadrillos) {
            alert("¡GANASTE, FELICITACIONES!");
            document.location.reload();
          }
        }
      }
    }
  }
}

function dibujarBola() {
  contexto.beginPath();
  contexto.arc(xBola, yBola, radioBola, 0, Math.PI * 2);
  contexto.fillStyle = colorPelota; // Utiliza el color almacenado en la variable
  contexto.fill();
  contexto.closePath();
}

// Función para dibujar la raqueta
function dibujarRaqueta() {
  contexto.beginPath();
  contexto.rect(xRaqueta, lienzo.height - alturaRaqueta, anchoRaqueta, alturaRaqueta);
  contexto.fillStyle = "#0095DD";
  contexto.fill();
  contexto.closePath();
}
function dibujarLadrillos() {
  for (var columna = 0; columna < columnasLadrillos; columna++) {
    for (var fila = 0; fila < filasLadrillos; fila++) {
      if (ladrillos[columna][fila].estado == 1) {
        var xLadrillo = columna * (anchoLadrillo + paddingLadrillo) + margenIzquierdoLadrillos;
        var yLadrillo = fila * (alturaLadrillo + paddingLadrillo) + margenSuperiorLadrillos;
        
        if (!isNaN(xLadrillo) && !isNaN(yLadrillo) && !isNaN(anchoLadrillo) && !isNaN(alturaLadrillo)) {
          var gradiente = contexto.createLinearGradient(xLadrillo, yLadrillo, xLadrillo + anchoLadrillo, yLadrillo);
          gradiente.addColorStop(0, "cyan");
          gradiente.addColorStop(1, "white");
          contexto.fillStyle = gradiente;
          contexto.fillRect(xLadrillo, yLadrillo, anchoLadrillo, alturaLadrillo);
        }
      }
    }
  }
}
// Función para dibujar la puntuación
function dibujarPuntuacion() {
  contexto.font = "16px Arial";
  contexto.fillStyle = "#0095DD";
  contexto.fillText("Marcador: " + puntuacion, 8, 20);
}

// Función para ponerle el título
function dibujarTítulo() {
  contexto.font = "16px Arial";
  contexto.fillStyle = "#FFFFFF";
  contexto.fillText("DESTROZANDO LADRILLOS" ,135, 20);
}
// Función para dibujar las vidas restantes
function dibujarVidas() {
  contexto.font = "16px Arial";
  contexto.fillStyle = "#0095DD";
  contexto.fillText("Vidas: " + vidas, lienzo.width - 65, 20);
}

// Función principal de dibujo y animación
function dibujar() {
  contexto.clearRect(0, 0, lienzo.width, lienzo.height);
  dibujarLadrillos();

  // Cambiar el color de la pelota al tocar la pared vertical
  if (xBola + velocidadXBola > lienzo.width - radioBola || xBola + velocidadXBola < radioBola) {
    var colorR = Math.floor(Math.random() * 256);
    var colorG = Math.floor(Math.random() * 256);
    var colorB = Math.floor(Math.random() * 256);
    colorPelota = "rgb(" + colorR + "," + colorG + "," + colorB + ")";
    velocidadXBola = -velocidadXBola; // Invertir la dirección
  }
  
  // Cambiar el color de la pelota al tocar la pared superior
  if (yBola + velocidadYBola < radioBola) {
    var colorR = Math.floor(Math.random() * 256);
    var colorG = Math.floor(Math.random() * 256);
    var colorB = Math.floor(Math.random() * 256);
    colorPelota = "rgb(" + colorR + "," + colorG + "," + colorB + ")";
    velocidadYBola = -velocidadYBola; // Invertir la dirección vertical
  }
  
  // Cambiar el color de la pelota al tocar la raqueta
  if (yBola + velocidadYBola > lienzo.height - radioBola) {
    if (xBola > xRaqueta && xBola < xRaqueta + anchoRaqueta) {
      var colorR = Math.floor(Math.random() * 256);
      var colorG = Math.floor(Math.random() * 256);
      var colorB = Math.floor(Math.random() * 256);
      colorPelota = "rgb(" + colorR + "," + colorG + "," + colorB + ")";
      velocidadYBola = -velocidadYBola; // Invertir la dirección
    } else {
      vidas--;
      if (!vidas) {
        alert("¡JUEGO TERMINADO!");
        document.location.reload();
      } else {
        xBola = lienzo.width / 2;
        yBola = lienzo.height - 30;
        velocidadInicialXBola += 0.5;
        velocidadInicialYBola += 0.5;
        velocidadXBola = velocidadInicialXBola;
        velocidadYBola = velocidadInicialYBola;
        xRaqueta = (lienzo.width - anchoRaqueta) / 2;
      }
    }
  }

  dibujarBola();
  dibujarRaqueta();
  dibujarPuntuacion();
  dibujarVidas();
  dibujarTítulo();

  // Mover la bola
  xBola += velocidadXBola;
  yBola += velocidadYBola;

  // Detectar colisiones después de mover la bola
  detectarColision();

  if (derechaPresionada && xRaqueta < lienzo.width - anchoRaqueta) {
    xRaqueta += 7;
  } else if (izquierdaPresionada && xRaqueta > 0) {
    xRaqueta -= 7;
  }

  requestAnimationFrame(dibujar);
}
dibujar(); // Iniciar la animación
