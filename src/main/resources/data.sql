-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: efo
-- ------------------------------------------------------
-- Server version	5.7.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `chart_of_accounts`
--

LOCK TABLES `chart_of_accounts` WRITE;
/*!40000 ALTER TABLE `chart_of_accounts` DISABLE KEYS */;
INSERT INTO `chart_of_accounts` (`account_num`, `account_balance`, `account_name`, `account_type`, `description`) VALUES ('1000',0,'Cash','Asset','This is a cash account'),('1001',0,'Accounts Receivable','Asset','This is Accounts Receivable'),('1002',0,'Accounts Payable','Liability','This is Accounts Payable'),('1003',0,'Inventory','Asset','This is Inventory Account'),('1004',0,'Equity Accounts','Equity','Owner\'s Equity Account'),('1005',0,'Capital','Asset','Capital Equipment Account'),('1006',0,'Interest Expense','Expense','Interest Expense'),('1007',0,'Bad Debt','Liability','Bad Debt'),('1008',0,'Common Stock','Liability','Common Stock Account'),('1009',0,'Sales','Revenue','Income through Sales'),('1010',0,'Tax Account','Liability','Tax Expense'),('1011',0,'Loan Liability','Liability','Loan Liability'),('1012',0,'Overhead Expense','Expense','This account is for monthly overhead expense'),('1013',0,'Customer Deposits','Liability','This account is for any down payments'),('1015',0,'Investments','Asset','Investment Account'),('2001',0,'Labor Expense','Liability','Labor Expense'),('3000',400,'Petty Cash','Asset','Petty Cash Account'),('3001',0,'Petty Cash (Office Supplies)','Expense','This account is to keep track of Petty Cash expenditures '),('3002',0,'Petty Cash (Delivery Expense)','Expense','This account is to keep track of Petty Cash expenditures.'),('3003',0,'Petty Cash (Postage Expense)','Expense','This is to keep track of Petty Cash expenditures.'),('3004',0,'Petty Cash (General Office Expense)','Expense','This is to keep track of Petty Cash expenditures.'),('3005',0,'Petty Cash (Short and Over)','Expense','This is to keep track of Petty Cash Expenditures.');
/*!40000 ALTER TABLE `chart_of_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `common_fields`
--

LOCK TABLES `common_fields` WRITE;
/*!40000 ALTER TABLE `common_fields` DISABLE KEYS */;
INSERT INTO `common_fields` (`user_id`, `address1`, `address2`, `city`, `country`, `postal_code`, `region`) VALUES (1,'','','','USA','','');
/*!40000 ALTER TABLE `common_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `emp_financial`
--

LOCK TABLES `emp_financial` WRITE;
/*!40000 ALTER TABLE `emp_financial` DISABLE KEYS */;
INSERT INTO `emp_financial` (`user_id`, `account_num`, `ein`, `employee_type`, `exemptions`, `fed`, `garnishment`, `medical`, `pay_method`, `rate`, `retirement`, `routing_num`, `ssn`, `state`, `status`, `unemployment`, `ytd_fed`, `ytd_garnish`, `ytd_med`, `ytd_ret`, `ytd_salary`, `ytd_state`, `ytd_unemp`) VALUES (1,'','',NULL,NULL,NULL,NULL,NULL,'C',NULL,NULL,'','',NULL,'S',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `emp_financial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`user_id`, `cell_phone`, `company`, `division`, `dnr`, `emer_contact`, `emer_ph`, `emp_type`, `end_date`, `extension`, `firstname`, `home_phone`, `lastname`, `male_female`, `office_loc`, `position`, `salutation`, `start_date`, `supervisor`) VALUES (1,'','','','\0','','','F','2100-12-31 00:00:00','','New','','Employee','M','','','Mr.','2018-06-09 00:00:00',''),;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
INSERT INTO `profiles` (`name`, `active`, `created`, `depreciation`, `descr`, `exclude`, `script`, `show_credit_terms`, `type`, `variables`) VALUES ('Add Equity','','2019-04-29 00:00:00','\0',NULL,'\0','equity/addequity.trans','\0','I','events,dao,Events;equity,dao,Equity'),('Capital Expense (cash)','','2019-04-29 00:00:00','\0',NULL,'\0','capital_assets/capitalexpense-cash.trans','\0','CE','expenses,dao,Expenses;events,dao,Events'),('Capital Expense (credit)','','2019-04-29 00:00:00','\0',NULL,'\0','capital_assets/capitalexpense-credit.trans','','CE','expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments;events,dao,Events'),('Loan Payment','','2019-04-29 00:00:00','\0',NULL,'\0','accounts_payable/payloan.trans','\0','P','newPayment,dao,LoanPayments;newEvent,dao,Events'),('New Loan','','2019-04-29 00:00:00','\0',NULL,'\0','accounts_payable/newloan.trans','','L','loans,dao,Loans;loanTerms,dao,LoanTerms;loanPayments,dao,LoanPayments;events,dao,Events'),('New Overhead','','2019-04-29 00:00:00','\0',NULL,'\0','overhead_expense/newoverhead.trans','\0','O','events,dao,Events;newEvent,dao,Events;expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments'),('Order Retail (Cash)','','2019-04-29 00:00:00','\0',NULL,'\0','order_inventory/orderretail-cash.trans','\0','RE','expenses,dao,Expenses;events,dao,Events'),('Order Retail (Credit)','','2019-04-29 00:00:00','\0',NULL,'\0','order_inventory/orderretail-credit.trans','','RE','expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments;events,dao,Events'),('Pay Credit Note','','2019-04-29 00:00:00','\0',NULL,'\0','accounts_payable/paycreditnote.trans','\0','P','newPayment,dao,ExpensePayments;newEvent,dao,Events'),('Pay Overhead','','2019-04-29 00:00:00','\0',NULL,'\0','overhead_expense/payoverhead.trans','\0','P','newEvent,dao,Events;newPayment,dao,ExpensePayments'),('Receive Payment','','2019-04-29 00:00:00','\0',NULL,'\0','accounts_receiveable/receivepayment.trans','\0','P','newPayment,dao,RevenuePayments;newEvent,dao,Events'),('Retail Sales (Cash)','','2019-04-29 00:00:00','\0',NULL,'\0','retail_sales/retailsales-cash.trans','\0','RR','taxRate,decimal,%tax%;revenues,dao,Revenues;events,dao,Events'),('Retail Sales (Credit)','','2019-04-29 00:00:00','\0',NULL,'\0','retail_sales/retailsales-credit.trans','','RR','taxRate,decimal,%tax%;revenues,dao,Revenues;revenueTerms,dao,RevenueTerms;revenuePayments,dao,RevenuePayments;events,dao,Events');
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role`) VALUES (1,'USER'),(2,'PERSONNEL'),(3,'BASIC'),(4,'ACCOUNTING'),(5,'EVENTS'),(6,'REPORTS'),(7,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `enabled`, `password`, `temp_pw`, `username`) VALUES (1,'\0','$2a$10$QFxwISh5JvXg8ztNMkkZ7u1VdWhEIDd/0tYa6fNRNst1KKrf0PzDC','\0','New@new.com'),(2,'','$2a$10$IFjI/nQQG7UGpi5ZoTxjLOn.7j2sX53OoVnGpSydO9YvVCSNWSmgO','\0','tmtmarcoe80@gmail.com'),(3,'\0','$2a$10$qulawcVXBapk9tmSRX4wluF5BXAruxvcObbzpBlvPl2hbUKW.tQDm','\0','tala@coffeeforpeace.com'),(4,'\0','$2a$10$/zaw7Hcaib.A6l4lGngIM.4u8naF68ddHQj5ZXLG7g2IrLkrO/8ke','\0','bbertelsen@progress.com'),(5,'\0','$2a$10$LrL/X/pXSWRktqVW.AAOG.8ZllGSUlX17g9KAMDnChjZhHplDqdTi','\0','jsmith@sr.com'),(6,'\0','$2a$10$FzIixJYVa7MCjV0/IntlqOfLyDvOHaCwPoxk82aMA3/2Xrk3ksL96','\0','mvincent@kinkos.com'),(7,'\0','$2a$10$D4JMXVvzc0rxmAgDtNTzLuuexOoKciII7EeMsyvYmc0p7dy2xHsEO','\0','Emillio@avjewelries.com'),(8,'\0','$2a$10$JxBCF2q9CA4c4EIuu6P9WeVFIUPlDbCa25L2.XrYDovw3vxCkBkj.','\0','jhs@service.taobao.com'),(9,'\0','$2a$10$vwUvfuaHQoRYoh/f8LRLvuY7ffzbNKEoIJYGQ0Ax9KkQbFRSdAIai','\0','customerservice@officedepot.com'),(10,'\0','$2a$10$LJNeZZgr0VOSEUvcJS4JP./1qKMvpxxo55NkTTI0IdQ8D/8C3Wrn6','\0','ltansiu@nccc.com'),(11,'\0','$2a$10$IiAlNyb9PL2sqwNjWLhgVOw3XokHiXCEjPNuDZV6uIuU0u8jvNjrK','\0','dmarcus@pnb.com'),(12,'\0','$2a$10$X/npoy4buHXOB/VSnvsxbu4eEvynCFxcSr3nEQI.tHVf724kcikoS','\0','kMoro@bpi.com'),(13,'','$2a$10$nq2V5J6XGXdEuqNFwF5YYuX.B7b9cYKtkEfAOAg4KG758FA5H/NZ6','\0','eleanordonzal@gmail.com'),(14,'\0','$2a$10$fQnORxeh06AG07.GWT18SOOBKY.4.XdkX2cT4BRkvyeiAbFIlgF7e','\0','boardcommunications@wellsfargo.com'),(15,'\0','$2a$10$ET4VGl7RXB58MV81UIXNte7F65LicYkOij7sNVmenK4BxntY2ZNH.','\0','davaolight@aboitiz.com'),(16,'\0','$2a$10$TM5cl3jQY7oP7fejtkFbtejY9NMblAR1gmAAHWnsrqclStIwl95Ia','\0','customerservice@demosaland.com'),(17,'\0','$2a$10$/JcIN8.4RPGPghGqiYY8i.gLjEMQZz6bKdYrAn5SitzGjxnVLuksW','\0','Pldt_ir_center@pldt.com.ph'),(18,'\0','$2a$10$hTdzUwtbqpJTUPwPgsaXL.DBKHr9rGTPIZPQXCbHOFroK4IZhsDV2','\0','fiona.sutherland@efo.com'),(19,'\0','$2a$10$mdSJe7hrocZocRRV3mfWrebdxIZaCHFpOK9OisPyk/kQ1sIAkv4We','\0','emma.morrison@efo.com'),(20,'\0','$2a$10$93mBBjxKs4KVrcWLSbDZVeKBdQTE.I9zdKeIQjmr/G/YSi9JFa3K2','\0','ruth.jackson@efo.com'),(21,'\0','$2a$10$Qj/up6lbkIHhK8dnqpt8WeEQc4HmyApFFOy7GJznw776orNQ7MFOG','\0','eric.knox@efo.com'),(22,'\0','$2a$10$/eREPdy5a1HwtPRoN/56UexbnYKAhBfd2dE8e1DJuK6t.F72cZtgO','\0','felicity.scott@efo.com'),(23,'\0','$2a$10$g8NE18KYs1pCG2R7qO59Lu0zh4rIwE8XfNSHgpElRnKNq8P4QBwE2','\0','deirdre.hunter@efo.com'),(24,'\0','$2a$10$UDwT267swWDQPm1cLO/B2uzO6ec0Z9hHMI11O8URACNJm59q.ULUW','\0','dylan.clark@efo.com'),(25,'\0','$2a$10$uzyGiiGSTSQ3ZRRlw0Wjo.j9jrS8wF.Z8WNgamHSLmvySH6R5B/8a','\0','vanessa.bell@efo.com'),(26,'\0','$2a$10$.3M8vwT0y2Wm7o4xoWk.m.Tin75p50CUL4lR1/rj79CsAUWhjX4Ha','\0','john.roberts@efo.com'),(27,'\0','$2a$10$.XIsYuC.seA9ZO6VqN0iCuq326ErgCaYJtjhBiancxJQr76wE2ADu','\0','piers.jones@efo.com'),(28,'\0','$2a$10$WCJoDOoPXB7TOIcuzKuvDuKCjH7Zk/l3CNbNfhEMS/eEIcZz12wsy','\0','Joe@ainsworth.com'),(29,'\0','$2a$10$cFOT.bW47LFVU.UCTvsi2uKT83kULw7io1tJvTgkYT.7OQ6BUwKc6','\0','allen@valdovinos.com'),(30,'\0','$2a$10$u719cyWdQuv3uMWAEoR7GeBrr/TwGwrTefeyt94HqketwYwvHzpVS','\0','erika@stevenson.com'),(31,'\0','$2a$10$hkq2ghImtt2Jk4wdcDE7yuQO/8hvAmI1xRaaX4tgAVJnDNmYvRWP2','\0','bonnie@rosario.com'),(32,'\0','$2a$10$t4SY.lbILbOX0lkNfpM8R.TN70ezIHfB5jLPVk.te8l55UL.slEVu','\0','robert@fletcher.com'),(33,'\0','$2a$10$K9yUTYOyqj5.KUWt.7WTxucX4xPA.P6SboDlcRkCHEmbI2IJhIJq2','\0','sylvia@onorato.com'),(34,'\0','$2a$10$KJUufFR/mG4GDQ34GJd9ieZFeSl09FD4X0Rorx3AXeq2PCCBwWFd.','\0','margaret@wright.com'),(35,'\0','$2a$10$qAvmPNz/fdR8wvxzzL8Jauxcrnw.QeMl5AjT3voD.YUejCbiapUba','\0','susan@cornell.com'),(36,'\0','$2a$10$CgwpmatrjGvYZeV26T74UemBzLykjvrZBZwr.hfgODvCFhvETSj4K','\0','margaret@billings.com'),(37,'\0','$2a$10$MN0Q1fOLQFCuv.Kr0Wbfj.BcZYqg6MIFZJ2NlQshOGy0pKcNL/Vdy','\0','timothymarcoe@gmail.com'),(39,'\0','$2a$10$KobkA6HnrNwSNg/KjIiOWuFDeLtZ2gu1xfHFpQF67EODXxHmp7rUy','\0','TimothyBMartell@dayrep.com'),(40,'\0','$2a$10$JtlY1JXcqegcsbbzsrHrNuCFxACp6J5jKEe9mln9TirNkDV8w/jlm','\0','RosellaBBaker@armyspy.com'),(41,'\0','$2a$10$XiDCZHUlfkAXECFzLeeMtOypxsYl9jGiY3iO9kBjAxDaTKWe..RUy','\0','DonaldDBrown@jourrapide.com'),(42,'\0','$2a$10$mVFGqeiAywewYOEpCggOa.vBy6H6ZqS3yTQWEh9wzhuyuBXVF.gtu','\0','RosaMLane@rhyta.com'),(43,'\0','$2a$10$9i3Zar/LPLWs3fN/nAjVguOV/Txwwl677Z4Y.tHC9kgIN/mUw0tZS','\0','KrystalTBlohm@dayrep.com'),(44,'\0','$2a$10$FgokdpmIb.cJU1FFZejyQehxclnN11TQZdTJ9itSYSqYox3TclD4a','\0','CindyMCampbell@dayrep.com'),(45,'\0','$2a$10$.M23K0fhw65LhIC8ORlWjekZHAWpxi0ZV2dmgy6tYuemq/pOXuuse','\0','PasqualeDEngland@dayrep.com'),(46,'','$2a$10$xv74iQU1o5QZLEGfs04v3euFE.6L9s48qtZF7fvqWqhboQxJn84pO','','dmarcoe@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(1,2),(2,2),(13,2),(46,2),(1,3),(2,3),(13,3),(46,3),(1,4),(2,4),(13,4),(46,4),(1,5),(2,5),(13,5),(46,5),(1,6),(2,6),(13,6),(46,6),(1,7),(2,7),(46,7);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-13 14:59:53
