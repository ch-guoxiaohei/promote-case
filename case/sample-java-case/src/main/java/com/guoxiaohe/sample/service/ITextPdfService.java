package com.guoxiaohe.sample.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ITextPdfService {

    private final static Logger logger = LoggerFactory.getLogger(ITextPdfService.class);

    public static void convertPicToPdf(String picPath, String pdfPath) throws IOException {
        // 创建PDF文档
        PdfDocument pdf = new PdfDocument(new PdfWriter(pdfPath));
        Document document = new Document(pdf);
        // 创建图片对象
        Image image = new Image(ImageDataFactory.create(picPath));
        // 设置图片大小适应页面（可选）
        image.scaleToFit(pdf.getDefaultPageSize().getWidth() - 50, pdf.getDefaultPageSize().getHeight() - 50);
        // 添加图片到文档
        document.add(image);
        // 关闭文档
        document.close();
    }

    public static void readPdf(String pdfPath) throws IOException {
        try (PdfReader reader = new PdfReader(pdfPath);
             PdfDocument pdfDoc = new PdfDocument(reader)) {
            // 使用高级文本提取策略
            LocationTextExtractionStrategy strategy = new LocationTextExtractionStrategy();
            PdfCanvasProcessor parser = new PdfCanvasProcessor(strategy);
            // 处理第一页
            parser.processPageContent(pdfDoc.getFirstPage());
            // 获取提取的文本
            String text = strategy.getResultantText();
            System.out.println("提取的文本内容:");
            System.out.println(text);

        } catch (IOException e) {
            System.err.println("读取PDF失败: " + e.getMessage());
        }
    }


    public static void ocrReadPic(String pdfPath) throws IOException {
        // windows 可以从github下载tessdata 解压到指定目录,linux 需要额外下载npm 包并下载language 包
        // 下载地址: https://github.com/tesseract-ocr/tessdata
        File imageFile = new File(pdfPath);
        Tesseract tesseract = new Tesseract();
        try {
            // 设置Tesseract数据路径和语言
            tesseract.setDatapath("");
            tesseract.setLanguage("eng+chi_sim");
            String result = tesseract.doOCR(imageFile);
            System.out.println("识别结果:");
            System.out.println(result);

        } catch (TesseractException e) {
            System.err.println("OCR识别错误: " + e.getMessage());
        }
    }
}
