import java.security.NoSuchAlgorithmException;

abstract class PipelineFactory {
    protected String algorithm;
    protected String transformation;

    protected PipelineFactory(String algorithm, String transformation){
        this.algorithm = algorithm;
        this.transformation = transformation;
    }

    public String getAlgorithmName(){
        return this.algorithm;
    }
    public String getTransformationName(){
        return this.transformation;
    }
    public abstract KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException;
    public abstract TransformationTask createTransformationTask() throws Exception;
}


// Concrete Pipeline Factories

// DES

class DESEncryptionFactory extends PipelineFactory{
    public DESEncryptionFactory(){
        super("DES", "Encryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new DESKeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception{
        return new DESEncryption();
    }
}

class DESDecryptionFactory extends PipelineFactory{
    protected DESDecryptionFactory() {
        super("DES", "Decryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new DESKeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception {
        return new DESDecryption();
    }
}

// DES3

class DES3EncryptionFactory extends PipelineFactory{
    public DES3EncryptionFactory(){
        super("DES3", "Encryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new DES3KeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception{
        return new DES3Encryption();
    }
}

class DES3DecryptionFactory extends PipelineFactory{
    protected DES3DecryptionFactory() {
        super("DES3", "Decryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new DES3KeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception {
        return new DES3Decryption();
    }
}

// AES

class AESEncryptionFactory extends PipelineFactory{
    public AESEncryptionFactory(){
        super("AES", "Encryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new DESKeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception{
        return new DESEncryption();
    }
}

class AESDecryptionFactory extends PipelineFactory{
    protected AESDecryptionFactory() {
        super("AES", "Decryption");
    }

    @Override
    public KeyGenerationTask createKeyGenerationTask() throws NoSuchAlgorithmException {
        return new AESKeyGeneration();
    }

    @Override
    public TransformationTask createTransformationTask() throws Exception {
        return new AESDecryption();
    }
}