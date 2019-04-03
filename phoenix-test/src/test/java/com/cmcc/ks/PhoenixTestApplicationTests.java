package com.cmcc.ks;

import com.cmcc.ks.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoenixTestApplicationTests {

    @Autowired
    @Qualifier("phoenixJdbcTemplate")
    private JdbcTemplate phoenixJdbcTemplate;

    public static final String PATH_PREFIX = "C:/tmp/quality_sys/";
    public static final String SUCCESS_RATE_FILE_NAME = "grail_play_success_rate_1d_delta_daily.xlsx";



    @Test
    public void testJdbcTemplate() {
        List<Map<String, Object>> list = phoenixJdbcTemplate.queryForList("select * from  STOCK_SYMBOL limit 10");
        System.out.println(list);
    }

    @Test
    public void testList() {
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = new ArrayList<>();
        String cellData = null;
        String filePath = FileName.SUCCESS_RATE.fileName;
        String columns[] = FileName.SUCCESS_RATE.columns;
        Workbook wb = ExcelUtils.readExcel(filePath);

        if (wb != null) {
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(0);
            int rownum = sheet.getPhysicalNumberOfRows();
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) ExcelUtils.getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }

        int i_list = 0;
        if (i_list++ < 10) {

            for (Map<String, String> map : list) {
                StringBuilder keys = new StringBuilder(), values = new StringBuilder("\'");
                int i = 0, j = 0;
                for (String key : map.keySet()) {
                    if (i++ == 0) {
                        keys.append(key);
                    } else {
                        keys.append("," + key);
                    }
                }
                for (String val : map.values()) {
                    if (j++ == 0) {
                        values.append(val);
                    } else {
                        values.append("\',\'").append(val);
                    }
                }
                values.append("\'");
                System.out.println(String.format(FileName.SUCCESS_RATE.prefixUpsert, keys.toString(), values.toString()));
//            phoenixJdbcTemplate.execute(String.format(FileName.SUCCESS_RATE.prefixUpsert, keys, values));
            }
        }

    }


    private enum FileName {
        SUCCESS_RATE(PATH_PREFIX.concat(SUCCESS_RATE_FILE_NAME), new String[]{"branch", "product", "os", "app_version",
                "network_type", "ip_prov_id", "error_code", "error_total", "play_total", "dt"},
//                "upsert into grail_play_success_rate_d (%s) values (%s)");
                "upsert into grail_play_success_rate_d (%s) values (%s)");

        private final String fileName;
        private final String[] columns;
        private final String prefixUpsert;

        FileName(String fileName, String[] columns, String prefixUpsert) {
            this.columns = columns;
            this.fileName = fileName;
            this.prefixUpsert = prefixUpsert;
        }

        public static String[] getColumns(String fileName) {
            for (FileName name : FileName.values()) {
                if (name.fileName.equals(fileName)) {
                    return name.columns;
                }
            }
            return null;
        }
    }

}
