package Tests;
import DDD.LoginPage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFromExcel extends BaseTest {

    static FileInputStream FIS = null;

    public static FileInputStream getFileInputStream(){
        String filepath = "D:\\mahrousxlsx.xlsx";
        File srcfile = new File(filepath);
        try {
            FIS = new FileInputStream(srcfile);
        } catch (FileNotFoundException e) {
            System.out.println("Error ,occured : " + e.getMessage());
        }

        return FIS;
    }
    @BeforeClass
    @DataProvider(name = "Excel")
    public static Object[][] getExcelData() throws IOException {
        FIS   = getFileInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(FIS);
        XSSFSheet sheet = wb.getSheetAt(0);
        //getLastRowNum zero based and i need start from index 1
        int TotalNumberOfRows = sheet.getLastRowNum();

        String[][] arrayList = new String[TotalNumberOfRows][2];
        for(int i = 1 ; i <= TotalNumberOfRows; i++ ){
            XSSFRow row = sheet.getRow(i);
            for(int j = 0 ; j < 2 ; j++)
            {
                //Array zero based
             arrayList[i-1][j] = row.getCell(j).toString();
            }

        }
        wb.close();

        return arrayList;
    }

    @Test(dataProvider = "Excel")
    public  void  Display(String email , String password) throws IOException {
        LoginPage loginPage = new LoginPage(driver);
        System.out.println(email + "  " +  password);
        loginPage.seteMail(email);
        loginPage.setPassword(password);
        loginPage.clickOnSignIn();
        loginPage.clearFields();
    }
}
