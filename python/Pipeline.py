
import threading
import queue
import time
from PipelineFactories import PipelineFactory
from KeyGenerationTasks import KeyGenerationTask
from TransformationTasks import TransformationTask

class Pipeline:

    def load_configuration(PipelineFactory:PipelineFactory):
        # Set up
        Pipeline.total_keys = 10000
        Pipeline.key_queue = queue.Queue()
        Pipeline.num_transformation_threads = 6
        TransformationTask.keys_counter = 0
        Pipeline.factory = PipelineFactory

        #  I/O Files
        input_path = './files/message.txt'
        TransformationTask.set_input_file(input_path)

        algorithm = PipelineFactory.algorithm
        transformation = PipelineFactory.transformation
        output_path = f'./python/output/{algorithm}_{transformation}.txt'
        TransformationTask.set_output_file(output_path)
        
        print(f'Setting up Pipeline with {type(PipelineFactory).__name__}')
        

    def run():
        print('Running Pipeline')
        start_time = time.time()

        key_generation_task = Pipeline.factory.create_key_generation_task()
        key_generation_thread = threading.Thread(target = key_generation_task.run)
        key_generation_thread.start()

        transformation_threads = []
        for i in range(Pipeline.num_transformation_threads):
            transformation_task = Pipeline.factory.create_transformation_task()
            transformation_thread = threading.Thread(target = transformation_task.run)
            transformation_thread.start()
            transformation_threads.append(transformation_thread)
        
        key_generation_thread.join()
        for thread in transformation_threads:
            thread.join()

        execution_time = round(time.time() - start_time, 4)
        print(f'Pipeline took: --- {execution_time} seconds ---\n')