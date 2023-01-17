import pickle
import random
if __name__ == '__main__':
    print("\n{:>65}".format("\033[4m" + "\033[1m" + "PROGRAMA ADIVINA EL NÚMERO" + "\033[0m" + "\033[0m"))
    with open("mensajes.txt", "r", encoding="utf-8") as f:
        lista = [f.readline(),f.readline(),f.readline(),f.readline(),f.readline(),f.readline(),f.readline()]
    f.close()
    print(f"Insertando el letrero en la lista:{lista[0]}")
    print(f"Insertando el letrero en la lista:{lista[1]}")
    print(f"Insertando el letrero en la lista:{lista[2]}")
    print(f"Insertando el letrero en la lista:{lista[3]}")
    print(f"Insertando el letrero en la lista:{lista[4]}")
    print(f"Insertando el letrero en la lista:{lista[5]}")
    print(f"Insertando el letrero en la lista:{lista[6]}")
    print("\033[1m" + "La lista ya está completa" + "\033[0m\n")
    naleatorio = random.randint(1,40)
    print(f"{lista[0]}")
    print(f"{lista[1]}")
    contador = 0
    valores = set()
    while contador < 6:
        numero = int(input(f"{lista[2]}"))
        if numero in range(0,41):
            valores.add(numero)
            if contador == 6:
                contador = 7
            if numero == naleatorio:
                contador += 1
                print(f"{lista[5]}¡Has necesitado {contador} intentos!")
                contador = 6
            if numero < naleatorio:
                contador += 1
                print(f"{lista[4]}")
            if numero > naleatorio:
                contador += 1
                print(f"{lista[3]}")
        else:
            print("El número que has introducido no es correcto, vuelve a introducir un número entre 1 y 40")
    print(f'Los valores de los números tecleados fueron: {"".join((list(str(sorted(valores)))[1:-1]))}')

