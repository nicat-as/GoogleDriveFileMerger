package googleDriveFileMerger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class FileOp implements Callable<Void> {

    private String rootDirectory;
    private String destinationDirectory;
    private int index;

    public FileOp(String rootDirectory, String destinationDirectory, int index) {
        this.rootDirectory = rootDirectory;
        this.destinationDirectory = destinationDirectory;
        this.index = index;
    }

    private void copyFiles() throws IOException {
        Path path = Paths.get(rootDirectory);

        AtomicInteger integer = new AtomicInteger();


        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                String s = file.toString();
                String fileDir = s.substring(s.indexOf('/', index));

                Path to = Paths.get(destinationDirectory + fileDir);
                if (!Files.exists(to)) {
                    Files.createDirectories(to.getParent());
                }
                Files.copy(file, to);
                System.out.println(integer.addAndGet(1) + ") " + to);
                return FileVisitResult.CONTINUE;
            }
        });
    }


    @Override
    public Void call() throws Exception {
        copyFiles();
        return null;
    }
}
