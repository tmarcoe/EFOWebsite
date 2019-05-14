package com.efo.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.service.ExpensePaymentsService;
import com.efo.service.ExpensesService;
import com.efo.service.LoanPaymentsService;
import com.efo.service.RevenuePaymentsService;
import com.efo.service.RevenuesService;

@Component
public class RevenueReport {
	
	@Autowired
	private RevenuesService revenuesService;
	
	@Autowired
	private RevenuePaymentsService revenuePaymentsService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ExpensePaymentsService expensePaymentsService;
	
	@Autowired
	private LoanPaymentsService loanPaymentsService;
	
	public String revenuesByMonth(Date begin, Date end) throws JSONException {
		String headingPattern = "MMMMM dd, yyyy";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);
		String reportTitle = String.format("Revenues and Expenses From: %-20s To: %s", df.format(begin), df.format(end));
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		List<Double> revenues = new ArrayList<Double>();
		List<Double> expenses = new ArrayList<Double>();
		List<Double> profits = new ArrayList<Double>();

		int m = jBegin.getMonthOfYear();
		int y = jBegin.getYear();
		for (int i = 0; i < diff; i++) {
			Double cash = revenuesService.sumCashRevenue(m, y);
			Double credit = revenuePaymentsService.sumCreditPayments(m, y);
			Double rev = cash + credit;
			revenues.add(rev);
			cash = expensesService.sumMonthlyExpenses(m, y);
			credit = expensePaymentsService.sumMontlyPayments(m, y);
			Double loanPayments = loanPaymentsService.sumMontlyPayments(m, y);
			Double exp = cash + credit + loanPayments;
			expenses.add(exp);
			profits.add(rev - exp);
			m++;
			if (m > 12) {
				m = 1;
				y++;
			}
		}
		
		return convertToJSON(revenues, expenses, profits, diff, jBegin, reportTitle).toString();
	}
	
	private JSONObject convertToJSON(List<Double> revenues, List<Double> expenses, List<Double> profits, 
									int length, LocalDate start, String reportTitle) throws JSONException {	
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONArray dateLabels = new JSONArray();
		
		JSONObject rev = new JSONObject();
		JSONObject exp = new JSONObject();
		JSONObject prf = new JSONObject();

		JSONObject options = new JSONObject();
		JSONObject scales = new JSONObject();
		JSONObject title = new JSONObject();
		JSONArray revenueDataPoints = new JSONArray();
		JSONArray expenseDataPoints = new JSONArray();
		JSONArray profitsDataPoints = new JSONArray();
		JSONArray yAxes = new JSONArray();
		JSONObject yObj = new JSONObject();
		JSONObject ticks = new JSONObject();

		for (int i = (start.getMonthOfYear() -1) ; i < ((start.getMonthOfYear() + length) - 1) ; i++) {
			dateLabels.put(months[(i % 12)]);
		}
		for (Double item : revenues) {
			revenueDataPoints.put(item);
		}
		for (Double item : expenses) {
			expenseDataPoints.put(item);
		}
		for (Double item : profits) {
			profitsDataPoints.put(item);
		}
		json.put("type", "line");
		json.put("data", data);
		data.put("labels", dateLabels);
		data.put("datasets", datasets);
		datasets.put(rev);
		rev.put("label", "Revenue" );
		rev.put("data",revenueDataPoints);
		rev.put("borderColor", "#0000ff");
		rev.put("backgroundColor", "#0000ff");
		rev.put("fill", false);
		datasets.put(exp);
		exp.put("label","Expense");
		exp.put("data", expenseDataPoints);
		exp.put("borderColor", "#ff0000");
		exp.put("backgroundColor", "#ff0000");
		exp.put("fill", false);
		datasets.put(prf);
		prf.put("label", "Profits");
		prf.put("data", profitsDataPoints);
		prf.put("borderColor", "#00ff00");
		prf.put("backgroundColor", "#00ff00");
		prf.put("fill", false);		
		json.put("options", options);
		//options.put("scales", scales);
		options.put("responsive", false);
		options.put("title", title);
		title.put("display", true);
		title.put("text", reportTitle);
		title.put("fontSize", 28);
		scales.put("yAxes", yAxes);
		yObj.put("ticks", ticks);
		ticks.put("beginAtZero", true);
		yAxes.put(yObj);
		
		return json;
	}
	
}
