package googleDriveFileMerger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

        //rootDirectory - after extracted zip files, you need give extracted directory
        //destinationDirectory - creates your directory tree in the destination directory
        // index - is used for specifying after which part directory structure is repeating
        executorService.submit(new FileOp("/home/nicat/Videos/testinh"
                , "/home/nicat/Documents"
                , 60));
        executorService.shutdown();

    }
}
