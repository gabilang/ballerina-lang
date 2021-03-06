/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.docgen.docs.utils;

import org.ballerinalang.docgen.docs.BallerinaDocConstants;
import org.ballerinalang.docgen.generator.model.ModuleDoc;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.ListBlock;
import org.commonmark.node.Node;
import org.commonmark.node.ThematicBreak;
import org.commonmark.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Util methods used for doc generation.
 */
public class BallerinaDocUtils {

    private static final boolean debugEnabled = "true".equals(System.getProperty(
            BallerinaDocConstants.ENABLE_DEBUG_LOGS));
    private static final PrintStream out = System.out;

    /**
     * Parse a given markdown.
     *
     * @param mdContent content
     * @return Node parse tree
     */
    public static Node parseMD(String mdContent) {
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).enabledBlockTypes(new HashSet<>(Arrays.asList(Heading
                .class, HtmlBlock.class, ThematicBreak.class, FencedCodeBlock.class, BlockQuote.class, ListBlock
                .class))).build();
       return parser.parse(mdContent != null ? mdContent.trim() : "");
    }

    /**
     * Load primitive types of Ballerina.
     *
     * @param filterDescription is filter.
     * @return a lit of primitive types and their corresponding descriptions.
     */
    public static List<String> loadPrimitivesDescriptions(boolean filterDescription) {
        List<String> list = new ArrayList<>();
        String filename = "/primitives-descriptions.properties";

        InputStream inputStream = BallerinaDocUtils.class.getResourceAsStream(filename);

        try (Stream<String> stream = readFromInputStream(inputStream).stream()) {
            list = stream.map(line -> {
                if (filterDescription) {
                    return line.split("=")[0];
                }
                return line;
            }).collect(Collectors.toList());

        } catch (IOException | FileSystemNotFoundException e) {
            // TODO handle
            return list;
        }

        return list;
    }

    private static List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void packageToZipFile(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            //Ignore
                        }
                    });
        }
    }

    
    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static String getSummary(Path descriptionPath) throws IOException {
        if (descriptionPath != null) {
            String mdContent = new String(Files.readAllBytes(descriptionPath), "UTF-8");
            Node document = BallerinaDocUtils.parseMD(mdContent);
            ModuleDoc.SummaryVisitor summaryVisitor = new ModuleDoc.SummaryVisitor();
            document.accept(summaryVisitor);
            return summaryVisitor.getSummary();
        }
        return null;
    }

    public static String getPrimitiveDescription(List<String> descriptions, String type) {
        // name=description
        Optional<String> desc = descriptions.stream().filter(description -> description.startsWith(type)).map
                (description -> description.split("=")[1]).findFirst();
        return desc.isPresent() ? desc.get() : "";
    }

    /**
     * Visits a folder recursively and copy folders and files to a target directory.
     */
    static class RecursiveFileVisitor extends SimpleFileVisitor<Path> {
        Path source;
        Path target;

        public RecursiveFileVisitor(Path aSource, Path aTarget) {
            this.source = aSource;
            this.target = aTarget;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path targetdir = target.resolve(source.relativize(dir).toString());
            try {
                Files.copy(dir, targetdir);
            } catch (FileAlreadyExistsException e) {
                if (!Files.isDirectory(targetdir)) {
                    throw e;
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.copy(file, target.resolve(source.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
            if (BallerinaDocUtils.isDebugEnabled()) {
                out.println("File copied: " + file.toString());
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
