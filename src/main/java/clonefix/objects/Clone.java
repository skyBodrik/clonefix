package clonefix.objects;

import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.util.List;

public class Clone implements InterfaceClone {

    private Position positionStart = null;
    private Position positionEnd = null;

    private Node nodeStart = null;
    private Node nodeEnd = null;

    private Node classOwner = null;

    private CompilationUnit compilationUnit;

    private void findCloneAst(List<Node> listNodes) {
        for (Node node : listNodes) {
            if (node.getBegin().get().equals(positionStart)) {
                nodeStart = node;
            }
            if (node.getEnd().get().equals(positionEnd)) {
                nodeEnd = node;
            }
            List<Node> childNodes = node.getChildNodes();
            if (childNodes.size() > 0) {
                findCloneAst(childNodes);
            }
        }
    }

    private Node pushUpUntil(Node currentNode, String nodeTypeName) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getMetaModel().getTypeName().equals(nodeTypeName)) {
            return currentNode;
        }
        return pushUpUntil(currentNode.getParentNode().get(), nodeTypeName);
    }

    public Clone(int lineStart, int columnStart, int lineEnd, int columnEnd, CompilationUnit cu) {
        positionStart = new Position(lineStart, columnStart);
        positionEnd = new Position(lineEnd, columnEnd);
        compilationUnit = cu;
    }

    public Node getCloneStartNode() {
        if (nodeStart == null) {
            List<Node> nodeList = compilationUnit.getChildNodes();
            findCloneAst(nodeList);
        }
        return nodeStart;
    }

    public Node getCloneEndNode() {
        if (nodeEnd == null) {
            List<Node> nodeList = compilationUnit.getChildNodes();
            findCloneAst(nodeList);
        }
        return nodeEnd;
    }

    public Node getClassOwner() {
        if (classOwner == null) {
            classOwner = pushUpUntil(getCloneStartNode(), "ClassOrInterfaceDeclaration");
        }
        return classOwner;
    }

    public String getClassOwnerName() {
        List<Node> childNode = getClassOwner().getChildNodes();
        for (Node c : childNode) {
            if (c.getMetaModel().getTypeName().equals("SimpleName")) {
                return c.toString();
            }
        }
        return null;
    }

    public String getClassOwnerSignature() {
        return compilationUnit.getPackageDeclaration().get().getNameAsString() + "." + getClassOwnerName();
    }

}
