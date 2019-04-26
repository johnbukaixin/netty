package com.ptl.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

/**
 * created by panta on 2019/4/26.
 *
 * @author panta
 */
public class Test {

    public static void main(String[] args) {
        String content = "昨天体检的 今天怎么查不到体检报告 我今年的体检报告没收到推送";
        List<String> keywordList = HanLP.extractKeyword(content, 10);
        System.out.println(keywordList);
        CustomDictionary.add("体检报告");
        List<Term> termList = StandardTokenizer.segment("昨天体检的 今天怎么查不到体检报告");
        System.out.println(termList);

        String content1 = "无法退款";
        List<Term> termList1 = StandardTokenizer.segment(content1);
        System.out.println(termList1);

        String content2 = "你们这开个体检发票怎么跟生孩子一样难产？";
        List<Term> termList2 = StandardTokenizer.segment(content2);
        System.out.println(termList2);

    }
}
