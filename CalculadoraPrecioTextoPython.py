# Introducir el mensaje.
frase = input("Teclea el mensaje:")

# Imprimir el mensaje.
print("Cadena tecleada:" , frase)

# Reemplazar los puntos por STOP.
stopfrase = frase.replace(".", " STOP")

# Método 1 para añadir STOP al final del mensaje e imprimirlo.
# frasefinal = stopfrase.__add__("STOP")
# print(f"Mensaje a enviar: {frasefinal}")
# Método 2 para añadir STOP al final del mensaje e imprimirlo.
# print(f"Mensaje a enviar: {stopfrase}STOP")
# Método 3 para añadir STOP al final del mensaje si tiene un punto.
if frase [-1] =='.':
    stopfrase = stopfrase.__add__('STOP')
print(stopfrase)

# Sustituir los puntos por espacios.
espaciofrase = frase.replace(".", " ")

# Dividir las palabras.
palabras = espaciofrase.split()

# Contar el número de palabras.
npalabras = len(palabras)

# Creamos variables para palabras grandes, chicas y suma.
palabrasg = 0
palabrasc = 0
suma = 0

# Bucle para contar palabras grandes y chicas.
for i in palabras:
    if len(i) > 5:
        suma += 0.5
        palabrasg += 1
    else:
        suma += 0.25
        palabrasc += 1


# Imprimir mensaje con las variables: palabras, palabrasg y palabrasc.
print(f"La cadena contiene {npalabras} palabras, de las cuales {palabrasg} tienen más de 5 letras.\nPor tanto, al precio de 0,25€/palabra tenemos {palabrasc} y a 0.50€/palabra hay otras {palabrasg}.")

# Imprimir mensaje con la variable suma.
print(f"Total: {suma}€")

print("{:>50}".format('\033[1m' + 'Menú de Opciones' + '\033[0m'))
print("{:>50}".format('\033[1m' + '================' + '\033[0m'))

