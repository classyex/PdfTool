package com.github.classyex.pdftool;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import java.io.File;
import java.time.Duration;
import java.time.Instant;


public class PdfToWordConverter {
    String splitPath = "./split_temp_SIX296S/";
    String docPath = "./doc_temp_SIX296S/";

    public String pdfToWord(String srcPath) {
        try {
            if (!FileUtils.isPdfFile(srcPath)) {
                return "输入的不是pdf文件";
            }
            if (initTempDir() && coverPdfToWord(srcPath)) {
                return "转换成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearTempDir();
        }
        return "转换失败";
    }

    private boolean coverPdfToWord(String srcPath) {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(srcPath);
        if (pdf.getPages().getCount() <= 10) {
            pdf.saveToFile(desPath(srcPath), FileFormat.DOCX);
            return true;
        }
        splitPdfByPage(pdf);
        coverPdfToDocs();
        return mergeDocs(desPath(srcPath));
    }

    private boolean initTempDir() {
        FileUtils.initTempDir(splitPath);
        FileUtils.initTempDir(docPath);
        return true;
    }

    private void clearTempDir() {
        FileUtils.clearFiles(splitPath);
        FileUtils.clearFiles(docPath);
    }

    private boolean mergeDocs(String desPath) {
        return new WordDocumentCombiner(docPath, desPath).merge();
    }

    private void splitPdfByPage(PdfDocument pdf) {
        System.out.println("开始按页切分PDF");
        Instant start = Instant.now();
        pdf.split(splitPath + "test{0}.pdf", 0);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(String.format("拆分每页耗时：%s ms ", duration.toMillis()));
    }

    private void coverPdfToDocs() {
        System.out.println("开始将转换doc");
        Instant start = Instant.now();
        File[] splitFiles = FileUtils.getSplitFiles(splitPath);
        PdfDocument pdfDocument = new PdfDocument();
        for (int i = 0; i < splitFiles.length; i++) {
            System.out.println(String.format("转换第%s页", i + 1));
            pdfDocument.loadFromFile(splitFiles[i].getAbsolutePath());
            pdfDocument.saveToFile(docPath + changeSuffixToDocx(splitFiles[i].getName()), FileFormat.DOCX);
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(String.format("转换doc耗时：%s ms ", duration.toMillis()));
    }

    private String desPath(String srcPath) {
        return changeSuffixToDocx(srcPath);
    }

    private String changeSuffixToDocx(String srcPath) {
        return srcPath.substring(0, srcPath.length() - 4) + ".docx";
    }





}
