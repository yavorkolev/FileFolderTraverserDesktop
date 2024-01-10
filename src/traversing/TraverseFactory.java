package traversing;

public class TraverseFactory {
    public Traversable getTraverse(String[] orderedArguments) {

        if (!orderedArguments[2].equals("no_filter")) {
            return new FilteredTraverse(orderedArguments);

        } else {
            return new Traverse(orderedArguments);
        }
    }
}
