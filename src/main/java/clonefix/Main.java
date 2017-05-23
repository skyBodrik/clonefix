package clonefix;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import clonefix.dependency.scanner.MainScanner;
import clonefix.dependency.scanner.Storage;
import clonefix.resolver.MainResolver;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.metamodel.CompilationUnitMetaModel;
import com.github.javaparser.*;

/**
 * Используется для тестирования
 */

public class Main {

    public static void main(String[] args) {

        String[] argv = {"/home/bodrik/diploma/examples/src/main/java/clonefix/examples/_JavaLexer.flex"};
/*        try {
            jflex.Main.generate(argv);
        } catch (SilentExit e) {
            System.out.print("Silent exit");
        }*/
        try {
            StringBuilder sourceCode = new StringBuilder();
            try {
                FileReader rdr = new FileReader("/home/bodrik/diploma/examples/src/main/java/clonefix/examples/Main.java");
                BufferedReader in = new BufferedReader(rdr);
                String buffer;
                try {
                    while ((buffer = in.readLine()) != null) {
                        sourceCode.append(buffer);
                        sourceCode.append("\n");
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //char[] buff = new char[200];
            //rdr.read(buff, 1, 200);
/*            JavaLexer lexer = new JavaLexer(rdr);
            Token token = lexer.nextToken();*/
/*            token = lexer.nextToken();
            token = lexer.nextToken();
            token = lexer.nextToken();*/
            //System.out.print(token.toString());
            CompilationUnit compilationUnit = JavaParser.parse(sourceCode.toString());
            //System.out.print(imports.get(1).get(3).toString());
            //ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("Client").get();
            List<Node> nodeList = compilationUnit.getChildNodes();
            //System.out.print(nodeList.get(9).getChildNodes().get(0).toString());
/*            ClassOrInterfaceDeclaration class1 = compilationUnit.getClassByName("Main").get();
            NodeList<Parameter> parameters = class1.getMethodsByName("client").get(0).getParameters();
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getName() + "\n");
            }*/
            //System.out.print(compilationUnit.getPackageDeclaration().get().getName());

            //System.out.print(nodeList.get(2).getChildNodes().get(15).getChildNodes().get(2).getChildNodes().get(0).getChildNodes().get(0).getChildNodes().get(0).getChildNodes().get(2).getMetaModel().getType());
/*            AstBuilderTransformation astTransformation = new AstBuilderTransformation();
            ASTFactory factory = new ASTFactory();
            AST rootAst = new CommonAST(token);
            AST ast = rootAst;
            for (int i = 0; i < 10; i++) {
                token = lexer.nextToken();
                AST newAst = factory.create(token);
                ast.addChild(newAst);
                ast = newAst;
            }*/
            //AST ast = new CommonAST(token);
            //System.out.print(rootAst.toStringTree());

            MainScanner scanner = new MainScanner("/home/bodrik/diploma/examples/src/main/java/");
            //scanner.findNestedFiles();
            //scanner.findDependencies(compilationUnit);
            System.out.print(Storage.getInstance().getJsonDependenciesMap());

            MainResolver resolver = new MainResolver();
            resolver.run();

/*            scanner.findNestedFiles();
            List<File> list = scanner.getNestedFiles();
            for (File file : list) {
                System.out.print(file.getPath() + "\n");
            }*/

/*            IElementType elm;
            elm = lexer.advance();
            lexer.advance();
            lexer.advance();
            lexer.advance();
            System.out.print(elm.toString());*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        };
    }

}
