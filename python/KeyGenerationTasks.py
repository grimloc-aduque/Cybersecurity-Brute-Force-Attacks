
import sys
import crypto
sys.modules['Crypto'] = crypto
from crypto.Random import get_random_bytes
from crypto.Cipher import DES3
import Pipeline as P
from abc import ABC, abstractmethod


class KeyGenerationTask(ABC):
    def run(self):
        for i in range(P.Pipeline.total_keys):
            key = self._generate_key()
            P.Pipeline.key_queue.put(key)

    @abstractmethod
    def _generate_key(self):
        pass


# Concrete Key Generation Tasks

# DES

class DESKeyGeneration(KeyGenerationTask):
    def _generate_key(self):
        return get_random_bytes(8)

# DES3

class DES3KeyGeneration(KeyGenerationTask):
    def _generate_key(self):
        return DES3.adjust_key_parity(get_random_bytes(24))

# AES

class AESKeyGeneration(KeyGenerationTask):
    def _generate_key(self):
        return get_random_bytes(16)
