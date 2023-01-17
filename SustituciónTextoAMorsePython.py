import pickle
from os import remove
# Mediante una función incorporamos las variables para mostrar el nombre del menú.

def mostrar_menu(nombre, nombre2, opciones):
    print(f' {nombre}\n{nombre2}\n')
    for clave in sorted(opciones):
        print(f'    {clave}) {opciones[clave][0]}')

# Creamos una función que lea la opción introducida por el usuario, si no es correcta, vuelve a pedirte otra opción.

def leer_opcion(opciones):
    while (a := input("\n{:>40}".format('Opción: '))) not in opciones:
        print('Opción incorrecta, vuelva a intentarlo.')
    return a

# Creamos una función que ejecute la opción introducida por el usuario.

def ejecutar_opcion(opcion, opciones, frasenormal, frasemayus):
    opciones[opcion][1](frasenormal, frasemayus)

# Creamos una función que mande sus variables a diferentes funciones.

def generar_menu(nombre, nombre2, opciones, salir, frasenormal, frasemayus):
    opcion = None
    while opcion != salir:
        mostrar_menu(nombre, nombre2, opciones)
        opcion = leer_opcion(opciones)
        ejecutar_opcion(opcion, opciones, frasenormal, frasemayus)
        print()

# Creamos una función que nos genere las diferentes opciones y el nombre de "Menu de Opciones" con su "=======" debajo.

def menu_principal(frasenormal, frasemayus):
    # Le decimos a una función que dependiendo de la opción (opciones) escogida nos ejecute una función (funcionN) u otra.

    opciones = {
        '1': ('Envío con precio normal', funcion1),
        '2': ('Envío con precio reducido', funcion2),
        '3': ('Envío barato en código Morse', funcion3),
        '4': ('Salir', salir)
    }
    # Indicamos el nombre del menú

    generar_menu("\n{:>50}".format('\033[1m' + 'Menú de Opciones' + '\033[0m'), "{:>50}".format('\033[1m' + '================' + '\033[0m'), opciones, '4', frasenormal, frasemayus)
    return

# Definimos la función de la opción 1.

def funcion1(frasenormal, frasemayus):
    stopfrase = frasemayus.replace(".", " STOP")
    if frasemayus[-1] == '.':
        stopfrase = stopfrase.__add__('STOP')
    espaciofrase = frasemayus.replace(".", " ")
    palabras = espaciofrase.split()
    npalabras = len(palabras)
    palabrasg = 0
    palabrasc = 0
    suma = 0
    for i in palabras:
        if len(i) > 5:
            suma += 0.5
            palabrasg += 1
        else:
            suma += 0.25
            palabrasc += 1
    print(f"\nLa cadena contiene {npalabras} palabras, de las cuales {palabrasg} tienen más de 5 letras.\nPor tanto, al precio de 0,25€/palabra tenemos {palabrasc} y a 0.50€/palabra hay otras {palabrasg}.")
    print(f"Precio Total: {suma}€\n")
    print(f'Mensaje enviado: \n{stopfrase}')


# Definimos la función de la opción 2.

def funcion2(frasenormal, frasemayus):

    #Sustituimos los . por stops y por espacios para tener 2 frases distintas.

    stopfrase = frasemayus.replace(".", " STOP")
    espaciofrase = frasemayus.replace(".", " ")

    #Spliteamos las dos frases para usarlas.

    espaciofrase = espaciofrase.split()
    numpalabras = len(espaciofrase)
    listapalabras = stopfrase.split()
    palabrasg = 0
    suma = 0
    frasefinal = ""

    #Creamos un loop para cada palabra en la lista.

    for cadena in listapalabras:

        # Ponemos la condición de que sea distinto a STOP y mayor a 5.

        if cadena != "STOP":
            suma += 0.25
        if len(cadena) > 5:
            palabrasg += 1
            frasefinal += " "+cadena[0:5]+"@"
        else:
            frasefinal += " "+cadena

    # Añadimos un STOP al final de la frase.

    frasefinal = frasefinal.__add__('STOP')
    print(f"\n La cadena contiene {numpalabras} palabras, de las cuales {palabrasg} tenían más de 5 letras.\n Por tanto, el mensaje se envía al precio 0,25€/palabra.")
    print(f" Precio Total: {suma}€\n")
    print(f' Mensaje enviado: \n{frasefinal}')

# Definimos la función de la opción 3.
def funcion3(frasenormal, frasemayus):

    # Creamos un diccionario morse.

    codigo_morse = {'A':'.-','B':'-...','C':'-.-.','D':'-..','E':'.','F':'..-.','G':'--.','H':'....','I':'..','J':'.---','K':'-.-','L':'.-..','M':'--','N':'-.','O':'---','P':'.--.','Q':'--.-','R':'.-.',
    'S':'...','T':'-','U':'..-','V':'...-','W':'.--','X':'-..-','Y':'-.--','Z':'--..','.':'.-.-.-','0':'-----','1':'.----','2':'..---','3':'...--','4':'....-','5':'.....','6':'-....',
    '7':'--...','8':'---..','9':'----.'}

    # Convertimos la frase en lista.

    listafrase = list(frasemayus)

    # Creamos un loop que nos recorra cada caracter de cada palabra dentro de la lista.

    for palabra,caracter in enumerate(listafrase):

        # Ponemos la condición de si el carácter no es un espacio y está dentro del diccionario lo sustituya.

        if caracter != " " and caracter in codigo_morse:
            listafrase[palabra] = codigo_morse[caracter]+" "

        # Si es un espacio que lo sustituya por la barra.

        elif caracter == " ":
            listafrase[palabra] = "/"

        # Si no sabemos lo que es, lo  sustituya por ???.

        else:
            listafrase[palabra] = "???"
    frasemorse = "".join(listafrase)
    rayas = 0
    puntos = 0
    preciopunto = 0
    precioraya = 0

    # Creamos un loop que recorra los puntos y raya de la frase en morse

    for caracter in frasemorse:
        if caracter == ".":
            puntos += 1
            preciopunto += 0.005
        elif caracter == "-":
            rayas += 1
            precioraya += 0.01

    # Sumamos los diferentes precios de los puntos y rayas

    precio = precioraya + preciopunto
    print(f'La cadena convertida a código Morse tiene {puntos} puntos (0.005€/punto) y {rayas} rayas\n(0.01€/raya)'
          f'\nPor tanto, el mensaje se envía al precio de {precio:.2f}€ ({preciopunto:.2f}€ y {precioraya:.2f}€)')
    print(f'\nMensaje enviado:\n{frasemorse[0:100]}\n{frasemorse[100:]}')
# Definimos la función de la opción salir.
def salir(frasenormal,frasemayus):
    print('Saliendo')


if __name__ == '__main__':
    print("\n{:>65}".format("\033[4m" + "\033[1m" + "PROGRAMA OPTIMIZACIÓN TELEGRAMA" + "\033[0m" + "\033[0m"))

    # Definimos la clase colores con las siguientes variables para la estética del programa.
    class colores:
        rojo = '\033[91m'
        verde = '\033[92m'
        amarillo = '\033[2;30;47m'
        azul = '\033[96m'
        reset = '\033[0m'

    frasenormal = sys.argv[1:]
    frasenormal = ' '.join(frasenormal)
    frasemayus = frasenormal.upper()
    menu_principal(frasenormal, frasemayus)