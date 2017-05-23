package clonefix.dependency.scanner;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Storage {
    private static Storage ourInstance = new Storage();

    private Map<String, CompilationUnit> compilationUnits = new HashMap<String, CompilationUnit>();

    private String rootPath = null;

    private List<File> nestedFiles = new ArrayList<File>();

    private List<String> nestedClasses = new ArrayList<String>();

    private List<String> packagesList = new ArrayList<String>();

    private Map<String, List<String>> dependenciesMap = new HashMap<String, List<String>>();

    public static Storage getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    private void findNestedFiles() {
        findNestedFiles(rootPath);
    }

    private void findNestedFiles(String path) {
        File root = new File(path);
        if (!root.isDirectory()) {
            return;
        }
        Pattern sourceJavaPattern = Pattern.compile("^.*\\.java$");
        File[] fileList = root.listFiles();
        for (File file : fileList) {
            if (file.isDirectory()) {
                findNestedFiles(file.getPath());
            }
            if (file.isFile() && sourceJavaPattern.matcher(file.getName()).matches()) {
                nestedFiles.add(file);
            }
        }
    }

    private void findNestedClasses(CompilationUnit cu) {
        List<Node> nodeList = cu.getChildNodes();
        String packageName = cu.getPackageDeclaration().get().getName().asString();
        for (Node node : nodeList) {
            if (node.getMetaModel().getTypeName().equals("ClassOrInterfaceDeclaration")) {
                String className = node.getChildNodes().get(0).toString();
                if (node.getChildNodes().get(0).getMetaModel().getTypeName().equals("SingleMemberAnnotationExpr")) {
                    List<Node> childNodes = node.getChildNodes();
                    for (Node childNode : childNodes) {
                        if (childNode.getMetaModel().getTypeName().equals("SimpleName")) {
                            className = childNode.toString();
                            break;
                        }
                    }
                }
                //System.out.print(packageName + "." + className + "\n");
                if (!Pattern.matches("^@.*$", className)) {
                    nestedClasses.add(packageName + "." + className);
                }
            }
        }
    }

    public void refresh(String rootPath) throws IOException {
        this.rootPath = rootPath;
        findNestedFiles();
        try {
            for (File file : nestedFiles) {
                StringBuilder sourceCode = new StringBuilder();
                FileReader rdr = new FileReader(file.getAbsolutePath());
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
                CompilationUnit cu = JavaParser.parse(sourceCode.toString());
                compilationUnits.put(file.getAbsolutePath(), cu);
                findNestedClasses(cu);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchClassByName(String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        for (String nestedClassName : nestedClasses) {
            for (String packageName : packagesList) {
                if (nestedClassName.equals(className) || nestedClassName.equals(packageName + "." + className)) {
                    return nestedClassName;
                }
            }
        }
        return null;
    }

    public String getPathToFileByClassName(String className) {
        String[] chunks = className.split("\\.");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chunks.length; i++) {
            buffer.append(chunks[i]);
            if (i < chunks.length - 1) {
                buffer.append("/");
            }
        }
        buffer.append(".java");
        return getRootPath() + buffer.toString();
    }

    public String getJsonDependenciesMap() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\n");
        for(Map.Entry<String, List<String>> entry : dependenciesMap.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            buffer.append("    \"" + key + "\": {\n         ");
            int i = 1;
            for(String item : list) {
                buffer.append("\"" + i + "\": " + "\"" + item + "\"");
                if (i++ < list.size()) {
                    buffer.append(", \n         ");
                }
            }
            buffer.append("\n   }, \n");
        }
        buffer.append("}");
        return buffer.toString();
    }

    public CompilationUnit getCompilationUnit(String name) {
        return compilationUnits.get(name);
    }

    public Map<String, CompilationUnit> getCompilationUnits() {
        return compilationUnits;
    }

    public String getRootPath() {
        return rootPath;
    }

    public List<File> getNestedFiles() {
        return nestedFiles;
    }

    public List<String> getNestedClasses() {
        return nestedClasses;
    }

    public Map<String, List<String>> getDependenciesMap() {
        return dependenciesMap;
    }

    public List<String> getPackagesList() {
        return packagesList;
    }
}
