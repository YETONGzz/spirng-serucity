package com.yetong.controller;

import com.yetong.dao.RuleNoMapper;
import com.yetong.entity.RuleNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    RuleNoMapper ruleNoMapper;

    @RequestMapping("/ge")
    public void ge() {
        Map<String, String> map = new HashMap<>();
        map.put("date", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        System.out.println(getNo("PH", map));
    }

    public String getNo(String seqId, Map<String, String> templateMap) {
        RuleNo info = ruleNoMapper.selectById("PH连");
        String rule = info.getRule();
        String currentVal = info.getCurrentVal();
        String step = info.getStep();
        String digit = info.getDigit();
        String newVal = Integer.valueOf(currentVal) + Integer.valueOf(step) + "";
        info.setCurrentVal(newVal);
        ruleNoMapper.updateById(info);
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(rule);
        while (matcher.find()) {
            String token = matcher.group(0);
            String key = matcher.group(1);
            String value = null;
            if ("seq".equals(key)) {
                value = String.format("%0" + digit + "d", Integer.valueOf(newVal));
            } else {
                String s = templateMap.get(key);
                value = s;
            }

            if (value != null) {
                rule = rule.replace(token, value.toString());
            }
        }
        return rule;
    }

}
