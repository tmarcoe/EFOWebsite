package com.efo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Profiles;
import com.efo.entity.Transactions;
import com.efo.service.FetalTransactionService;
import com.efo.service.ProfilesService;
import com.efo.service.TransactionsService;

@Controller
@RequestMapping("/basic/")
public class TransactionsController {
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private FetalTransactionService fetalTransactionService;
	
	@Autowired
	private ProfilesService profilesService;
	
	@Value("${efo.federal.taxRate}")
	private String taxRate;
	
	private final String pageLink = "/basic/transactionspaging";
	private PagedListHolder<Transactions> transList;
	private SimpleDateFormat dateFormat;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("transactionslist/from/{from}/to/{to}")
	public String listTransactions(@PathVariable("from") Date from,@PathVariable("to") Date to, Model model) {
		
		transList = transactionsService.retrieveList(from, to);
		
		model.addAttribute("objectList", transList);
		model.addAttribute("pagelink", pageLink);
			
		return "transactionslist";
	}
	
	@RequestMapping("newreceiveretailtransaction")
	public String newreceiveretailtransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("RR"));
		model.addAttribute("transaction", transaction);
		
		return "newreceiveretailtransaction";
	}
	@RequestMapping("addreceiveretail")
	public String addTReceiveRetail(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("RR"));
			return "newreceiveretailtransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	
	@RequestMapping("newoverheadtransaction")
	public String newOverheadTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("O"));
		model.addAttribute("transaction", transaction);
		
		return "newoverheadtransaction";
	}
	
	@RequestMapping("addoverhead")
	public String addOverhead(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("O"));
			return "newoverheadtransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	@RequestMapping("neworderretailtransaction")
	public String newOrderRetailTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("RE"));
		model.addAttribute("transaction", transaction);
		
		return "neworderretailtransaction";
	}
	
	@RequestMapping("addorderretail")
	public String addOrderRetail(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("RE"));
			return "neworderretailtransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	@RequestMapping("newcapitalexpensetransaction")
	public String newCapitalExpenseTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("CE"));
		model.addAttribute("transaction", transaction);
		
		return "newcapitalexpensetransaction";
	}
	
	@RequestMapping("addcapitalexpense")
	public String addCapitalExpense(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("CE"));
			return "newcapitalexpensetransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	@RequestMapping("newloantransaction")
	public String newLoanTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("L"));
		model.addAttribute("transaction", transaction);
		
		return "newloantransaction";
	}
	
	@RequestMapping("addloan")
	public String addLoan(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("L"));
			return "newloantransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	@RequestMapping("newinvestortransaction")
	public String newInvestorTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("namesList", profilesService.retrieveNames("I"));
		model.addAttribute("transaction", transaction);
		
		return "newinvestortransaction";
	}
	
	@RequestMapping("addequity")
	public String addEquity(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		if (result.hasErrors()) {
			model.addAttribute("namesList", profilesService.retrieveNames("I"));
			return "newinvestortransaction";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-3";
	}
		
	@RequestMapping("viewtransaction")
	public String editTransaction(@ModelAttribute("reference") Long reference, Model model) {
		
		Transactions transaction = transactionsService.retrieve(reference);
		
		model.addAttribute("transaction", transaction);
		
		return "viewtransaction";
	}
	
	@RequestMapping("payment")
	public String receivePayment(@ModelAttribute("id") Long id, 
								 @ModelAttribute("payment_name") String paymentName, 
								 @ModelAttribute("profilename") String profileName, Model model) {
		
		Transactions transaction = new Transactions();
		transaction.setPayment_ref(id);
		transaction.setTimestamp(new Date());
		transaction.setName(profileName);
		transaction.setPayment_name(paymentName);
		transaction.setStart(transaction.getTimestamp());
		
		model.addAttribute("transaction", transaction);
		
		return "payment";
	}
	
	@RequestMapping("addtransaction")
	public String addTransaction(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result, Model model) throws NumberFormatException, Exception {
		Object[] variables = null;
		
		if (result.hasErrors()) {
		
			return "payment";
		}

		String profileName = transaction.getName();
		Profiles profile = profilesService.retrieve(profileName);
		
		if ("".compareTo(profile.getVariables()) != 0) {
			variables = getObject(profile.getVariables());
		}
		
		fetalTransactionService.execTransaction(profile, transaction, variables);	
		
		transactionsService.create(transaction);
		
		return "redirect:/#tabs-5";
	}
	
	@RequestMapping(value = "transactionspaging", method = RequestMethod.GET)
	public String handleBudgeItemtRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			transList.nextPage();
		} else if ("prev".equals(page)) {
			transList.previousPage();
		} else if (pgNum != -1) {
			transList.setPage(pgNum);
		}
		
		model.addAttribute("objectList", transList);
		model.addAttribute("pagelink", pageLink);

		return "transactionslist";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}

	private Object[] getObject(String varString) throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String variables = "";
		final String daoPath = "com.efo.entity.";
		
		if ("".compareTo(varString) == 0) return null;
		
		if (varString.toLowerCase().contains("%tax%")) {
			variables = varString.replace("%tax%", taxRate);
		}else{
			variables = varString;
		}
		
		String[] vars = variables.split(";");
		Object[] result = new Object[vars.length];
		
		for (int i=0; i < vars.length; i++) {
			String[] parms = vars[i].split(",");
			switch(parms[1].toLowerCase()) {
				case "decimal":
					result[i] = (Double) Double.valueOf(parms[2]);
					break;
				case "number":
					result[i] = (Long) Long.valueOf(parms[2]);
					break;
				case "string":
					result[i] = (String) parms[2];
					break;
				case "date":
					result[i] = (Date) dateFormat.parse(parms[2]);
					break;
				case "boolean":
					if ("true".compareTo(parms[2]) == 0) {
						result[i] = (Boolean) true;
					}else{
						result[i] = (Boolean) false;
					}
					break;
				case "object":
					result[i] = Class.forName(parms[2]).newInstance();
					break;
				case "dao":
					result[i] = Class.forName(daoPath + parms[2]).newInstance(); 
					break;
				default:
					result[i] = null;
					break;
			}
		}
		return result;
	}
}

