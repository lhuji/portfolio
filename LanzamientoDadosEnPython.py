import pickle
from os import remove


# Creamos una función que mande sus variables a diferentes funciones.

def ejecutardado(dado, resuldado):

    # Definimos las diferentes funciones para cada opción.

    opciones = {
        '1': dado1,
        '2': dado2,
        '3': dado3,
        '4': dado4,
        '5': dado5,
        '6': dado6
    }

    # Le decimos a resuldado que se convierta en cadena.

    resuldado = str(resuldado)

    # Ejecutamos la opción.

    ejecutar_opcion(resuldado,opciones, dado)

# Ejecutamos la función para la opción que haya resultado.
def ejecutar_opcion(resuldado,opciones, dado):
    opciones[resuldado](dado)

# Creamos una función que nos genere un bucle hasta que la tecla pulsada sea 0.
def pregunta():
    d1 = 0
    d2 = 0
    d3 = 0
    d4 = 0
    d5 = 0
    d6 = 0
    # Decimos que dadoslanzados sea un conjunto.
    ntotaldados = 0
    totaldados = []
    dadoslanzados = set()
    ndados = 1
    while ndados != 0:

        # Pedimos que introduzca un número por teclado, si es diferente a un número positivo, saltará un error y
        # pedirá la introducción de un nuevo número.

        ndados = int(input(f"\n{colores.azul}¿Cuantos dados lanzamos?{colores.reset}"))
        if ndados >= 0:
            lanzamientodados(ndados, dadoslanzados, totaldados)
        else:
            print(f"\n{colores.rojo}Lo que has introducido no es un número positivo.{colores.reset}\n")

    # Llamamos a las funciones y nos llevamos cada una de las variables de los dados.
    ntotaldados2, d1, d2, d3, d4, d5, d6 = contar(totaldados, ntotaldados, d1, d2, d3, d4, d5, d6)
    d1, d2, d3, d4, d5, d6 = porcentaje(ntotaldados2, d1, d2, d3, d4, d5, d6)
    salir(dadoslanzados, d1, d2, d3, d4, d5, d6)

# Ejecutamos el lanzamiento de los dados.
def lanzamientodados(ndados, dadoslanzados, totaldados):

    # Le decimos que el valor de ndados sea un entero para hacerle un range.

    ndados= int(ndados)
    dado = 0

    for i in range(ndados):
        if i <= ndados or i == ndados:

            # Pedimos un número aleatorio entre 1 y 6.

            resuldado = random.randint(1, 6)
            dado += 1

            # Ejecutamos la función ejecutardado con el resultado del dado obtenido(resuldado)

            ejecutardado(dado, resuldado)
            i += 1

            # Cambiamos resuldado a cadena de caracteres para poder añadirlo.
            resuldado = str(resuldado)
            dadoslanzados.update(resuldado)
            totaldados.append(int(resuldado))

# Definimos una función que nos cuente el número total de dados y el número de veces que ha salido cada uno.
def contar(totaldados, ntotaldados, d1, d2, d3, d4, d5, d6):

    ntotaldados = len(totaldados)
    d1 = totaldados.count(1)
    d2 = totaldados.count(2)
    d3 = totaldados.count(3)
    d4 = totaldados.count(4)
    d5 = totaldados.count(5)
    d6 = totaldados.count(6)
    return ntotaldados, d1, d2, d3, d4, d5, d6

# Definimos una función que nos calcule el porcentaje de veces que ha salido cada dado.
def porcentaje(ntotaldados2, d1, d2, d3, d4, d5, d6):
    d1 = (d1 * 100 / ntotaldados2)
    d2 = (d2 * 100 / ntotaldados2)
    d3 = (d3 * 100 / ntotaldados2)
    d4 = (d4 * 100 / ntotaldados2)
    d5 = (d5 * 100 / ntotaldados2)
    d6 = (d6 * 100 / ntotaldados2)
    return d1, d2, d3, d4, d5, d6
# Definimos la función si el dado ha sido 1.

def dado1(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}         *         {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función si el dado ha sido 2.
def dado2(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}             *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *             {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función si el dado ha sido 3.
def dado3(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}             *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}         *         {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *             {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función si el dado ha sido 4.
def dado4(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función si el dado ha sido 5.
def dado5(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}         *         {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función si el dado ha sido 6.
def dado6(dado):
    print(f'\n{colores.verde}El dado número {dado} ha generado aleatoriamente un:{colores.reset}\n'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}'
          f'\n{colores.amarillo}     *       *     {colores.reset}'
          f'\n{colores.amarillo}                   {colores.reset}')

# Definimos la función de la opción salir.

def salir(dadoslanzados, d1, d2, d3, d4, d5, d6):
    dadosordenados = sorted(list(dadoslanzados))
    dadosordenados = ", ".join(dadosordenados)
    print(f'Los valores de los dados lanzados fueron: {dadosordenados}')

    print(f'\nEl porcentaje de veces que ha salido el 1 ha sido:{colores.rojo} {"{:.4f}".format(d1)}% {colores.reset}\n'
          f'\nEl porcentaje de veces que ha salido el 2 ha sido:{colores.rojo} {"{:.4f}".format(d2)}% {colores.reset}\n'
          f'\nEl porcentaje de veces que ha salido el 3 ha sido:{colores.rojo} {"{:.4f}".format(d3)}% {colores.reset}\n'
          f'\nEl porcentaje de veces que ha salido el 4 ha sido:{colores.rojo} {"{:.4f}".format(d4)}% {colores.reset}\n'
          f'\nEl porcentaje de veces que ha salido el 5 ha sido:{colores.rojo} {"{:.4f}".format(d5)}% {colores.reset}\n'
          f'\nEl porcentaje de veces que ha salido el 6 ha sido:{colores.rojo} {"{:.4f}".format(d6)}% {colores.reset}\n')
    print(f'\n{colores.rojo}Fin del programa{colores.reset}')



if __name__ == '__main__':
    print("\n{:>65}".format("\033[4m" + "\033[1m" + "PROGRAMA GENERA DADOS" + "\033[0m" + "\033[0m"))

    # Definimos la clase colores con las siguientes variables para la estética del programa.
    class colores:
        rojo = '\033[91m'
        verde = '\033[92m'
        amarillo = '\033[2;30;47m'
        azul = '\033[96m'
        reset = '\033[0m'

    # Ejecutamos la función pregunta.

    pregunta()