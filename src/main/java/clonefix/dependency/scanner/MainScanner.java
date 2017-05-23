package clonefix.dependency.scanner;

import clonefix.dependency.scanner.handlers.ClassHandler;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MainScanner {

    private Storage storage = Storage.getInstance();

    public MainScanner(String rootPath) throws IOException {
        storage.refresh(rootPath);
/*        CompilationUnit cu;
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone2/Generator.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone3/Generator.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone1/SellerExtend.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone1/Seller.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/clone1/Client.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/Main.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/MainParent.java");
        findDependencies(cu);
        cu = storage.getCompilationUnit("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/_JavaLexer.java");
        findDependencies(cu);*/
        for(Map.Entry<String, CompilationUnit> entry : storage.getCompilationUnits().entrySet()) {
            String key = entry.getKey();
            CompilationUnit cu = entry.getValue();
            findDependencies(cu);
        }
    }


    public void findDependencies(CompilationUnit cu) {
        List<Node> nodeList = cu.getChildNodes();
        List<String> packagesList = storage.getPackagesList();
        packagesList.clear();
        packagesList.add(cu.getPackageDeclaration().get().getName().asString());
        //if (nodeList.get(0).getMetaModel().getTypeName().equals("PackageDeclaration")) {
        //}
        NodeList<ImportDeclaration> imports = cu.getImports();
        for (ImportDeclaration imp : imports) {
            String namePackage = imp.getName().asString().replaceAll(".[^\\.]+$", "");
            if (!packagesList.contains(namePackage)) {
                packagesList.add(namePackage);
            }
        }
        for (Node node : nodeList) {
            if (node.getMetaModel().getTypeName().equals("ClassOrInterfaceDeclaration")) {
                List<Node> childNode = node.getChildNodes();
                for (Node c : childNode) {
                    if (c.getMetaModel().getTypeName().equals("SimpleName")) {
                        System.out.print(c.toString());
                        ClassHandler ch = new ClassHandler(cu.getClassByName(c.toString()).get());
                    }
                }
            }
        }
/*        for (String str : packagesList) {
            System.out.print(str + "\n");
        }*/
    }

}
