package com.gitee.sop.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成_sidebar.md文件，直接运行即可
 *
 * @author tanghc
 */
public class SidebarTest {

    static String format = "  * [%s](files/%s?t=%s)\r\n";
    static Map<String, Menu> levelMap = new HashMap<>(8);

    static {
        int i = 0;
        levelMap.put("1", new Menu("* 开发文档\n", i++));
        levelMap.put("9", new Menu("* 原理分析\n", i++));
    }

    public static void main(String[] args) throws Exception {
        String path = SidebarTest.class.getClassLoader().getResource("").getPath();
        String root = path.substring(0, path.indexOf("doc")) + "doc";
        String fileDir = root + "/docs/files";
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        Stream<File> filesStream = Stream.of(files);
        Map<String, List<FileExt>> menuMap = filesStream
                .sorted(Comparator.comparing(File::getName))
                .map(file -> {
                    if (file.isDirectory()) {
                        return null;
                    }
                    FileExt fileExt = new FileExt();
                    fileExt.menu = file.getName().substring(0, 1);
                    fileExt.file = file;
                    return fileExt;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(FileExt::getMenu));

        StringBuilder output = new StringBuilder();
        output.append("* [首页](/?t=" + System.currentTimeMillis() + ")\n");
        for (Map.Entry<String, List<FileExt>> entry : menuMap.entrySet()) {
            Menu menu = levelMap.get(entry.getKey());
            output.append(menu.parentName);
            for (FileExt fileExt : entry.getValue()) {
                String filename = fileExt.file.getName();
                String title = filename.substring(filename.indexOf("_") + 1, filename.length() - 3);
                String line = String.format(format, title, filename, System.currentTimeMillis());
                output.append(line);
            }
        }

        System.out.println(output);

        String sidebarFilepath = root + "/docs/_sidebar.md";

        FileOutputStream out = new FileOutputStream(new File(sidebarFilepath));
        out.write(output.toString().getBytes());
        out.close();
    }

    static class Menu {
        String parentName;
        int order;

        public Menu(String parentName, int order) {
            this.parentName = parentName;
            this.order = order;
        }

    }

    static class FileExt {
        File file;
        String menu;

        public File getFile() {
            return file;
        }

        public String getMenu() {
            return menu;
        }
    }
}
