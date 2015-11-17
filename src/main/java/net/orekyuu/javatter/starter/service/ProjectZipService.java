package net.orekyuu.javatter.starter.service;

import net.orekyuu.javatter.starter.controller.StarterForm;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ProjectZipService {

    private static final String PLUGIN_VERSION = "${PluginVersion}";
    private static final String MIN_API_VERSION = "${MinApiVersion}";
    private static final String PLUGIN_ID = "${PluginID}";
    private static final String PLUGIN_NAME = "${PluginName}";
    private static final String PLUGIN_AUTHOR = "${Author}";
    private static final String PLUGIN_AUTHOR_WEB = "${AuthorWeb}";
    private static final String PLUGIN_MAIN = "${MainClass}";
    private static final String PLUGIN_PACKAGE = "${PluginPackage}";
    private static final String DEPENDENCIES = "${Dependencies}";

    public void generate(StarterForm form, OutputStream outputStream) {
        String script = readTempFile(form, "GradleBuildScriptTemp");
        String javaFile = readTempFile(form, "MainClassTemp");

        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(outputStream))){
            {
                ZipEntry entry = new ZipEntry("build.gradle");
                zip.putNextEntry(entry);
                zip.write(script.getBytes("UTF-8"));
                zip.closeEntry();
            }
            {
                String dir = "src/main/java/" + form.getProjectPackage().replace(".", "/");
                ZipEntry entry = new ZipEntry(dir + "/" + form.getMainClass() + ".java");
                zip.putNextEntry(entry);
                zip.write(javaFile.getBytes("UTF-8"));
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readTempFile(StarterForm form, String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass()
                .getResourceAsStream("/" + file)))) {

            String dependencies = getDependencies(form);
            return bufferedReader.lines()
                    .map(ProjectZipService.replace(form, dependencies))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String createGradleScript(StarterForm form) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass()
                .getResourceAsStream("/GradleBuildScriptTemp")))) {

            String dependencies = getDependencies(form);
            return bufferedReader.lines()
                    .map(ProjectZipService.replace(form, dependencies))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getDependencies(StarterForm form) {
        StringBuilder builder = new StringBuilder();
        boolean guava = form.isGuava();
        if (guava) {
            builder.append("compile 'com.google.guava:guava:18.0'");
        }
        return builder.toString();
    }

    private static Function<String, String> replace(StarterForm form, String dependencies) {
        return str -> {
            return str.replace(PLUGIN_VERSION, form.getPluginVersion())
                    .replace(MIN_API_VERSION, form.getMinApiVersion())
                    .replace(PLUGIN_ID, form.getPluginId())
                    .replace(PLUGIN_NAME, form.getPluginName())
                    .replace(PLUGIN_AUTHOR, form.getAuthor())
                    .replace(PLUGIN_AUTHOR_WEB, form.getAuthorWeb())
                    .replace(PLUGIN_MAIN, form.getMainClass())
                    .replace(PLUGIN_PACKAGE, form.getProjectPackage())
                    .replace(DEPENDENCIES, dependencies);
        };
    }
}
