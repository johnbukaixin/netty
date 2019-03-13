package com.ptl.poi.demo.test;

import com.alibaba.fastjson.JSONArray;
import com.ptl.poi.demo.model.EasyExcelStudent;
import com.ptl.poi.demo.util.ExcelUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * created by panta on 2019/3/13.
 *
 * @author panta
 */
public class EasyExcelTest {

    public static void main(String[] args) throws IOException {
        /**
         * 模拟100W条数据,存入JsonArray,此处使用fastJson(号称第一快json解析)快速解析大数据量数据
         * 至于容量问题,Java数组的length必须是非负的int，所以它的理论最大值就是java.lang.Integer.MAX_VALUE = 2^31-1 = 2147483647。
         * 由于xlsx最大支持行数为1048576行,此处模拟了1048573调数据,剩下的3条占用留给自定义的excel的头信息和列项.
         */
        // int count = 100000;
        // int count = 1000000;
        int count = 1048573;
        JSONArray studentArray = new JSONArray();
        for (int i = 0; i < count; i++) {
            EasyExcelStudent s = new EasyExcelStudent();
            s.setName("POI-" + i);
            s.setAge(i);
            s.setBirthday(new Date());
            s.setHeight(i);
            s.setWeight(i);
            s.setSex(i % 2 == 0 ? false : true);
            studentArray.add(s);
        }

        File file = new File("D://ExcelExportDemo/");
        if (!file.exists()) file.mkdirs();// 创建该文件夹目录
        OutputStream os = null;
        try {
            System.out.println("正在导出xlsx...");
            long start = System.currentTimeMillis();
            // .xlsx格式
            os = new FileOutputStream(file.getAbsolutePath() + File.separator + start + ".xlsx");
            ExcelUtil.easyExcelExport(studentArray,os);
            System.out.println("导出完成...共" + count + "条数据,用时" + (System.currentTimeMillis() - start) + "毫秒");
            System.out.println("文件路径：" + file.getAbsolutePath() + File.separator + start + ".xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert os != null;
            os.close();
        }

    }
}
