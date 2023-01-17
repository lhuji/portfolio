import pickle
from os import remove


# Mediante una función incorporamos las variables para mostrar el nombre del menú.

def introduciropciones():
    # Abrimos el archivo en modo escritura.

    with open("menu.txt", "w+", encoding="utf-8") as f:
        print(f"\n{colores.rojo}Escribe el menú de opciones:{colores.reset}")
        for i in range(6):
            f.write(input(f"\n\t\t {colores.negrita}Escribir la opción {i + 1}:{colores.reset} ") + "\n")


def mostrar_menu(nombre, nombre2, opciones):
    print(f' {nombre}\n{nombre2}\n')
    for clave in sorted(opciones):
        print(f'\t\t\t\t{colores.fblanco} {clave}) {colores.reset} {opciones[clave][0]}')


# Creamos una función que lea la opción introducida por el usuario, si no es correcta, vuelve a pedirte otra opción.

def leer_opcion(opciones):
    while (a := input("\n{:>40}".format('\033[1m' + 'Opción\t' + '\033[0m'))) not in opciones:
        print('Opción incorrecta, vuelva a intentarlo.')
    return a


# Creamos una función que ejecute la opción introducida por el usuario.

def ejecutar_opcion(opcion, opciones):
    opciones[opcion][1]()


# Creamos una función que mande sus variables a diferentes funciones.

def generar_menu(nombre, opciones, sali):
    opcion = None
    while opcion != saliR:
        mostrar_menu(nombre, nombre2, opciones)
        opcion = leer_opcion(opciones)
        ejecutar_opcion(opcion, opciones)
        print()


# Creamos una función que nos genere las diferentes opciones y el nombre de "Menu de Opciones" con su "=======" debajo.

def menu_principal():
    # Le decimos a una función que dependiendo de la opción (opciones) escogida nos ejecute una función (funcionN) u
    # otra.
    with open("menu.txt", "r") as f:
        opciones = {
            '1': (f.readline(), funcion1),
            '2': (f.readline(), funcion2),
            '3': (f.readline(), funcion3),
            '4': (f.readline(), funcion4),
            '5': (f.readline(), funcion5),
            '6': (f.readline(), salir)
        }
    # Indicamos el nombre del menú

    generar_menu("\n{:>50}".format('\033[1m' + 'Menú de Opciones' + '\033[0m'),
                 "{:>50}".format('\033[1m' + '================' + '\033[0m'), opciones, '6')
    return


# Creamos una función que nos transforme en lista el archivo .dat.
def listabonados():
    f = open("facturas_telf.dat", "rb")

    lista = pickle.load(f)

    f.close()

    return lista


# Definimos la función de la opción 1.

def funcion1():
    print("\n{:>5}".format("\033[4m" + colores.negrita + "Alta de factura\n" + colores.reset))

    lista = listabonados()

    num_abo = int(input(colores.verde + 'Número del abonado: ' + colores.reset))
    nombre = input(colores.verde + "Nombre: " + colores.reset)
    valor_fact = float(input(colores.verde + "Valor de la factura: " + colores.reset))
    print("\n\t Datos del nuevo usuario incorporados al fichero")

    # Los datos tecleados pasan como valores a un diccionario

    nuevousuario = {"num_abo": num_abo, "nombre": nombre, "valor_fact": valor_fact}

    # Añadimos el diccionario nuevousuario a la lista

    lista.append(nuevousuario)

    # Abrimos el archivo para modo escritura

    f = open("facturas_telf.dat", "wb")

    # Con dump (de la librería pickle) se guardan los datos que hay en la lista en el fichero

    pickle.dump(lista, f)

    f.close()


# Definimos la función de la opción 2.

def funcion2():
    lista = listabonados()

    x = True
    print("\n{:>5}".format(f"\033[4m" + "\033[1m" + "Modificación del valor de factura" + "\033[0m" + "\033[0m"))
    while x:
        a = int(input("\n{:>1}".format(colores.negrita + "Número del abonado: " + colores.reset)))

        for i, elem in enumerate(lista):
            if elem["num_abo"] == a:
                print(f"\n\tValor de la factura: ", elem["valor_fact"], "€")
                nvalor = float(input(f"{colores.verde}\nNuevo Valor Factura: {colores.reset}"))
                print(f"{colores.rojo}\n\tDato del usuario modificado en el fichero{colores.reset}")
                elem["valor_fact"] = nvalor

                # Hacemos un break para que no siga buscando.

                break
        # Abrimos el archivo para modo escritura en binario.

        f = open("facturas_telf.dat", "wb")

        # Con dump (de la librería pickle) se guardan los datos que hay en la lista en el fichero.

        pickle.dump(lista, f)

        f.close()
        x = False


# Definimos la función de la opción 3.

def funcion3():
    lista = listabonados()

    # Creamos un bucle para que busque el número del abonado en la lista.

    x = True
    print("\n{:>5}".format(f"\033[4m" + "\033[1m" + "Consulta facturación abonado" + "\033[0m" + "\033[0m"))
    while x:
        a = int(input("\n{:>1}".format(colores.negrita + "Número del abonado: " + colores.reset)))

        for i, elem in enumerate(lista):
            if elem["num_abo"] == a:
                print(f"\n\tValor de la factura: ", elem["valor_fact"], "€")
                a = 0
                x = False

        # Sin embargo, si no lo encuentra, dirá que el abonado no está registrado

        if a != 0:
            print("\n\tAbonado no registrado")


# Definimos la función de la opción 4.

def funcion4():
    print("\n{:>5}".format(f"\033[4m" + "\033[1m" + "Consulta de facturación total" + "\033[0m" + "\033[0m"))
    lista = listabonados()
    suma = 0

    # Decimos que nos haga un bucle que nos sume los valores de todos los diccionarios en la lista

    for i, elem in enumerate(lista):
        suma += (elem["valor_fact"])
    print(f'\n\tFacturación total: {suma}€')


# Definimos la función de la opción 5.

def funcion5():
    print("\n{:>5}".format(f"\033[4m" + "\033[1m" + "Eliminar fichero" + "\033[0m" + "\033[0m"))
    remove("facturas_telf.dat")
    print("\n\tFichero eliminado.")


# Definimos la función de la opción salir.

def salir():
    print('Saliendo')


if __name__ == '__main__':
    print("\n{:>65}".format(f"\033[4m" + "\033[1m" + "PROGRAMA GESTIÓN COMPAÑÍA TELEFÓNICA" + "\033[0m" + "\033[0m"))


    # Definimos la clase colores con las siguientes variables para la estética del programa.

    class colores:
        negrita = '\033[1m'
        rojo = '\033[91m'
        verde = '\033[92m'
        fblanco = '\033[2;30;47m'
        azul = '\033[96m'
        reset = '\033[0m'


    introduciropciones()
    menu_principal()
