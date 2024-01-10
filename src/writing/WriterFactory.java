package writing;

public class WriterFactory {
    public Writable getWrite(int threadNumber, String logFile) {
        if (logFile != null) {
            return new WriterToFile(threadNumber, logFile);
        } else {
            return new Writer(threadNumber);
        }
    }
}
