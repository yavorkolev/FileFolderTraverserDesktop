package writing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class WriterToFile extends Writer {
    File mFilePathTraverseLog;
    ExecutorService mExecutorService;
    List<String> mTraversedFolderContent = new ArrayList<>();

    public WriterToFile(int threadNumber, String logFile) {
        super(threadNumber);
        mFilePathTraverseLog = new File(logFile);
        mExecutorService = newFixedThreadPool(threadNumber);
    }

    @Override
    public void write(List<String> traversedFolderContent) {
        mTraversedFolderContent.addAll(traversedFolderContent);
        try {
            mExecutorService.execute(new ThreadExecutor(traversedFolderContent));
            throw new RuntimeException();
        } catch (Exception e) {
        }
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
            try (FileWriter fw = new FileWriter(mFilePathTraverseLog, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                for (String string : traversedFolderContent) {
                    out.print(string);
                }
            } catch (IOException e) {
            };
        }
    }

    @Override
    public void writeStop() {
        super.write(mTraversedFolderContent, mExecutorService);
    }
}
