package com.efo.reports;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.efo.service.RevenuesService;

@Component
public class RevenueReport {
	
	@Autowired
	private RevenuesService revenuesService;
	
	public String revenuesByMonth(Date begin, Date end) throws JSONException {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		int diff = Months.monthsBetween(jBegin, jEnd).getMonths() + 1;
		List<Double> sums = new ArrayList<Double>();

		List<Object[]> result = revenuesService.listCashRevenue(begin, end);
		for (Object[] obj : result) {
			Double var = round((Double) obj[2], 2);
			sums.add(var);
		}
		
		return convertToJSON(sums, diff, jBegin,String.format("Revenues From %tD To %tD", begin, end)).toString();
	}
	
	private JSONObject convertToJSON(List<Double> revenue, int length, LocalDate start, String reportTitle) throws JSONException {	
		final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		//final int[] temp = {10,2,3,45,78,9,86,94,73,29,59,62};
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
