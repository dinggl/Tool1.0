package excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.samskivert.mustache.Mustache;

/**
 * Created with IntelliJ IDEA. User: 00000000000000000000 Date: 13-10-21 Time:
 * 下午4:30 To change this template use File | Settings | File Templates.
 */
public class Excel2007 {
	Map<String, List<Map<String, Object>>> mlm = null;
	public List<Object> fillKeys(XSSFRow row) {
		List<Object> list = new ArrayList<Object>();
		for (Iterator<Cell> j = row.cellIterator(); j.hasNext();) {
			// Cell cell = j.next();
			XSSFCell cell = (XSSFCell) j.next();
			if (cell == null)
				continue;
			Object key = getCellValue(cell);
			if (key == null)
				continue;
			list.add(key);
		}
		return list;
	}

	// 读excel文档
	public void readExcelFromModel(String path, String sheetName, String key)
			throws IllegalAccessException, InstantiationException, IOException {
		XSSFSheet sheet = getSheet(path, sheetName);
		if (sheet != null) {
			List<Object> keys = null;
			Map<String, List<List<Object>>> map = new HashMap<String, List<List<Object>>>();
			int keyIndex = -1;
			for (Iterator<Row> i = sheet.rowIterator(); i.hasNext();) {
				// Row row = i.next();
				XSSFRow row = (XSSFRow) i.next();
				if (row == null)
					continue;
				// System.out.println(row.getRowNum() + "="+row.getCell(0));
				if (keys == null) {
					keys = fillKeys(row);
					keyIndex = keys.indexOf(key);
				} else {
					fillMap(map, row, keyIndex);
				}
			}
			mlm = convert(map, keys, key);
		} else {
			System.out.println("没有找到工作表");
		}
	}

	private Map<String, List<Map<String, Object>>> convert(
			Map<String, List<List<Object>>> mll, List<Object> keys, String key) {
		// TODO Auto-generated method stub
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		Iterator<Entry<String, List<List<Object>>>> it = mll.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, List<List<Object>>> entry = it.next();
			Map<String, Object> city = new HashMap<String, Object>();
			city.put(key, entry.getKey());
			List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
			Iterator<List<Object>> it1 = entry.getValue().iterator();
			while (it1.hasNext()) {
				List<Object> item = it1.next();
				Map<Object, Object> values = new HashMap<Object, Object>();
				for (int index = 0; index < item.size(); index++) {
					if (index >= keys.size())
						break;
					values.put(keys.get(index), item.get(index));
				}
				list.add(values);
			}
			city.put("list", list);
			map.add(city);
		}
		result.put("map", map);
		return result;
	}

	private void fillMap(Map<String, List<List<Object>>> map, XSSFRow row,
			int keyIndex) {
		// TODO Auto-generated method stub
		List<Object> list = fillKeys(row);
		String key = list.get(keyIndex).toString();
		if (map.containsKey(key)) {
			map.get(key).add(list);
		} else {
			List<List<Object>> lists = new ArrayList<List<Object>>();
			lists.add(list);
			map.put(key, lists);
		}
	}

	// 读excel文档
	public void readExcel(String path, String sheetName) {
		XSSFSheet sheet = getSheet(path, sheetName);
		if (sheet != null) {
			for (Iterator<Row> i = sheet.rowIterator(); i.hasNext();) {
				// Row row = i.next();
				XSSFRow row = (XSSFRow) i.next();
				if (row == null)
					continue;
				// System.out.println(row.getRowNum() + "="+row.getCell(0));
				for (Iterator<Cell> j = row.cellIterator(); j.hasNext();) {
					// Cell cell = j.next();
					XSSFCell cell = (XSSFCell) j.next();
					if (cell == null)
						continue;
					System.out.print(getCellValue(cell) + " ");

				}
				System.out.println();
			}
		} else {
			System.out.println("没有找到工作表");
		}
	}

	// 加载工作薄
	private XSSFWorkbook getXSSFWorkBook(String path) {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	// 根据名字取工作表Sheet
	private XSSFSheet getSheet(String path, String sheetName) {
		return this.getXSSFWorkBook(path).getSheet(sheetName);
	}

	// 判断Cell单元格的类型。
	public static Object getCellValue(Cell cell) {
		Object result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue().trim();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellInternalDateFormatted(cell))
				result = cell.getDateCellValue();
			else
				result = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_ERROR:
			result = cell.getErrorCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		default:
			result = "NULL";
			break;
		}
		return result;
	}

	// 生成excel文件
	public void createExcelDoc() {
		String path = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "doc"
				+ System.getProperty("file.separator") + "creatExcelDoc.xlsx";
		try {
			FileOutputStream outStream = new FileOutputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet("first");
			sheet.autoSizeColumn(0);
			XSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("Name");
			row.createCell(1).setCellValue(2.2);

			// helper.createDataFormat() 得到一个DataFormat实例
			/*
			 * CreationHelper helper = workbook.getCreationHelper(); CellStyle
			 * dateStyle = workbook.createCellStyle();
			 * dateStyle.setDataFormat(helper
			 * .createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
			 * 
			 * Cell c = row.createCell(2); c.setCellValue(new Date());
			 * c.setCellStyle(dateStyle);
			 */

			// XSSF....方法
			XSSFCell cell = row.createCell(2);
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFCreationHelper h = workbook.getCreationHelper();

			style.setDataFormat(h.createDataFormat().getFormat("yyyy-MM-dd"));
			style.setAlignment(XSSFCellStyle.VERTICAL_CENTER);

			cell.setCellStyle(style);
			cell.setCellValue(new Date());

			// 设置第n单元格的宽度，自动
			sheet.autoSizeColumn(2);

			workbook.write(outStream);
			System.out.println("create finished!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File save2File(String tpl) throws IOException {
		File file = new File(tpl);
		StringWriter sw = new StringWriter();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			sw.write(line);
		}
		String templete = sw.toString();

		String result = Mustache.compiler().compile(templete).execute(mlm);
		result = result.replaceAll("}\\s*,\\s*]", "}]");
		File data = new File(System.currentTimeMillis()+"data.js");
		if (!data.exists()) {
			FileOutputStream fos = null;
			try {
				if (data.createNewFile()) {
					fos = new FileOutputStream(data);
					fos.write(result.getBytes());
					fos.flush();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null)
					fos.close();
			}
			return data;
		}
		return null;
	}
}