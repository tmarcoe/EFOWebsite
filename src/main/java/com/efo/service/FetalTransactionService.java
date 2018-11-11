package com.efo.service;


import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.efo.entity.PaymentsReceived;
import com.efo.entity.PettyCashVoucher;
import com.efo.entity.Product;
import com.efo.entity.ProductOrders;
import com.efo.entity.OrderItems;
import com.efo.entity.Receivables;
import com.efo.entity.RetailSales;
import com.efo.dao.FetalTransactionDao;
import com.efo.entity.CapitalAssets;
import com.efo.entity.Equity;
import com.efo.entity.Events;
import com.efo.entity.FluidInventory;
import com.efo.entity.Loans;
import com.efo.entity.OverheadExpenses;
import com.efo.entity.Payables;
import com.efo.entity.PaymentHistory;
import com.efo.entity.PaymentsBilled;
import com.ftl.helper.FetalTransaction;
import com.ftl.helper.VariableType;


@Service
public class FetalTransactionService extends FetalTransaction {
	private static Logger logger = Logger.getLogger(FetalTransactionService.class);
	
	@Value("${logging.file}")
	private String logFile;
	
	@Value("${efo.federal.taxRate}")
	private String federalTaxRate;

	@Autowired
	private FetalTransactionDao transDao;

	private Session session;


	@Value("${fetal.properiesFile}")
	private String filePath;
	
	public void addLoans(Loans loan) throws IOException  {
		try {
			initTransaction(filePath);
			setDescription("Add Equity");
			publish("loan", VariableType.DAO, loan);
			publish("payable", VariableType.DAO, new Payables());
			publish("payment", VariableType.DAO, new PaymentsBilled());
			publish("event", VariableType.DAO, new Events());
			loadRule("accounts_payable/newloan.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	
	}
	
	public void addEquity(Equity equity) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Add Equity");
			publish("equity", VariableType.DAO, equity);
			loadRule("equity/addequity.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	
	}
	
	public void payOverheadExpense(OverheadExpenses expense, PaymentHistory payment) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Pay Overhead - " + expense.getReason());
			publish("expense", VariableType.DAO, expense);
			publish("payment", VariableType.DAO, payment);
			publish("newPayment", VariableType.DAO, new PaymentHistory());
			publish("event", VariableType.DAO, new Events());
			loadRule("overhead_expense/payoverhead.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
		
	}
	
	public void newOverheadExpense(OverheadExpenses expense) throws IOException {
		try {
			initTransaction(filePath);
			publish("expense", VariableType.DAO, expense);
			publish("payment", VariableType.DAO, new PaymentHistory());
			publish("event", VariableType.DAO, new Events());
			loadRule("overhead_expense/newoverhead.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	
	}
	
	public void shipSales(RetailSales sales) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Shipping Invoice #: " + sales.getInvoice_num());
			publish("sales", VariableType.DAO, sales);
			loadRule("retail_sales/shipretailsales.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void retailSalesOrder(RetailSales sales, PaymentsReceived payment, Date latest_date) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Retail Sales - (Invoice Number = " + sales.getInvoice_num() + ")");
			publish("sales", VariableType.DAO, sales);
			publish("receivables", VariableType.DAO, sales.getReceivables());
			publish("payment", VariableType.DAO, payment);
			publish("latest_date", VariableType.DATE, latest_date);
			publish("taxRate", VariableType.DECIMAL, Double.valueOf(federalTaxRate));
			publish("event", VariableType.DAO, new Events());
			loadRule("retail_sales/retailpurchase.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void purchaseCapital(CapitalAssets asset, PaymentsBilled payments) throws Exception {
		
		try {
			initTransaction(filePath);
			setDescription("Purchase of Capital Asset -(" + asset.getItem_name() + ")");
			publish("asset", VariableType.DAO, asset);
			publish("payables", VariableType.DAO, asset.getPayables());
			publish("billed", VariableType.DAO, payments);
			publish("event", VariableType.DAO, new Events());
			loadRule("captial_assets/purchasecapital.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
		
	}

	
	public void makePayment(Payables payables, PaymentsBilled billed) throws IOException {
		try {
			initTransaction(filePath);
			if (billed.getPayables() == null) {
				billed.setPayables(payables);
			}
			publish("billed", VariableType.DAO, billed);
			publish("payables", VariableType.DAO, payables);
			publish("nextBill", VariableType.DAO, new PaymentsBilled());
			publish("event", VariableType.DAO, new Events());
			loadRule("accounts_payable/makepayment.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
		
	}
	public Double calculatePayments(double total, double down, double interest, long payments) throws Exception {
		Double result = 0.0;
		try {
			initTransaction(filePath);
			publish("total", VariableType.DECIMAL, total);
			publish("down", VariableType.DECIMAL, down);
			publish("interest", VariableType.DECIMAL, interest);
			publish("payments", VariableType.NUMBER, payments);
			loadRule("calculators/calcpayment.trans");
			result = (Double) getValue("eachPayment");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
		
		return result;
	}
	
	public void cancelOrder(OrderItems order, FluidInventory inventory ) throws IOException {
		
		try {
			initTransaction(filePath);
			setDescription("Cancel Order: " + order.getReference());
			publish("order", VariableType.DAO, order);
			publish("payables", VariableType.DAO, new Payables());
			publish("inventory", VariableType.DAO, inventory);
			loadRule("order_inventory/ordercancelled.trans");
			}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void orderDelivered(ProductOrders productOrder, OrderItems order, Product product, double qty ) throws RecognitionException, IOException, RuntimeException {
			String description = String.format("Order Received: (Invoice - %s, SKU - %s)", productOrder.getInvoice_num(), product.getSku());
		try {
			initTransaction(filePath);
			setDescription(description);
			publish("order", VariableType.DAO, order);
			publish("received", VariableType.DECIMAL, qty);
			loadRule("order_inventory/orderdelivered.trans");
			}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}

	public void purchaseInventory(ProductOrders order, Payables payables, PaymentsBilled payments) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Purchase of Inventory (Invoice #" + order.getInvoice_num() + ")");
			publish("order", VariableType.DAO, order);
			publish("payables", VariableType.DAO, payables);
			publish("payment", VariableType.DAO, payments);
			publish("event", VariableType.DAO, new Events());
			loadRule("order_inventory/orderinventory.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void replenishPettyCash( double pettyCashCeiling) throws IOException {
		try {
			initTransaction(filePath);
			publish("pettyCashCeiling", VariableType.DECIMAL, pettyCashCeiling);
			loadRule("petty_cash/replenishpc.trans");
			}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}

	public void addPettyCash(PettyCashVoucher pettyCashVoucher) throws IOException {
		try {
			initTransaction(filePath);
			setDescription("Petty Cash: " + pettyCashVoucher.getReason());
			publish("pettyCashVoucher", VariableType.DAO, pettyCashVoucher);
			loadRule("petty_cash/pcdisbursement.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
		
	}

	public void pettyCashAdjustment(PettyCashVoucher pettyCashVoucher, double adjustAmount) throws IOException{
		try {
			initTransaction(filePath);
			setDescription("Adjustment for error");
			publish("pettyCashVoucher", VariableType.DAO, pettyCashVoucher);
			publish("adjustAmount", VariableType.DECIMAL, adjustAmount);
			loadRule("petty_cash/pcadjustment.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void transferPC(PettyCashVoucher oldPc, String fromAccount) throws IOException{
		try {
			initTransaction(filePath);
			setDescription("Changing Petty Cash Type");
			publish("pettyCash", VariableType.DAO, oldPc);
			publish("toAccount", VariableType.STRING, fromAccount);
			loadRule("petty_cash/transferpc.trans");
		}
		finally{
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}

	public void adjustAp(Payables payables, double amount) throws IOException {
		try {
			initTransaction(filePath);
			publish("payables", VariableType.DAO, payables);
			publish("adjustment", VariableType.DECIMAL, amount);
			loadRule("accounts_payable/adjustap.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}

	}
	
	public void adjustAr(Receivables receivables, double amount) throws IOException {
		
		try {
			initTransaction(filePath);
			publish("receivables", VariableType.DAO, receivables);
			publish("adjustment", VariableType.DECIMAL, amount);
			loadRule("accounts_receiveable/adjustar.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally{
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void receivePaymentFromReceivable(PaymentsReceived payment, Receivables receivables) throws IOException {
		
		try {
			initTransaction(filePath);
			setDescription("Payment received (ID #" + payment.getId() + ")");
			publish("payment", VariableType.DAO, payment);
			publish("receivables", VariableType.DAO, receivables);
			publish("nextPayment", VariableType.DAO, new PaymentsReceived() );
			publish("event", VariableType.DAO, new Events());
			loadRule("accounts_receiveable/receivepayment.trans");
		}
		finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	
	public void receivePayment(PaymentsReceived payments, Receivables receivables) throws IOException {
		try {
			initTransaction(filePath);
			publish("payment", VariableType.DAO, payments);
			publish("receivable", VariableType.DAO, receivables);
			publish("event", VariableType.DAO, new Events());
			loadRule("accounts_receiveable/paymentreceived.trans");
			if (hasErrors()) {
				throw new RuntimeException();
			}
			
		} finally {
			closeFetal();
			Transaction tx = transDao.getTrans();
			if (tx != null) {
				tx.rollback();
				session.clear();
				session.disconnect();
			}
		}
	}
	

	/******************************************************
	 * Overridden methods
	 ******************************************************/

	@Override
	public void beginTrans() {
		session = transDao.beginTrans();
	}

	@Override
	public void commitTrans() {
		if (this.getErrorCount() > 0) {
			rollback();
		} else {
			transDao.commitTrans(session);
		}
	}

	@Override
	public void credit(Double amount, String account) {

		transDao.credit(amount, account, session);
	}

	@Override
	public void debit(Double amount, String account) {

		transDao.debit(amount, account, session);
	}

	@Override
	public void ledger(char type, Double amount, String account, String description) {

		transDao.ledger(type, amount, account, description, session);
	}

	@Override
	public double getBalance(String account) {

		return transDao.getBalance(account, session);
	}

	@Override
	public void rollback() {
		transDao.rollback(session);
		session = null;
	}

	@Override
	public Object lookup(String sql, Object... args) {
		String sqlWithArgs = String.format(sql, args);

		return transDao.lookup(sqlWithArgs, session);
	}

	@Override
	public Set<Object> list(String sql, int limit, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		List<Object> l = transDao.list(sqlWithArgs, limit, session);

		return new HashSet<Object>(l);
	}

	@Override
	public void update(String sql,int limit, Object... args) {
		String sqlWithArgs = String.format(sql, args);
		transDao.update(sqlWithArgs, limit, session);
	}

	
	@Override
	public void fetalLogger(String clss, String msg) {
		String errorMsg = "Error in class " + clss + ": " + msg;
		logger.error(errorMsg);
		
	}

	@Override
	public void insert(Object record) {
		transDao.create(record, session);
	}

	@Override
	public void delete(Object record) {
		transDao.delete(record, session);
	}

	@Override
	public void merge(Object record) {
		transDao.merge(record, session);
	}

	@Override
	public void commitStock(Set<?> items) {
		transDao.commitStock(items, session);
	}

	@Override
	public void depleteStock(Set<?> items) {
		transDao.depleteStock(items, session);
	}

	@Override
	public void inventoryLedger(char type, Double qty, Double amount, String description) {
		transDao.inventoryLedger(type, qty, amount, description, session);
	}

}
