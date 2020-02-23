# Google Drive File Merger
## Problem
When use Google Drive, we need download big size folder. Google Drive is zipping folder and creates multiple .zip files. Each .zip file contains same directory structure but not files. Files are distributed among the .zip files. This program solve these problems.

## Code
This program uses [**Files**](https://docs.oracle.com/javase/8/docs/api/java/nio/file/package-summary.html "java.nio.file") and [**ExecutorService**](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html "ExecutorService") . Following code snippet depicts **copying** functionality:
```java
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
```
For executing the program, first you need enter the parameters . Following code snippet describe that:
```java
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
```
