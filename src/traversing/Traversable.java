package traversing;

import java.io.FileFilter;

public interface Traversable {
    void traverse();

    void traverse(FileFilter fileFilter);
}
