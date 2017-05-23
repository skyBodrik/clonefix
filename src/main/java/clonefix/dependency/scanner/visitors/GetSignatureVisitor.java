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

import java.util.Map;
import java.util.Optional;

public class GetSignatureVisitor implements GenericVisitor<String, Map<String, String>> {

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

    public String visit(MethodDeclaration method, Map<String, String> vars) {
        GetTypeExpressionVisitor typeExpressionVisitor = new GetTypeExpressionVisitor();
        NodeList<Parameter> args = method.getParameters();
        StringBuffer signature = new StringBuffer();
        String className = storage.searchClassByName(vars.get("0className"));
        if (className != null) {
            signature.append(className + ".");
        }
        signature.append(method.getNameAsString() + "(");
        int i = 0;
        for (Parameter arg : args) {
            i++;
            signature.append(arg.getChildNodes().get(0).accept(typeExpressionVisitor, vars));
            if (i < args.size()) {
                signature.append(", ");
            }
        }
        signature.append(")");
        return signature.toString();
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

    public String visit(ClassOrInterfaceType classOrInterfaceType, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(PrimitiveType primitiveType, Map<String, String> stringStringMap) {
        return null;
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
        return null;
    }

    public String visit(IntegerLiteralExpr integerLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(LongLiteralExpr longLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(CharLiteralExpr charLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(DoubleLiteralExpr doubleLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(BooleanLiteralExpr booleanLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(NullLiteralExpr nullLiteralExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(MethodCallExpr expr, Map<String, String> vars) {
        NodeList<Expression> args = expr.getArguments();
        StringBuffer signature = new StringBuffer();
        String className = storage.searchClassByName(vars.get("0className"));
        if (className != null) {
            signature.append(className + ".");
        }
        signature.append(expr.getName() + "(");
        int i = 0;
        for (Expression arg : args) {
            i++;
            signature.append(arg.accept(new GetTypeExpressionVisitor(), vars));
            if (i < args.size()) {
                signature.append(", ");
            }
        }
        signature.append(")");
        return signature.toString();
    }

    public String visit(NameExpr nameExpr, Map<String, String> stringStringMap) {
        return null;
    }

    public String visit(ObjectCreationExpr objectCreationExpr, Map<String, String> stringStringMap) {
        return null;
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
