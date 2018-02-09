package me.rl24.angtil;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Angular Utility
 * Easy to use module and component generater with template files
 */
public class Angtil {

    /**
     * Different file types generated for components and modules
     */
    private enum FileType {
        MODULE("%s.module.ts"),
        COMPONENT("%s.component.ts"),
        HTML("%s.component.html"),
        SCSS("%s.component.scss"),
        SPEC("%s.component.spec.ts");

        private String type;

        FileType(String type) {
            this.type = type;
        }

        public String getType(String name) {
            return String.format(type, name);
        }
    }

    //Argument specifiers
    private static final char MODULE = 'm';
    private static final char COMPONENT = 'c';

    /**
     * Bootstrapper
     * @param args Launch arguments
     */
    public static void main(String[] args) {
        /*
         * Example:
         * angtil -m=MyModule
         * angtil -c=MyComponent
         */
        for (String arg : args) {
            if (arg.startsWith("-")) {
                String[] split = arg.split("=");
                if (split.length != 2)
                    continue;
                char option = arg.charAt(1);
                String value = arg.substring(3);
                switch (option) {
                    case MODULE:
                        //module
                        generateModule(value);
                        break;
                    case COMPONENT:
                        //component
                        generateComponent(value, false);
                        break;
                }
            }
        }
    }

    /**
     * Generate a module with the given module name
     * @param moduleName The name of the module
     */
    private static void generateModule(String moduleName) {
        String MODULE = moduleName;
        String MODULE_NAME = moduleName.toLowerCase();

        try {
            createDirectory(MODULE_NAME);
            generateFile(MODULE_NAME, MODULE_NAME, FileType.MODULE, new String[]{
                    "%MODULE%", MODULE,
                    "%MODULE_NAME%", MODULE_NAME
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateComponent(moduleName, true);
    }

    /**
     * Generate a component with the given component name
     * @param componentName The name of the component
     * @param isModule      Is the component a result of a module generation
     */
    private static void generateComponent(String componentName, boolean isModule) {
        String COMPONENT = componentName;
        String COMPONENT_NAME = componentName.toLowerCase();
        String COMPONENT_FILE = String.format("./%s.component", COMPONENT_NAME);
        String COMPONENT_SELECTOR = String.format("%s-%s", isModule ? "mod" : "com", COMPONENT_NAME);

        try {
            createDirectory(COMPONENT_NAME);
            FileType[] types = {
                    FileType.COMPONENT,
                    FileType.HTML,
                    FileType.SCSS,
                    FileType.SPEC,
            };
            for (FileType type : types)
                generateFile(COMPONENT_NAME, COMPONENT_NAME, type, new String[]{
                        "%COMPONENT%", COMPONENT,
                        "%COMPONENT_NAME%", COMPONENT_NAME,
                        "%COMPONENT_FILE%", COMPONENT_FILE,
                        "%COMPONENT_SELECTOR%", COMPONENT_SELECTOR
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a file in the given directory with the given name
     * @param directory    The desired directory to create the file in
     * @param fileName     The output file name
     * @param type         The type of the file
     * @param replacements All text replacements to replace in the text file (requires an even number of entries, 0, 2, 4, etc)
     * @throws IOException If the output is unsuccessful
     */
    private static void generateFile(String directory, String fileName, FileType type, String[] replacements) throws IOException {
        StringBuilder string = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(Angtil.class.getResourceAsStream(String.format("/%s", type.getType("").substring(1)))));
        String line;
        while ((line = br.readLine()) != null)
            string.append(line).append("\n");
        String output = string.toString();
        for (int i = 0; i < replacements.length; i+= 2)
            output = output.replaceAll(replacements[i], replacements[i + 1]);
        writeFile(String.format("./%s/%s", directory, type.getType(fileName)), output);
    }

    /**
     * Create a directory if it doesn't already exist
     * @param directory The desired directory name
     * @throws IOException If the creation is unsuccessful
     */
    private static void createDirectory(String directory) throws IOException {
        if (!new File(directory).exists())
            Files.createDirectory(Paths.get(directory));
    }

    /**
     * Write content to a file
     * @param path    The desired path of the file
     * @param content The content to write
     * @throws IOException If the creation is unsuccessful
     */
    private static void writeFile(String path, String content) throws IOException {
        FileUtils.writeStringToFile(new File(path), content, false);
    }

}
