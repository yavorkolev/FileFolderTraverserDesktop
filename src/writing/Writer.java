package writing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Writer implements Writable {
    ExecutorService mExecutorService;

    public Writer(int threadNumber) {
        mExecutorService = newFixedThreadPool(threadNumber);
    }

    @Override
    public void write(List<String> traversedFolderContent) {
        try {
            mExecutorService.execute(new ThreadExecutor(traversedFolderContent));
            throw new RuntimeException();
        }
        catch (Exception e) {
        }
    }

    @Override
    public void write(List<String> traversedFolderContent, ExecutorService mExecutorService) {
        try {
            mExecutorService.execute(new ThreadExecutor(traversedFolderContent));
            throw new RuntimeException();
        }
        catch (Exception e) {
        }
        mExecutorService.shutdown();
    }

    class ThreadExecutor implements Runnable {
        List<String> mTraversedFolderContent = new ArrayList<>();

        ThreadExecutor(List<String> traversedFolderContent) {
            mTraversedFolderContent.addAll(traversedFolderContent);
        }

        @Override
        public void run() {
            writeContent(mTraversedFolderContent);
        }

        synchronized void writeContent(List<String> traversedFolderContent) {
            traversedFolderContent.stream().forEach(System.out::print);
        }
    }

    @Override
    public void writeStop() {
        mExecutorService.shutdown();
    }
}
