package com.pay.payment.center.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyWriteTest {


    /**
     * 拦截器形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 创建一个style策略 并注册
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void handlerStyleWrite() {
        String fileName = TestFileUtil.getPath() + "handlerStyleWrite" + System.currentTimeMillis() + ".xlsx";


        //数据列表
        List<List<String>> dataList = new ArrayList<>();
        //表头
        List<List<String>> header = new ArrayList<>();

        List<String> cellContain1 = new ArrayList<>();
        cellContain1.add("EMPLOYEE PAYSLIP SCHEDULE");
        cellContain1.add("EMPLOYEE DETAILS");
        cellContain1.add("Employee Name");

        header.add(cellContain1);


        List<String> cellContain2 = new ArrayList<>();
        cellContain2.add("EMPLOYEE PAYSLIP SCHEDULE");
        cellContain2.add("EMPLOYEE DETAILS");
        cellContain2.add("Designation/Department");


        header.add(cellContain2);

        List<String> cellContain3 = new ArrayList<>();
        cellContain3.add("EMPLOYEE PAYSLIP SCHEDULE");
        cellContain3.add("EMPLOYEE DETAILS");
        cellContain3.add("Email Address");



        header.add(cellContain3);

        List<String> cellContain4 = new ArrayList<>();
        cellContain4.add("EMPLOYEE PAYSLIP SCHEDULE");
        cellContain4.add("EMPLOYEE DETAILS");

        cellContain4.add("Pension Fund Administrator");


        header.add(cellContain4);

        List<String> cellContain5 = new ArrayList<>();
        cellContain5.add("EMPLOYEE PAYSLIP SCHEDULE");
        cellContain5.add("EMPLOYEE DETAILS");
        cellContain5.add("RSA Pin");

        header.add(cellContain5);

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为白色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);


        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

//        List<String> cellContain2 = new ArrayList<>();
//        cellContain2.add("EMPLOYEE PAYSLIP SCHEDULE");
//        cellContain2.add("GROSS EMOLUMENTS");
//
//        cellContain2.add("Basic Allowance");
//        cellContain2.add("Housing Allowance");
//        cellContain2.add("Transport Allowance");
//        cellContain2.add("Other Allowance");
//        cellContain2.add("Bonus/Other Allowance");



//        List<String> cellContain3 = new ArrayList<>();
//        cellContain3.add("EMPLOYEE PAYSLIP SCHEDULE");
//        cellContain3.add("锦江区");
//        header.add(cellContain3);
//
//        List<String> cellContain4 = new ArrayList<>();
//        cellContain4.add("EMPLOYEE PAYSLIP SCHEDULE");
//        cellContain4.add("青羊区");
//        header.add(cellContain4);


        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(header).registerWriteHandler(horizontalCellStyleStrategy).sheet("TEST")

                .doWrite(dataList);



//        // 头的策略
//        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
//        // 背景设置为白色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//        WriteFont headWriteFont = new WriteFont();
//        headWriteFont.setFontHeightInPoints((short) 10);


//        headWriteCellStyle.setWriteFont(headWriteFont);
//        //设置 自动换行
////        headWriteCellStyle.setWrapped(true);
//        //设置 垂直居中
////        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
////        //设置 水平居中
//        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        //设置边框样式
//        headWriteCellStyle.setBorderLeft(DASHED);
//        headWriteCellStyle.setBorderTop(DASHED);
//        headWriteCellStyle.setBorderRight(DASHED);
//        headWriteCellStyle.setBorderBottom(DASHED);
//
//
//
//        // 内容的策略
//        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//        WriteFont contentWriteFont = new WriteFont();
//        // 字体大小
//        contentWriteFont.setFontHeightInPoints((short) 20);
//        contentWriteCellStyle.setWriteFont(contentWriteFont);
//        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
//        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
//                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
//                .doWrite(data());
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

}
