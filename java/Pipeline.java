import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import javax.crypto.SecretKey;

public class Pipeline {
    public static Integer totalKeys;
    public static Queue<SecretKey> keyQueue;
    private static Integer numTransformationThreads;
    private static PipelineFactory factory;


    public static void loadConfiguration(PipelineFactory pipelineFactory) throws IOException{
        
        Pipeline.totalKeys = 10000;
        Pipeline.numTransformationThreads = 6;
        Pipeline.keyQueue = new ArrayBlockingQueue<>(totalKeys);
        Pipeline.factory = pipelineFactory;

        // I/O Files
        String inputPath = "./files/message.txt";
        TransformationTask.setInputFile(inputPath);
        String algorithm = Pipeline.factory.getAlgorithmName();
        String transformation = Pipeline.factory.getTransformationName();

        String outputPath = String.format("./java/output/%s_%s.txt", algorithm, transformation);
        TransformationTask.setOutputFile(outputPath);

        System.out.printf("Setting pipeline with %s\n", pipelineFactory.getClass().getName());
    }

    
    // Execution

    public static void run() throws Exception{
        System.out.println("Running Pipeline");
        long startTime = System.currentTimeMillis();
        
        // Inicio la generacion de claves
        KeyGenerationTask keyGenerationTask = Pipeline.factory.createKeyGenerationTask();
        Thread keyGenerationThread = new Thread(keyGenerationTask);
        keyGenerationThread.start();

        // Inicio las encripciones/desencripciones
        List<Thread> transformationThreads = new ArrayList<>();
        for(int i=0; i<Pipeline.numTransformationThreads; i++){
            TransformationTask transformationTask = Pipeline.factory.createTransformationTask();
            Thread transformationThread = new Thread(transformationTask);
            transformationThread.start();
            transformationThreads.add(transformationThread);
        }
        
        // Espero a que terminen
        keyGenerationThread.join();
        for(int i=0; i<Pipeline.numTransformationThreads; i++){
            transformationThreads.get(i).join();
        }
        TransformationTask.close_output_file();
        TransformationTask.resetKeyCounter();

        long endTime = System.currentTimeMillis();
        float executiontime = (endTime - startTime) / 1000F;
        System.out.printf("Pipeline took: --- %s seconds ---\n\n", executiontime);
    }
}
