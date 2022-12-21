import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;


abstract class TransformationTask implements Runnable{

    private static Integer keyCounter = 0;
    private static FileWriter outputFile;
    protected static byte[] messageBytes;
    protected Cipher cipher;

    public static void setInputFile(String inputPath) throws IOException{
        TransformationTask.messageBytes = Files.readAllBytes(Paths.get(inputPath));
    }

    public static void setOutputFile(String outputPath) throws IOException{
        TransformationTask.outputFile = new FileWriter(outputPath);
        TransformationTask.outputFile.write("key,output\n");
    }

    public static void close_output_file() throws IOException{
        TransformationTask.outputFile.close();
    }

    public static void resetKeyCounter(){
        TransformationTask.keyCounter = 0;
    }

    @Override
    public void run() {
        while (TransformationTask.keyCounter < Pipeline.totalKeys){
            SecretKey key = TransformationTask.get_next_key();
            if(key == null){
                continue;
            }
            byte[] outputBytes = null;
            Encoder encoder = Base64.getEncoder();
            String keyString = encoder.encodeToString(key.getEncoded());
            String outputString;
            try {
                outputBytes = this.transformBytes(key);
                outputString = encoder.encodeToString(outputBytes);
            } catch (Exception e) {
                outputString = "Could not encrypt/decrypt. Wrong Key/Input Format";
            }
            try {
                TransformationTask.outputFile.write(String.format("%s,%s\n", keyString, outputString));
            } catch (IOException e) {}
            TransformationTask.increaseKeyCounter();
        }
    }

    synchronized
    private static SecretKey get_next_key(){
        SecretKey key = null;
        try{
            key = Pipeline.keyQueue.remove();
        }catch(Exception e){}
        return key;
    }

    synchronized
    private static void increaseKeyCounter(){
        TransformationTask.keyCounter  ++;
    }

    abstract protected byte[] transformBytes(SecretKey key) throws Exception;
}


// Concrete Transformation Tasks

// DES

class DESEncryption extends TransformationTask{
    public DESEncryption() throws Exception{
        this.cipher = Cipher.getInstance("DES");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        return encryptedBytes;
    }
}

class DESDecryption extends TransformationTask{
    public DESDecryption() throws Exception{
        this.cipher = Cipher.getInstance("DES");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes;
        try{
            decryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        }catch(Exception e){
            decryptedBytes = null;
        }
        return decryptedBytes;
    }
}

// DES3

class DES3Encryption extends TransformationTask{
    public DES3Encryption() throws Exception{
        this.cipher = Cipher.getInstance("DESede");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        return encryptedBytes;
    }
}

class DES3Decryption extends TransformationTask{
    public DES3Decryption() throws Exception{
        this.cipher = Cipher.getInstance("DESede");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes;
        try{
            decryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        }catch(Exception e){
            decryptedBytes = null;
        }
        return decryptedBytes;
    }
}

// AES

class AESEncryption extends TransformationTask{
    public AESEncryption() throws Exception{
        this.cipher = Cipher.getInstance("AES");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        return encryptedBytes;
    }
}

class AESDecryption extends TransformationTask{
    public AESDecryption() throws Exception{
        this.cipher = Cipher.getInstance("AES");
    }
    @Override
    protected byte[] transformBytes(SecretKey key) throws Exception{
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes;
        try{
            decryptedBytes = this.cipher.doFinal(TransformationTask.messageBytes);
        }catch(Exception e){
            decryptedBytes = null;
        }
        return decryptedBytes;
    }
}