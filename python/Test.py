from Pipeline import Pipeline
from PipelineFactories import *


def main():
    print("Python Encryption/Decryption Pipeline Test")

    factories = [DESEncryptionFactory(), DESDecryptionFactory(), 
                 DES3EncryptionFactory(), DES3DecryptionFactory(),
                 AESEncryptionFactory(), AESDecryptionFactory()]

    for factory in factories:
        Pipeline.load_configuration(factory)
        Pipeline.run()


if __name__ == "__main__":
    main()




