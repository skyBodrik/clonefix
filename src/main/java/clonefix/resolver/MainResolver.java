package clonefix.resolver;

import clonefix.dependency.scanner.Storage;
import clonefix.objects.Clone;
import clonefix.objects.factory.ClonesGroup;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.util.List;

public class MainResolver {

    private Storage storage = Storage.getInstance();

    private boolean[] availableSolverMap = { true, true, true, true, true, true, true };

    public void run() {
        CompilationUnit cu;
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone3/Generator.java");
        ClonesGroup cloneGroup = new ClonesGroup();
        cloneGroup.addClone(new Clone(15, 5, 22, 6, cu));
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone2/Generator.java");
        cloneGroup.addClone(new Clone(12, 5, 19, 6, cu));
        cloneGroup.addClone(new Clone(21, 5, 28, 6, cu));
        cloneGroup.addClone(new Clone(30, 5, 37, 6, cu));
        List<Clone> listClone = cloneGroup.getCloneList();
        System.out.print(listClone.get(0).getClassOwnerSignature());
        Node startNode = listClone.get(0).getCloneStartNode();
/*        if (startNode != null) {
            System.out.print(startNode.toString());
        }*/
    }
}
