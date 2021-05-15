package com.proteech.berwa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.proteech.berwa.model.Employee;
import com.proteech.berwa.repository.EmployeeRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.XlsExporterConfiguration;

@Service
public class ReportService {
	@Autowired
	private EmployeeRepository employeeRepository;

	
	  @SuppressWarnings("deprecation") public String exportRepot(String
	  reportFormat) throws FileNotFoundException,
	  JRException {
		 String path="C:\\Users\\reg.rw\\Desktop\\Report";
		  List<Employee> employees=employeeRepository.findAll();
	  //load files from the classPath
	  File  file=ResourceUtils.getFile("classpath:Employees.jrxml"); 
	  //file and compile the file 
	  JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	  //Map datasource and the report 
	  JRBeanCollectionDataSource dataSource= new
	  JRBeanCollectionDataSource(employees);
	  
	  //add owner of report
	  Map<String,Object>parameter=new HashMap<>();
	  parameter.put("Created By", "Proteech"); 
	  //Print the report 
	  JasperPrint
	  jasperPrint=JasperFillManager.fillReport(jasperReport,parameter, dataSource);
	  
	  if(reportFormat.equalsIgnoreCase("html")) {
	  JasperExportManager.exportReportToHtmlFile(jasperPrint,
	  path+"\\Employee.html"); }
	  
	  if(reportFormat.equalsIgnoreCase("pdf")) {
	  JasperExportManager.exportReportToPdfFile(jasperPrint,
	  path+"\\Employee.pdf");
	  
	  }
	  
	
	  return "Report generated successfully in Path:"+path;
	  
	  
	  
	  
	  }
	  
	 
	
		/*
		 * @SuppressWarnings("deprecation") public void exportAllFileData(String
		 * path,List list,String filetype,String filename,HttpServletResponse response)
		 * { System.out.print("File Type report: "+filetype);
		 * System.out.println("Main Export Method..."+filename);
		 * System.out.print("File Type report: "+filetype); JasperPrint jasperPrint;
		 * ServletOutputStream outputStream = null; try { JRBeanCollectionDataSource
		 * result=new JRBeanCollectionDataSource(list); JasperReport jasperReport =
		 * JasperCompileManager.compileReport(path); jasperPrint =
		 * JasperFillManager.fillReport(jasperReport, new HashMap<String,Object>(),
		 * result); System.out.print("-------------------- "); String mimeType = new
		 * MimetypesFileTypeMap().getContentType( filename+".pdf" );
		 * System.out.println("mimetypee : "+mimeType);
		 * 
		 * if(filetype.equals("PDF")) { response.setHeader("Content-Disposition",
		 * "attachment;filename"+filename+".pdf");
		 * response.setContentType("application/octet-stream");
		 * response.setContentLength(4096); outputStream = response.getOutputStream();
		 * JRPdfExporter exporter = new JRPdfExporter();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		 * exporter.exportReport(); } if(filetype.equals("EXCEL")) {
		 * System.out.print("In Excel report: ");
		 * response.setHeader("Content-Disposition",
		 * "attachment;filename"+filename+".xlsx");
		 * response.setContentType("application/octet-stream");
		 * response.setContentLength(4096); outputStream = response.getOutputStream();
		 * JRXlsxExporter exporter = new JRXlsxExporter();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		 * filename+".xlsx"); exporter.exportReport(); } if(filetype.equals("DOC")) {
		 * response.setHeader("Content-Disposition",
		 * "attachment;filename"+filename+".docx");
		 * response.setContentType("application/octet-stream");
		 * response.setContentLength(4096); outputStream = response.getOutputStream();
		 * JRDocxExporter exporter = new JRDocxExporter();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		 * exporter.exportReport(); } if(filetype.equals("CSV")) {
		 * response.setHeader("Content-Disposition",
		 * "attachment;filename"+filename+".csv");
		 * response.setContentType("application/octet-stream");
		 * response.setContentLength(4096); outputStream = response.getOutputStream();
		 * JRCsvExporter exporter = new JRCsvExporter();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		 * exporter.exportReport(); }
		 * 
		 * } catch(Exception e) { e.printStackTrace(); } }
		 */
	}

	
	

