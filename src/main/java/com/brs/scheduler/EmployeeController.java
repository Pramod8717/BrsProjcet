package com.brs.scheduler;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.CSVHelper;
import com.brs.entities.Employee;
import com.brs.entities.UserPDFExporter;
import com.brs.repository.EmployeeRepository;
import com.brs.service.BrsDataService;
import com.brs.util.BatchStatusInterf;
import com.brs.util.BrsConsoldtStgDTO;

@Controller
public class EmployeeController {

	@Autowired
	private BrsDataService brsdataservice;

	@Autowired
	private EmployeeRepository eRepo;

	@GetMapping({ "/list", "/" })
	public ModelAndView getAllEmployees() {
		ModelAndView mav = new ModelAndView("list-employees");
		mav.addObject("employees", eRepo.findAll());
		return mav;
	}

//	@RequestMapping(value= "/statusjob", method = RequestMethod.GET)
//	public ModelAndView getStatusJobs() {
//		ModelAndView mav = new ModelAndView("jobstatus");
//		mav.addObject("getStatusjobs", brsdataservice.getStatusJob());
//
//		return mav;
//	}

	@RequestMapping(value = "/statusjob", method = RequestMethod.GET)
	public String getStatusJobs() {
//		ModelAndView mav = new ModelAndView("jobstatus");
//		mav.addObject("getStatusjobs", brsdataservice.getStatusJob());

		return "statusjob";
	}

	@GetMapping("/joblistfromdb/{bankAccountNo}/{voucherDate}")
	// @GetMapping(value="/joblistfromdb/{bankAccountNo}/{voucherDate}", consumes =
	// "application/json")
	public ResponseEntity<?> getStatusJobs1(@PathVariable(required = true) String bankAccountNo,
			@PathVariable(required = true) String voucherDate, Model model) {

		try {

			System.out.println(bankAccountNo + " " + voucherDate);
			// ModelAndView mav1 = new ModelAndView("job-status");
			// mav1.addObject("getStatusjobs1", brsdataservice.getStatusJob1(bankAccountNo,
			// voucherDate));
			List<BrsConsoldtStg> brsData = brsdataservice.getStatusJob1(bankAccountNo, voucherDate);
			System.out.println("LIST ==== " + brsData.toString());

			List<BrsConsoldtStgDTO> brsList = new ArrayList<>();

			if (brsData.isEmpty()) {
				return new ResponseEntity<>("Data not found.", HttpStatus.NOT_FOUND);
			} else {
				for (BrsConsoldtStg brs : brsData) {
					BrsConsoldtStgDTO brsDataDTO = new BrsConsoldtStgDTO(brs.getBankAcNo(), brs.getValueDate(),
							brs.getProcessFlag(), brs.getStatus(), brs.getErrorMsg());
					brsList.add(brsDataDTO);
				}

				// model.addAttribute("users", brsdataservice.getBrsConsoldtStgData());
				return new ResponseEntity<>(brsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Please Enter Valid Bank Account Number or Voucher Date.",
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/chequestatus/{bankAccountNo}/{voucherDate}")
	public ResponseEntity<?> getChequeStatusfromdb(@PathVariable String bankAccountNo,
			@PathVariable String voucherDate) {
		try {
			List<BatchStatusInterf> brscheque = brsdataservice.getChequeStatus(bankAccountNo, voucherDate);
			System.out.println("brscheque:::::::"+brscheque);
			List<BatchStatusInterf> brsd = new ArrayList<BatchStatusInterf>();

			if (brscheque.isEmpty()) {
				return new ResponseEntity<>("Data not found.", HttpStatus.NOT_FOUND);
			} else {
				for (BatchStatusInterf brs : brscheque) {
					BatchStatusInterf brscheques = brs;
					brsd.add(brscheques);
					
					System.out.println("BrsD::::"+brsd.toString());
				}
				return new ResponseEntity<>(brsd, HttpStatus.OK);
			}
		} catch (Exception e) {

			return new ResponseEntity<>("Please Enter Valid Bank Account Number or Voucher Date.",
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping({ "/userlist", "/" })
	public ModelAndView getUserList() {
		ModelAndView mav = new ModelAndView("user-list");
		return mav;
	}

	@GetMapping("/download_file")
	public void downloadFile(HttpServletResponse response) throws Exception {
		// File file = new File("templates\\list-employees.html");
		System.out.println("Hiii");
		// Long tbl_employeesId=1L;
		// System.out.println(tbl_employeesId);

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=employee_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<BatchStatusInterf> getStatusjobs = brsdataservice.getStatusJob();
		System.out.println(getStatusjobs);

		UserPDFExporter exporter = new UserPDFExporter(getStatusjobs);
		exporter.export(response);

	}

//	@GetMapping("/download_file")
//	public void downloadFile(HttpServletResponse response) throws Exception {
//		// File file = new File("templates\\list-employees.html");
//		System.out.println("Hello");
//		// Long tbl_employeesId=1L;
//		// System.out.println(tbl_employeesId);
//
//		response.setContentType("application/pdf");
//		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String currentDateTime = dateFormatter.format(new Date());
//
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment; filename=employee_" + currentDateTime + ".pdf";
//		response.setHeader(headerKey, headerValue);
//		List<BatchStatusInterf> getStatusjobs1 = brsdataservice.getStatusJob1(currentDateTime, headerValue);
//	//	List<BatchStatusInterf> getStatusjobs1 = brsdataservice.getStatusJob();
//		System.out.println(getStatusjobs1);
//
//		UserPDFExporter exporter = new UserPDFExporter(getStatusjobs1);
//		exporter.export(response);
//
//	}

//	@GetMapping("/download_file")
//	public void downloadFile(HttpServletResponse response) throws Exception {
//		// File file = new File("templates\\list-employees.html");
//		System.out.println("Hiii");
//		// Long tbl_employeesId=1L;
//		// System.out.println(tbl_employeesId);
//
//		response.setContentType("application/pdf");
//		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String currentDateTime = dateFormatter.format(new Date());
//
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment; filename=employee_" + currentDateTime + ".pdf";
//		response.setHeader(headerKey, headerValue);
//
//		List<Employee> employees = eRepo.findAll();
//		System.out.println(employees);
//
//		UserPDFExporter exporter = new UserPDFExporter(employees);
//		exporter.export(response);
//
//	}

	@GetMapping("/download/empCsv")
	public ResponseEntity<Resource> getFile() {
		String filename = "empCsv.csv";
		InputStreamResource file = new InputStreamResource(load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	public ByteArrayInputStream load() {
		List<Employee> emps = eRepo.findAll();

		ByteArrayInputStream in = CSVHelper.tutorialsToCSV(emps);
		return in;
	}

//	@GetMapping("/getstatus/{bankAccountNo}/{voucherDate}")
//	// @GetMapping(value="/joblistfromdb/{bankAccountNo}/{voucherDate}", consumes =
//	// "application/json")
//	public ResponseEntity<?> getStatusJobs2(@PathVariable (required = true) String bankAccountNo,
//			@PathVariable(required = true) String voucherDate) {
//
//		try {			
//
//			System.out.println(bankAccountNo + " " + voucherDate);
//			// ModelAndView mav1 = new ModelAndView("job-status");
//			// mav1.addObject("getStatusjobs1", brsdataservice.getStatusJob1(bankAccountNo,
//			// voucherDate));
//			List<BrsConsoldtStg> brsData = brsdataservice.getStatusJob1(bankAccountNo, voucherDate);
//			System.out.println("LIST ==== " + brsData.toString());
//
//			List<BrsConsoldtStgDTO> brsList = new ArrayList<>();
//
//			if (brsData.isEmpty()) {
//				return new ResponseEntity<>("Data not found.", HttpStatus.NOT_FOUND);
//			} else {
//				for (BrsConsoldtStg brs : brsData) {
//					BrsConsoldtStgDTO brsDataDTO = new BrsConsoldtStgDTO(brs.getBankAcNo(), brs.getValueDate(),
//							brs.getProcessFlag(), brs.getStatus(), brs.getErrorMsg());
//					brsList.add(brsDataDTO);
//				}
//				return new ResponseEntity<>(brsList, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>("Please Enter Valid Bank Account Number or Voucher Date.",
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
//	@GetMapping(value = "/joblistfromdb/{bankAccountNo}/{voucherDate}")
//    public String viewBooks(Model model) {
//        model.addAttribute("brsData", brsdataservice.getStatusJob());
//        System.out.println("brsdataservice.getStatusJob()::::::::::"+brsdataservice.getStatusJob());
//        return "statusjob";
//    }
}
