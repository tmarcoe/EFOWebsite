package com.efo.reports;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.efo.service.RevenuePaymentsService;
import com.efo.service.RevenuesService;

@Component
public class RevenueReport {
	
	@Autowired
	private RevenuesService revenuesService;
	
	@Autowired
	private RevenuePaymentsService revenuePaymentsService;
	
	public String revenuesByMonth(Date begin, Date end) throws JSONException {
		String headingPattern = "MMMMM dd, yyyy";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);
		String reportTitle = String.format("Revenues From: %-20s To: %s", df.format(begin), df.format(end));
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		List<Double> sums = new ArrayList<Double>();

		int m = jBegin.getMonthOfYear();
		int y = jBegin.getYear();
		for (int i = 0; i < diff; i++) {
			Double cash = revenuesService.sumCashRevenue(m, y);
			Double credit = revenuePaymentsService.sumCreditPayments(m, y);
			sums.add(round((cash + credit), 2));
			m++;
			if (m > 12) {
				m = 1;
				y++;
			}
		}
		
		return convertToJSON(sums, diff, jBegin, reportTitle).toString();
	}
	
	private JSONObject convertToJSON(List<Double> revenue, int length, LocalDate start, String reportTitle) throws JSONException {	
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray datasets = new JSONArray();
		JSONArray dateLabels = new JSONArray();
		
		JSONObject exp = new JSONObject();

		JSONObject options = new JSONObject();
		JSONObject scales = new JSONObject();
		JSONObject title = new JSONObject();
		JSONArray dataPoints = new JSONArray();
		JSONArray yAxes = new JSONArray();
		JSONObject yObj = new JSONObject();
		JSONObject ticks = new JSONObject();

		for (int i = (start.getMonthOfYear() -1) ; i < ((start.getMonthOfYear() + length) - 1) ; i++) {
			dateLabels.put(months[(i % 12)]);
		}
		for (Double item : revenue) {
			dataPoints.put(item);
		}
		json.put("type", "line");
		json.put("data", data);
		data.put("labels", dateLabels);
		data.put("datasets", datasets);
		datasets.put(exp);
		exp.put("label", "Revenue by month" );
		exp.put("data",dataPoints);
		exp.put("borderColor", "rgba(158, 3, 3, .5)");
		exp.put("backgroundColor", "rgba(158, 3, 3, .5)");
		exp.put("fill", false);
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
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
