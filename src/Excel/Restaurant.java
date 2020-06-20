package Excel;

import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Restaurant {

	public static String[] rNameList = new String[9999];
	public static int[] telList = new int[9999];
	public static String[] addressList = new String[9999];
	public static String[] categoryList = new String[9999];

	public static int createArray() {
		String path = System.getProperty("user.dir");
		int cnt=0;
		try {
			FileInputStream file = new FileInputStream(path + "/Schema.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			// 시트 수 (첫번째에만 존재하므로 0을 준다)
			// 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			XSSFSheet sheet = workbook.getSheetAt(0); //첫번재시트는 Restaurant
			// 행의 수
			int rows = sheet.getPhysicalNumberOfRows(); cnt = rows-1;
			for (rowindex = 0; rowindex < rows; rowindex++) {

				// 행을읽는다
				XSSFRow row = sheet.getRow(rowindex);

				// 행에서 읽어올 변수들
				String rName = null;
				int tel = 0;
				String address = null;
				String category = null;

				if (row != null) {
					// 읽은 행의 셀(열)의 수
					int cells = row.getPhysicalNumberOfCells();
					for (columnindex = 0; columnindex <= cells; columnindex++) {

						// 셀값을 읽는다
						XSSFCell cell = row.getCell(columnindex);
						String value = "";
						// 셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							// 타입별로 내용 읽기
							switch (cell.getCellType()) {
							case FORMULA:
								value = cell.getCellFormula();
								break;
							case NUMERIC:// 실수형 데이터가 정수형으로 나오도록
								value = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
								// value=cell.getNumericCellValue()+"";
								break;
							case STRING:
								value = cell.getStringCellValue() + "";
								break;
							case BLANK:
								value = cell.getBooleanCellValue() + "";
								break;
							case ERROR:
								value = cell.getErrorCellValue() + "";
								break;
							}
						}
						// System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);

						if (rowindex != 0) {
							switch (columnindex) {

							case 0:
								rName = value;
								Restaurant.rNameList[rowindex] = rName;
								break;
							case 1:
								tel = Integer.valueOf(value);
								Restaurant.telList[rowindex] = tel;
								break;
							case 2:
								address = value;
								Restaurant.addressList[rowindex] = address;
								break;
							case 3:
								category = value;
								Restaurant.categoryList[rowindex] = category;
								break;
							}
						}

					}

				}

				if (rName != null) {
					// System.out.println(rName +"/"+ tel +"/"+ meal +"/"+ price);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cnt;
	}

}