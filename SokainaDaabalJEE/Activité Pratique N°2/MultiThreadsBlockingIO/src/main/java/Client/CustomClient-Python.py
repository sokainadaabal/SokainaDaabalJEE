import socket as sc
import  threading as th


client_socket= sc.socket(sc.AF_INET, sc.SOCK_STREAM)  # instance
client_socket.connect(("localhost",1234)) # connceter au serveur


def recuReponse(sc):
    while True:
        try:
            data = sc.recv(1024).decode('utf-8')[:-1] #reprise reponse
            print(data) #show in terminal
        except:
            print("Erreur")
            client_socket.close()
            break
def EnvoieReponse():
      while True:
        request = input()
        client_socket.send(request.encode('utf-8')+b'\n') # envoyer un message


thread=th.Thread(target=recuReponse, args=(client_socket,))
Reponse=th.Thread(target=EnvoieReponse)

thread.start()
Reponse.start()