package clonefix.dependency.scanner.handlers;


import clonefix.dependency.scanner.Storage;
import clonefix.dependency.scanner.visitors.GetSignatureVisitor;
import clonefix.dependency.scanner.visitors.GetTypeExpressionVisitor;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.*;

public class ClassHandler {

    private String className = "";

    private ClassOrInterfaceDeclaration classObj;

    private Storage storage = Storage.getInstance();

    public ClassHandler(ClassOrInterfaceDeclaration class1) {
        GetSignatureVisitor signatureVisitor = new GetSignatureVisitor();
        this.className = class1.getName().asString();
        this.classObj = class1;
        List<MethodDeclaration> methods = class1.getMethods();
        Map<String, String> vars = new HashMap<String, String>();
        for (MethodDeclaration method : methods) {
            vars.put("0className", storage.searchClassByName(this.className));
            String methodSignature = method.accept(signatureVisitor, vars);
            String depName = storage.searchClassByName(method.getType().asString());
            if (depName != null) {
                //System.out.print(depName);
            }
            NodeList<Parameter> parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                depName = storage.searchClassByName(parameter.getType().asString());
                if (depName != null) {
                    //System.out.print(depName);
                }
            }
            System.out.print("\n\n" + method.getName().asString() + "\n");
            //System.out.print(method.getSignature() + "\n");
            vars.put("0className", storage.searchClassByName(this.className));
            List<String> dependencyList = findNestedDependency(method, vars);
            for (String d : dependencyList) {
                List<String> curList = storage.getDependenciesMap().get(d);
                if (curList == null) {
                    curList = new ArrayList<String>();
                }
                if (!curList.contains(methodSignature)) {
                    curList.add(methodSignature);
                }
                storage.getDependenciesMap().put(d, curList);
                //System.out.print(d + "\n------\n");
            }
/*            System.out.print("\n\n");
        List<Node> listChildNodes = method.getChildNodes();
        for (Node node : listChildNodes) {
            if (node.getChildNodes().size() != 6) continue;
            System.out.print(node.getChildNodes().get(0).getChildNodes().get(0).getChildNodes().get(0).getChildNodes().get(2).getChildNodes().get(0).toString() + ", ");
        }*/
            //method.getType();
        }
    }

    private List<String> findNestedDependency(Node node, Map<String, String> vars) {
        List<String> list = new ArrayList<String>();
        for (Node i : node.getChildNodes()) {
            if (i.getMetaModel().getTypeName().equals("VariableDeclarationExpr")) {
                VariableDeclarationExpr expr = (VariableDeclarationExpr)i;
                String className = storage.searchClassByName(expr.getElementType().asString());
                //System.out.print(className + ", ");
/*                if (expr.getElementType().getChildNodes().size() > 1) {
                    System.out.print(expr.getElementType().getChildNodes().get(1).getMetaModel().getTypeName() + ", ");
                }*/
                if (className != null && !list.contains(className)) {
                    list.add(className);
                }
            }
            if (i.getMetaModel().getTypeName().equals("ObjectCreationExpr")) {
                ObjectCreationExpr expr = (ObjectCreationExpr)i;
                //System.out.print(expr.getChildNodes().get(0).toString() + ", ");
                //String className = expr.getChildNodes().get(0).toString();
                String className;
                if (expr.getScope().equals(Optional.empty())) {
                    className = storage.searchClassByName(expr.getChildNodes().get(0).toString());
                } else {
                    className = expr.getScope().get().toString() + "." + expr.getChildNodes().get(0).toString();
                }
                if (className != null && !list.contains(className)) {
                    list.add(className);
                }
                if (expr.getParentNode().get().getMetaModel().getTypeName().equals("VariableDeclarator")) {
                    VariableDeclarator vd = (VariableDeclarator)expr.getParentNode().get();
                    String nameVar = vd.getName().asString();
                    vars.put(nameVar, className);
                    //System.out.print(nameVar + " = " + className + ", ");
                }
            }
            if (i.getMetaModel().getTypeName().equals("ClassOrInterfaceType") && !i.getParentNode().get().getMetaModel().getTypeName().equals("ClassOrInterfaceType")) {
                ClassOrInterfaceType expr = (ClassOrInterfaceType)i;
                String className;
                if (expr.getScope().equals(Optional.empty())) {
                    className = storage.searchClassByName(expr.getName().asString());
                } else {
                    className = expr.getScope().get().asString() + "." + expr.getName().asString();
                }
                if (className != null && !list.contains(className)) {
                    list.add(className);
                    //System.out.print(className + ", ");
                }
            }
            if (i.getMetaModel().getTypeName().equals("MethodCallExpr")) {
                MethodCallExpr expr = (MethodCallExpr)i;

                //System.out.print(expr.getScope().get() + ", ");
                String className;
                if (expr.getScope().equals(Optional.empty())) {
                    className = storage.searchClassByName(this.className);
                    if (className != null) {
                        vars.replace("0className", null);
                        String signature = expr.accept(new GetSignatureVisitor(), vars);
                        className = findInheritMethod(className, signature, vars);
                    }
                } else {
                    if (vars.containsKey(expr.getScope().get().toString())) {
                        className = storage.searchClassByName(vars.get(expr.getScope().get().toString()));
                    } else {
                        className = storage.searchClassByName(expr.getScope().get().toString());
                    }
                }
                if (className != null) {
                    vars.replace("0className", className);
                    String signature = expr.accept(new GetSignatureVisitor(), vars);
                    list.add(signature);
                }
            }
            if (i.getChildNodes().size() > 0) {
                List<String> list2 = findNestedDependency(i, vars);
                for (String item : list2) {
                    if (!list.contains(item)) {
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }

    private String findInheritMethod(String className, String methodSignature, Map<String, String> vars) {
        GetSignatureVisitor signatureVisitor = new GetSignatureVisitor();
        String shortClassName = className.replaceAll("^.*\\.([^\\.]+)$", "$1");
        CompilationUnit cu = storage.getCompilationUnit(storage.getPathToFileByClassName(className));
        ClassOrInterfaceDeclaration class1 = cu.getClassByName(shortClassName).get();
        List<MethodDeclaration> methodsList = class1.getMethods();
        //className = "";
        //className += methodSignature;
        //System.out.print(methodSignature + " >>> ");

        for (MethodDeclaration method : methodsList) {
            vars.replace("0className", null);
            //System.out.print(methodSignature + " =>" + method.accept(signatureVisitor, vars) + "\n");
            if (methodSignature.equals(method.accept(signatureVisitor, vars))) {
                return className;
            }
        }
        NodeList<ClassOrInterfaceType> extendedClass = class1.getExtendedTypes();
        if (extendedClass.size() == 1) {
            className = storage.searchClassByName(extendedClass.get(0).getName().asString());
            return findInheritMethod(className, methodSignature, vars);
        }
        return null;
    }
}
