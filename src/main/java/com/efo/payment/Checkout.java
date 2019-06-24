package com.efo.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.efo.entity.CommonFields;
import com.efo.entity.ShoppingCart;
import com.efo.entity.User;

@Component
public class Checkout {
	private static Logger logger = Logger.getLogger(Checkout.class.getName());

	private Status[] TRANSACTION_SUCCESS_STATUSES = new Status[]{
			Transaction.Status.AUTHORIZED, 
			Transaction.Status.AUTHORIZING, 
			Transaction.Status.SETTLED,
			Transaction.Status.SETTLEMENT_CONFIRMED, 
			Transaction.Status.SETTLEMENT_PENDING, 
			Transaction.Status.SETTLING,
			Transaction.Status.SUBMITTED_FOR_SETTLEMENT};

	public boolean braintreePayment(User user, ShoppingCart cart, BraintreeGateway gateway, BigDecimal amount, String nonce) {
		CommonFields common = user.getCommon();

		amount = amount.setScale(2, RoundingMode.CEILING);
		TransactionRequest request = new TransactionRequest()
				.amount(amount)
				.paymentMethodNonce(nonce)
				.billingAddress()
				.firstName(getFirstName(user))
				.lastName(getLastName(user))
				.streetAddress(
						common.getAddress1())
				.extendedAddress(common.getAddress2())
						.locality(common.getCity())
				.region(common.getRegion())
				.countryCodeAlpha3(
						common.getCountry())
				.done()
				.options()
				.storeInVault(true)
				.addBillingAddressToPaymentMethod(false)
				.submitForSettlement(true)
				.done();

		Result<Transaction> result = gateway.transaction().sale(request);

		if (result.isSuccess()) {
			return true;
		} else if (result.getTransaction() != null) {
			logger.error(result.getMessage());
			return false;
		} else {
			String errorString = "";
			for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
				errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
			}
			logger.error(errorString);
		}
		return false;
	}

	private String getFirstName(User user) {
		String firstName = "";

		if (user.getCustomer() != null) {
			firstName = user.getCustomer().getFirstname();
		} else if (user.getEmployee() != null) {
			firstName = user.getEmployee().getFirstname();
		} else if (user.getInvestor() != null) {
			firstName = user.getInvestor().getFirstname();
		} else if (user.getVendor() != null) {
			firstName = user.getVendor().getFirstname();
		}

		return firstName;
	}

	private String getLastName(User user) {
		String lastName = "";

		if (user.getCustomer() != null) {
			lastName = user.getCustomer().getLastname();
		} else if (user.getEmployee() != null) {
			lastName = user.getEmployee().getLastname();
		} else if (user.getInvestor() != null) {
			lastName = user.getInvestor().getLastname();
		} else if (user.getVendor() != null) {
			lastName = user.getVendor().getLastname();
		}

		return lastName;
	}

	public String getTransaction(BraintreeGateway gateway, String transactionId, Model model) throws Exception {
		Transaction transaction;
		CreditCard creditCard;
		Customer customer;

		transaction = gateway.transaction().find(transactionId);
		creditCard = transaction.getCreditCard();
		customer = transaction.getCustomer();

		model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
		model.addAttribute("transaction", transaction);
		model.addAttribute("creditCard", creditCard);
		model.addAttribute("customer", customer);

		return "checkouts/show";
	}

}
