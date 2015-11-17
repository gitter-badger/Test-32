package net.orekyuu.javatter.starter.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class StarterForm {

    @NotNull(message = "Packageは必須項目です。")
    @Size(min = 1, max = 128, message = "Packageは{max}文字以下で入力してください。")
    private String projectPackage;
    @NotNull(message = "MainClassは必須項目です。")
    @Size(min = 1, max = 128, message = "MainClassは{max}文字以下で入力してください。")
    private String mainClass;
    @NotNull(message = "PluginIDは必須項目です。")
    @Size(min = 1, max = 128, message = "PluginIDは{max}文字以下で入力してください。")
    private String pluginId;
    @NotNull(message = "PluginNameは必須項目です。")
    @Size(min = 1, max = 128, message = "PluginNameは{max}文字以下で入力してください。")
    private String pluginName;
    @NotNull(message = "Authorは必須項目です。")
    @Size(min = 1, max = 128, message = "Authorは{max}文字以下で入力してください。")
    private String author;
    @Pattern(regexp = "|https?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]{1,256}", message = "AuthorWebは256文字以下の正しい形式で入力してください。")
    private String authorWeb;
    @NotNull(message = "PluginVersionは必須項目です。")
    @Size(min = 1, max = 128, message = "PluginVersionは{max}文字以下で入力してください。")
    private String pluginVersion;
    @NotNull(message = "MinApiVersionは必須項目です。")
    @Size(min = 1, max = 128, message = "MinApiVersionは{max}文字以下で入力してください。")
    private String minApiVersion;

    private boolean guava;

    public String getMinApiVersion() {
        return minApiVersion;
    }

    public void setMinApiVersion(String minApiVersion) {
        this.minApiVersion = minApiVersion;
    }

    public String getProjectPackage() {
        return projectPackage;
    }

    public void setProjectPackage(String projectPackage) {
        this.projectPackage = projectPackage;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorWeb() {
        return authorWeb;
    }

    public void setAuthorWeb(String authorWeb) {
        this.authorWeb = authorWeb;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public boolean isGuava() {
        return guava;
    }

    public void setGuava(boolean guava) {
        this.guava = guava;
    }
}
