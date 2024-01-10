package traversing;

import java.io.FileFilter;
import java.util.List;

public class FilteredTraverse extends Traverse {
    FileFilter fileFilter;
    String mStringCommand;
    public static List COMMANDS = List.of(new String[]{"CONTAINS", "ENDS"});

    FilteredTraverse(String[] orderedArguments) {
        super(orderedArguments);
        mStringCommand = orderedArguments[3];
        if(mStringCommand != null){
            if (mStringCommand.equals(COMMANDS.get(0))) {
                fileFilter = file -> !file.isDirectory() && file.getName().contains(orderedArguments[2]);
            } else if (mStringCommand.equals(COMMANDS.get(1))) {
                fileFilter = file -> !file.isDirectory() && file.getName().endsWith(orderedArguments[2]);
            }
        } else {
            fileFilter = file -> !file.isDirectory() && file.getName().contains(orderedArguments[2]);
        }
    }

    @Override
    public void traverse() {
        super.traverse(fileFilter);
        super.setShutdownThread();
    }
}
