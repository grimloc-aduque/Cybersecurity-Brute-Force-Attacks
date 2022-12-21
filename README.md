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
Highly efficient multithreading applications capable of performing encryptions and decryptions of:
* DES (56 bits)
* AES (256 bits)
* 3DES (168 bits).

The scheme consists of one thread in charge of generating the keys and adding them to a queue, and various other thread in charge of extracting the keys from the queue and performing the encryption/decryption.

Thanks to its modularity, the scheme can be easily extended to any other encryption algorithm.
