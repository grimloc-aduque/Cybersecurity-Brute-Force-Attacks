# Network-Security-Brute-Force-Attacks

# Technologies
Python
* crypto
* threading
* queue

Java
* javax.crypto
* java.util

# Brute Force Attack Scheme
Highly efficient multithreading applications capable of duing encryiptions and decryptions of DES (56 bits), AES (256 bits) and 3DES (168 bits). <br>
Consists of a thread in charge of generating the keys and adding them to a queue, and various other thread in charge of doing the encryption/decryption. <br>
Thanks to its modularity, the scheme can be easily extended to any other encryption algorithm.
