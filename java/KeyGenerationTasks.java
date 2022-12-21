import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

abstract class KeyGenerationTask implements Runnable {
    protected KeyGenerator keygenerator;

    @Override
    public void run(){
        for(int i=0; i<Pipeline.totalKeys; i++){
            SecretKey key = this.generateKey();
            Pipeline.keyQueue.add(key);
        }
    }
    
    abstract protected SecretKey generateKey();
}

// Concrete Key Generation Tasks

// DES

class DESKeyGeneration extends KeyGenerationTask{
    public DESKeyGeneration() throws NoSuchAlgorithmException {
        this.keygenerator = KeyGenerator.getInstance("DES");
    }
    @Override
    protected SecretKey generateKey() {
        return keygenerator.generateKey();
    }
}

// DES3

class DES3KeyGeneration extends KeyGenerationTask{
    public DES3KeyGeneration() throws NoSuchAlgorithmException {
        this.keygenerator = KeyGenerator.getInstance("DESede");
    }
    @Override
    protected SecretKey generateKey() {
        return keygenerator.generateKey();
    }
}

// AES

class AESKeyGeneration extends KeyGenerationTask{
    public AESKeyGeneration() throws NoSuchAlgorithmException {
        this.keygenerator = KeyGenerator.getInstance("AES");
    }
    @Override
    protected SecretKey generateKey() {
        return keygenerator.generateKey();
    }
}