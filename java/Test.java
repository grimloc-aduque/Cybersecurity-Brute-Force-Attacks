public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("Java Encryption/Decryption Pipeline Test");
        PipelineFactory[] factories = new PipelineFactory[]{
            new DESEncryptionFactory(), new DESDecryptionFactory(),
            new DES3EncryptionFactory(), new DES3DecryptionFactory(),
            new AESEncryptionFactory(), new AESDecryptionFactory()
        };
        
        for(int i=0; i<factories.length; i++){
            PipelineFactory factory = factories[i];
            Pipeline.loadConfiguration(factory);
            Pipeline.run();
        }
    }
}
