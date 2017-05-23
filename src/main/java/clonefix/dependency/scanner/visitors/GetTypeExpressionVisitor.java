package clonefix.dependency.scanner.visitors;

import clonefix.dependency.scanner.Storage;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.modules.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.visitor.GenericVisitor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class GetTypeExpressionVisitor implements GenericVisitor<String, Map<String, String>> {

    private Storage storage = Storage.getInstance();

    public String visit(CompilationUnit compilationUnit, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(PackageDeclaration packageDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(TypeParameter typeParameter, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(LineComment lineComment, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(BlockComment blockComment, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(EnumDeclaration enumDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(EnumConstantDeclaration enumConstantDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(AnnotationDeclaration annotationDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(AnnotationMemberDeclaration annotationMemberDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(FieldDeclaration fieldDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(VariableDeclarator variableDeclarator, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ConstructorDeclaration constructorDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MethodDeclaration expr, Map<String, String> vars) {
        return expr.getType().asString();
    }

    public String visit(Parameter parameter, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(EmptyMemberDeclaration emptyMemberDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(InitializerDeclaration initializerDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(JavadocComment javadocComment, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ClassOrInterfaceType expr, Map<String, String> vars) {
        String className;
        if (expr.getScope().equals(Optional.empty())) {
            className = storage.searchClassByName(expr.getName().asString());
        } else {
            className = storage.searchClassByName(expr.getScope().get().toString() + "." + expr.getName().asString());
        }
        return className;
    }

    public String visit(PrimitiveType primitiveType, Map<String, String> stringStringMap) {
        return primitiveType.getType().asString();
    }

    public String visit(ArrayType arrayType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ArrayCreationLevel arrayCreationLevel, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(IntersectionType intersectionType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(UnionType unionType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(VoidType voidType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(WildcardType wildcardType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(UnknownType unknownType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ArrayAccessExpr arrayAccessExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ArrayCreationExpr arrayCreationExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ArrayInitializerExpr arrayInitializerExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(AssignExpr assignExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(BinaryExpr binaryExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(CastExpr castExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ClassExpr classExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ConditionalExpr conditionalExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(EnclosedExpr enclosedExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(FieldAccessExpr fieldAccessExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(InstanceOfExpr instanceOfExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(StringLiteralExpr stringLiteralExpr, Map<String, String> stringStringMap) {
        return "String";
    }

    public String visit(IntegerLiteralExpr integerLiteralExpr, Map<String, String> stringStringMap) {
        return "int";
    }

    public String visit(LongLiteralExpr longLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(CharLiteralExpr charLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(DoubleLiteralExpr doubleLiteralExpr, Map<String, String> stringStringMap) {
        return "double";
    }

    public String visit(BooleanLiteralExpr booleanLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(NullLiteralExpr nullLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MethodCallExpr expr, Map<String, String> vars) {
        GetSignatureVisitor signatureVisitor = new GetSignatureVisitor();
        String signature1 = expr.accept(signatureVisitor, vars);
        String className;
        if (expr.getScope().equals(Optional.empty())) {
            className = "???";
        } else {
            if (vars.containsKey(expr.getScope().get().toString())) {
                className = storage.searchClassByName(vars.get(expr.getScope().get().toString()));
            } else {
                className = storage.searchClassByName(expr.getScope().get().toString());
            }
        }

        if (className != null) {
            return null;
            // Пока заблокируем
/*            String shortClassName = className.replaceAll("^.*\\.([^\\.]+)$", "$1");
            CompilationUnit cu = storage.getCompilationUnit(storage.getPathToFileByClassName(className));
            ClassOrInterfaceDeclaration class1 = cu.getClassByName(shortClassName).get();
            List<MethodDeclaration> methodsList = class1.getMethods();
            for (MethodDeclaration method : methodsList) {
                if (signature1.equals(method.accept(signatureVisitor, vars))) {
                    return method.accept(this, vars);
                }
            }*/
        }
        return className;
    }

    public String visit(NameExpr nameExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ObjectCreationExpr expr, Map<String, String> vars) {
        String className;
        if (expr.getScope().equals(Optional.empty())) {
            className = storage.searchClassByName(expr.getChildNodes().get(0).toString());
        } else {
            className = expr.getScope().get().toString() + "." + expr.getChildNodes().get(0).toString();
        }
        return className;
    }

    public String visit(ThisExpr thisExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SuperExpr superExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(UnaryExpr unaryExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(VariableDeclarationExpr variableDeclarationExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MarkerAnnotationExpr markerAnnotationExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SingleMemberAnnotationExpr singleMemberAnnotationExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(NormalAnnotationExpr normalAnnotationExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MemberValuePair memberValuePair, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ExplicitConstructorInvocationStmt explicitConstructorInvocationStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(LocalClassDeclarationStmt localClassDeclarationStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(AssertStmt assertStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(BlockStmt blockStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(LabeledStmt labeledStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(EmptyStmt emptyStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ExpressionStmt expressionStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SwitchStmt switchStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SwitchEntryStmt switchEntryStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(BreakStmt breakStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ReturnStmt returnStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(IfStmt ifStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(WhileStmt whileStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ContinueStmt continueStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(DoStmt doStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ForeachStmt foreachStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ForStmt forStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ThrowStmt throwStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SynchronizedStmt synchronizedStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(TryStmt tryStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(CatchClause catchClause, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(LambdaExpr lambdaExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MethodReferenceExpr methodReferenceExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(TypeExpr typeExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(NodeList nodeList, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(Name name, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(SimpleName simpleName, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ImportDeclaration importDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleDeclaration moduleDeclaration, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleRequiresStmt moduleRequiresStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleExportsStmt moduleExportsStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleProvidesStmt moduleProvidesStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleUsesStmt moduleUsesStmt, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ModuleOpensStmt moduleOpensStmt, Map<String, String> stringStringMap) {
        return null;
    }
}
