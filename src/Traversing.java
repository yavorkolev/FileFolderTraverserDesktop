import arguments.Arguments;
import traversing.Traversable;
import traversing.TraverseFactory;

public class Traversing {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.print("Argument \"File\" to a valid folder for traverse is a mandatory!");
        } else {
            Arguments arguments = new Arguments(args);
            if (arguments.argToMap()) {
                String[] orderedArguments = {
                        arguments.getFile(),
                        String.valueOf(arguments.getThreadsNumber()),
                        arguments.getFilter(),
                        arguments.getCommand(),
                        arguments.getLogFile()};
                if (
                        arguments.getFile() != null
                                && arguments.getThreadsNumber() != 0
                                && arguments.getFilter() != null
                                && arguments.getCommand() != null
                                && arguments.getLogFile() != null
                ) {
                    TraverseFactory traverseFactory = new TraverseFactory();
                    Traversable traverse = traverseFactory.getTraverse(orderedArguments);
                    traverse.traverse();
                } else if (arguments.getFile() == null) {
                    System.out.print("Argument: " + Arguments.ARGUMENTS_KEYS.get(0) + " is not set properly!");
                } else if (arguments.getThreadsNumber() == 0) {
                    System.out.print("Argument: " + Arguments.ARGUMENTS_KEYS.get(1) + " is not set properly!");
                } else if (arguments.getFilter() == null) {
                    System.out.print("Argument: " + Arguments.ARGUMENTS_KEYS.get(2) + " is not set properly!");
                } else if (arguments.getCommand() == null) {
                    System.out.print("Argument: " + Arguments.ARGUMENTS_KEYS.get(3) + " is not set properly!");
                } else if (arguments.getLogFile() == null) {
                    System.out.print("Argument: " + Arguments.ARGUMENTS_KEYS.get(4) + " is not set properly!");
                }
            }

        }

    }
}
