package com.github.classyex.pdftool;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class WordDocumentCombiner {

    private final String docPath;
    private final String desPath;

    public WordDocumentCombiner(String docPath, String desPath) {
        this.docPath = docPath;
        this.desPath = desPath;
    }

    public boolean merge() {
        try {
            System.out.println("开始合并文档");
            Instant start = Instant.now();
            File[] fs = FileUtils.getSplitFiles(docPath);
            Document document = new Document(docPath + "test0.docx");
            for (int i = 1; i < fs.length; i++) {
                System.out.println(String.format("合并第%s个文档", i + 1));
                document.insertTextFromFile(docPath + "test" + i + ".docx", FileFormat.Docx_2013);
            }
            document.saveToFile(desPath);
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            System.out.println(String.format("合并文档耗时：%s ms ", duration.toMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
