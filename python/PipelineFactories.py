from abc import ABC, abstractmethod
from tokenize import String
from KeyGenerationTasks import *
from TransformationTasks import *

class PipelineFactory(ABC):
    def __init__(self, algorithm, transformation):
        self.algorithm = algorithm
        self.transformation = transformation

    @abstractmethod
    def create_key_generation_task(self) -> KeyGenerationTask:
        pass

    @abstractmethod
    def create_transformation_task(self) -> TransformationTask:
        pass


# Concrete Pipeline Factories

# DES

class DESEncryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('DES', 'Encryption')

    def create_key_generation_task(self):
        return DESKeyGeneration()

    def create_transformation_task(self):
        return DESEncryption()

class DESDecryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('DES', 'Decryption')

    def create_key_generation_task(self):
        return DESKeyGeneration()
        
    def create_transformation_task(self):
        return DESDecryption()

# DES3

class DES3EncryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('DES3', 'Encryption')

    def create_key_generation_task(self):
        return DES3KeyGeneration()

    def create_transformation_task(self):
        return DES3Encryption()

class DES3DecryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('DES3', 'Decryption')

    def create_key_generation_task(self):
        return DES3KeyGeneration()
        
    def create_transformation_task(self):
        return DES3Decryption()

# AES

class AESEncryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('AES', 'Encryption')

    def create_key_generation_task(self):
        return AESKeyGeneration()

    def create_transformation_task(self):
        return AESEncryption()

class AESDecryptionFactory(PipelineFactory):
    def __init__(self):
        super().__init__('AES', 'Decryption')

    def create_key_generation_task(self):
        return AESKeyGeneration()
        
    def create_transformation_task(self):
        return AESDecryption()