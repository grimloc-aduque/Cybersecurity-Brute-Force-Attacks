
import base64
import sys
import crypto
sys.modules['Crypto'] = crypto
from crypto.Cipher import DES, DES3, AES
import Pipeline as P

from abc import ABC, abstractmethod

class TransformationTask(ABC):
    keys_counter = 0

    # Static methods

    def set_input_file(input_path):
        file = open(input_path, 'rb')
        TransformationTask.message_bytes = file.read()
        file.close()

    def set_output_file(output_path):
        TransformationTask.output_file = open(output_path, 'w')
        TransformationTask.output_file.write('key,output\n')
    
    def close_output_file():
        TransformationTask.output_file.close()

    # Instance methods

    def run(self):
        while TransformationTask.keys_counter < P.Pipeline.total_keys:
            if not P.Pipeline.key_queue.empty():
                key = P.Pipeline.key_queue.get()
                output_bytes = self._transform_bytes(key)
                key_string = f'{base64.b64encode(key)}'[2:-1]
                output_string = f'{base64.b64encode(output_bytes)}'[2:-1]
                TransformationTask.output_file.write(f'{key_string},{output_string}\n')
                TransformationTask.keys_counter += 1 

    @abstractmethod
    def _transform_bytes(self, key):
        pass


# Concrete Transformation Tasks

# DES

class DESEncryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = DES.new(key, DES.MODE_OFB,iv=(0).to_bytes(8, 'big'))
        encrypted_msg = cipher.encrypt(TransformationTask.message_bytes)
        return encrypted_msg

class DESDecryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = DES.new(key, DES.MODE_OFB, iv=(0).to_bytes(8, 'big'))
        decrypted_msg = cipher.decrypt(TransformationTask.message_bytes)
        return decrypted_msg

# DES3

class DES3Encryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = DES3.new(key, DES3.MODE_CFB, iv=(0).to_bytes(8, 'big'))
        encrypted_msg = cipher.encrypt(TransformationTask.message_bytes)
        return encrypted_msg

class DES3Decryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = DES3.new(key, DES3.MODE_CFB, iv=(0).to_bytes(8, 'big'))
        decrypted_msg = cipher.decrypt(TransformationTask.message_bytes)
        return decrypted_msg

# AES

class AESEncryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = AES.new(key, AES.MODE_CFB, iv=(0).to_bytes(16, 'big'))
        encrypted_msg = cipher.encrypt(TransformationTask.message_bytes)
        return encrypted_msg

class AESDecryption(TransformationTask):
    def _transform_bytes(self, key):
        cipher = AES.new(key, AES.MODE_CFB, iv=(0).to_bytes(16, 'big'))
        decrypted_msg = cipher.decrypt(TransformationTask.message_bytes)
        return decrypted_msg