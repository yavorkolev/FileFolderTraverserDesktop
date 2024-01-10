package traversing;

import writing.Writable;
import writing.WriterFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Traverse implements Traversable {
    File mFilePathTraverse;
    Integer mThreadNumber;
    Writable mWritable;
    ExecutorService executorService;
    long start;

    boolean mHasContent = false;

    Traverse(String[] orderedArguments) {
        mFilePathTraverse = new File(orderedArguments[0]);
        mThreadNumber = Integer.valueOf(orderedArguments[1]);
        WriterFactory writerFactory = new WriterFactory();
        mWritable = writerFactory.getWrite(mThreadNumber, orderedArguments[4]);
    }

    @Override
    public void traverse() {
        setThreadNumber();
        traverse(0, mFilePathTraverse, executorService, null);
        setShutdownThread();
    }

    @Override
    public void traverse(FileFilter fileFilter) {
        setThreadNumber();
        traverse(0, mFilePathTraverse, executorService, fileFilter);
    }

    private void setThreadNumber() {
        start= System.nanoTime();
        executorService = newFixedThreadPool(mThreadNumber);
    }

     void setShutdownThread() {
        executorService.shutdown();
         try {
             executorService.awaitTermination(365, TimeUnit.DAYS);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
         long elapsed = System.nanoTime() - start;
         System.out.println("Duration in millisecond: "+TimeUnit.NANOSECONDS.toMillis(elapsed));
         if (executorService.isShutdown()) {
            mWritable.writeStop();
        }
    }

    private void traverse(int indent, File file, ExecutorService taskList, FileFilter fileFilter) {
        if (file.isDirectory()) {
            if(!mHasContent){
                if(file.listFiles(fileFilter).length > 0){
                    mHasContent = true;
                }
            }
            taskList.execute(new ThreadExecutor(file.listFiles(fileFilter), file.getAbsolutePath(), indent));
            File[] files = file.listFiles();
            for (File value : files != null ? files : new File[0]) traverse(indent + 1, value, taskList, fileFilter);
        }
    }

    public class ThreadExecutor implements Runnable {
        File[] mFiles;
        String mTraversedFolder;
        int mIndentNumber;
        String mIndentChar = "-";
        String mFormattedContent;
        List<String> listFolderContent = new ArrayList<>();

        public ThreadExecutor(File[] file, String traverseFolder, int indent) {
            mFiles = file;
            mTraversedFolder = traverseFolder + "\\";
            mIndentNumber = indent;
        }

        @Override
        public void run() {
            mIndentChar = "-";
            for (int i = 0; i < mIndentNumber; i++) mIndentChar += "-";
            if (mFiles != null) {
                for (File content : mFiles) {
                    mFormattedContent = content.toString();
                    listFolderContent.add(mFormattedContent.replace(mTraversedFolder, mIndentChar) + "\n");
                }
            }
            if(mHasContent){
                setFolderContent(listFolderContent);
            } else {
                listFolderContent.add("No Result");
                setFolderContent(listFolderContent);
            }
        }

        synchronized void setFolderContent(List<String> listFolderContent) {
            mWritable.write(listFolderContent);
        }
    }
}

