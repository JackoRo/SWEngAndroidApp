import socket

socket = socket.socket()
host = "localhost"
port = 2400

socket.bind((host, port))
socket.listen(5)

print("Setup")
conn, addr = socket.accept()
print("Server connection established", addr)

msg = ""

try:
    while (1):
        msg += conn.recv(1024)
        print(msg)

        if (msg == "Hello Server"):
            print("Server ping successful")
        else:
            print("Error")
except KeyboardInterrupt:
    socket.close();


