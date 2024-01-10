package writing;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface Writable {
    void write(List<String> stringList);

    void write(List<String> stringList, ExecutorService mExecutorService);

    void writeStop();
}
