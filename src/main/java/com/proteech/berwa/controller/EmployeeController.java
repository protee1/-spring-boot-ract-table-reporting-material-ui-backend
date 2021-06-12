package com.proteech.berwa.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proteech.berwa.model.ApiResponse;
import com.proteech.berwa.model.Employee;
import com.proteech.berwa.repository.EmployeeRepository;
import com.proteech.berwa.service.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.AbstractXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.XlsxReportConfiguration;
@CrossOrigin({"http://localhost:3001","http://localhost:5000"})
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ReportService reportService;
	
@GetMapping("/all")
public ResponseEntity<List<Employee>>getAllEmployees(){
	return ResponseEntity.ok(employeeRepository.findAll());
}

@PostMapping("/")
public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
	if(employee.getName().isEmpty() || employee.getEmail().isEmpty()) {
		return ResponseEntity.badRequest().body( new ApiResponse("F","Name can not be empty!"));
	}
	return ResponseEntity.ok(employeeRepository.save(employee));
}
@GetMapping("/report/{format}")
public String generateReport( @PathVariable String format) throws FileNotFoundException, JRException {
	
	return reportService.exportRepot(format);
}
@GetMapping("/document/xls")
public void getDocument(HttpServletResponse response) throws JRException, IOException {
	
	File file=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"Employees.jrxml");
	JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	//String file=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"Employee.jasper").getAbsolutePath();
	List<Employee> employees=employeeRepository.findAll();
	 JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(employees);

	Map<String, Object>parameters=new HashMap<>();
	parameters.put("Created by", "Proteech");
	JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, parameters,datasource);
JRXlsxExporter exporter=new JRXlsxExporter();
XlsxReportConfiguration xlConfiguration=new SimpleXlsxReportConfiguration();
((AbstractXlsReportConfiguration) xlConfiguration).setSheetNames(new String[] {"sheet1"});
exporter.setConfiguration( xlConfiguration);
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
response.setHeader("Content-Disposition", "attachment;filename=Employee.xlsx");
response.setContentType("application/octet-stream");
exporter.exportReport();
}
@GetMapping("/document/pdf")
public void getPdfDocument(HttpServletResponse response) throws IOException, JRException {

///String sourceFileName = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "SampleJasperTemplate.jasper").getAbsolutePath();
	File file=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"Employees.jrxml");
	JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
// creating our list of beans
	List<Employee> employees=employeeRepository.findAll();
	// creating datasource from bean list
	 JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(employees);
Map parameters = new HashMap();
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
response.setContentType("application/pdf");
response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
	}
@GetMapping("/document/docx")
public void GetDocxDocument(HttpServletResponse response) throws IOException, JRException {
	File file=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"Employees.jrxml");
	JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
 // creating our list of beans
 	List<Employee> employees=employeeRepository.findAll();
 	// creating datasource from bean list
 	 JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(employees);
    Map<String, Object> parameters = new HashMap();
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
    JRDocxExporter exporter = new JRDocxExporter();
    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
    response.setHeader("Content-Disposition", "attachment;filename=jasperfile.docx");
    response.setContentType("application/octet-stream");
    exporter.exportReport();
}

@GetMapping("/document/java")
public void getJavaDocument(HttpServletResponse response) throws IOException, JRException {

	File file=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"Employees.jrxml");
	JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
    // creating our list of beans
 	List<Employee> employees=employeeRepository.findAll();
 	// creating datasource from bean list

 	 JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(employees);
    Map<String, Object> parameters = new HashMap();
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
    JRDocxExporter exporter = new JRDocxExporter();
    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
    response.setHeader("Content-Disposition", "attachment;filename=jasperfile.docx");
    response.setContentType("application/octet-stream");
    exporter.exportReport();
}
}


