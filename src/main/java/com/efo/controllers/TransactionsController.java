package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	public String listTransactions(@PathVariable("from") Date from, @PathVariable("to") Date to, Model model) {

		transList = transactionsService.retrieveList(from, to);

		model.addAttribute("objectList", transList);
		model.addAttribute("pagelink", pageLink);

		return "transactionslist";
	}

	@RequestMapping("newtransaction")
	public String newTransaction(Model model) {
		Transactions transaction = new Transactions();
		transaction.setTimestamp(new Date());

		List<String> profileList = profilesService.retrieveNames();
		model.addAttribute("profileList", profileList);
		model.addAttribute("transaction", transaction);

		return "newtransaction";
	}

	@RequestMapping("addtransaction")
	public String addTransaction(@Valid @ModelAttribute("transaction") Transactions transaction, BindingResult result) throws NumberFormatException, Exception {

		if (result.hasErrors()) {

			return "newtransaction";
		}

		String profileName = transaction.getName();

			Profiles profile = profilesService.retrieve(profileName);
			if ("".compareTo(profile.getScript()) != 0) {
			if (profileName.contains("Retail Sales")) {
				if ("".compareTo(profile.getVariables()) == 0) {
					profile.setVariables("taxRate,decimal");
				}
				fetalTransactionService.execTransaction(profile, transaction, Double.valueOf(taxRate));
			} else {
				fetalTransactionService.execTransaction(profile, transaction);
			}
		}
		transactionsService.create(transaction);

		return "redirect:/#tabs-3";
	}

	@RequestMapping("viewtransaction")
	public String editTransaction(@ModelAttribute("reference") Long reference, Model model) {

		Transactions transaction = transactionsService.retrieve(reference);

		model.addAttribute("transaction", transaction);

		return "viewtransaction";
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

}
