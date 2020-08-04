package com.github.classyex.pdftool;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("请输入pdf文件路径，比如 java -jar pdftool2.jar test.pdf");
			System.exit(0);
		}
		String fileName = args[0];
		System.out.println("输入文件路径为：" + fileName);
		String res = new PdfToWordConverter().pdfToWord(fileName);
		System.out.println(res);
	}
	
}
