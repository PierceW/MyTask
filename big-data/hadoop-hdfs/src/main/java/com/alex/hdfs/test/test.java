package com.alex.hdfs.test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName test
 * @Description todo
 * @Author AlexWang
 * @Date 2019/5/30 0030 15:27
 * @Version 1.0
 **/

public class test {
    public static  void main(String[] args) {
        List<TestDTO> testDTOS = new ArrayList<>();
        for (int i=0; i<20; i++) {
            TestDTO testDTO = new TestDTO();
            testDTO.setGateNumber(new Random(i).nextInt(46));
            testDTO.setAnswerNumber(new Random(i).nextInt(38));
            testDTOS.add(testDTO);
        }
        List<TestDTO> result = testDTOS.stream().sorted(Comparator.comparing((TestDTO t) -> t.getGateNumber()).reversed()).collect(Collectors.toList());

        Map<Integer, List<TestDTO>> listMap = new LinkedHashMap<>();
        for (TestDTO dto : result) {
            List<TestDTO> list = new ArrayList<>();
            if (listMap.containsKey(dto.getGateNumber())) {
                list = listMap.get(dto.getGateNumber());
            }
            list.add(dto);
            listMap.put(dto.getGateNumber(), list);
        }

        List<TestDTO> resultList = new ArrayList<>();
        for (List<TestDTO> testList : listMap.values()) {
            List<TestDTO> sortList = testList.stream().sorted(Comparator.comparing((TestDTO t) -> t.getAnswerNumber()).reversed()).collect(Collectors.toList());
            resultList.addAll(sortList);
        }

        for (TestDTO testDTO : resultList) {
            System.out.println(testDTO.getGateNumber() + " ---- " + testDTO.getAnswerNumber());
        }

        System.out.println("----------------------------------------------------------");

        String tempStr = "";
        int sortNum = 1;
        for (TestDTO rankDTO : resultList) {
            String gateAns = rankDTO.getGateAnswer();

            if (tempStr.isEmpty()) {
                rankDTO.setSort(sortNum);
                tempStr = rankDTO.getGateAnswer();
            } else {

                if (gateAns.equals(tempStr)) {
                    rankDTO.setSort(sortNum);
                } else {
                    sortNum++;
                    rankDTO.setSort(sortNum);
                    tempStr = rankDTO.getGateAnswer();
                }
            }
        }
        System.out.println("----------------------------------------------------------");

        for (TestDTO testDTO : resultList) {
            System.out.println(testDTO.getSort() + " ----  " + testDTO.getGateNumber() + " ----  " + testDTO.getAnswerNumber());
        }
    }

}
