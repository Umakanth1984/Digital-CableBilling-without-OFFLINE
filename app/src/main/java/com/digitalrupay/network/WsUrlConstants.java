package com.digitalrupay.network;

/**
 * Created by Santosh on 7/5/2016.
 */
public class WsUrlConstants {
    public static String USERNAME = "[username]";//9849835128
    public static String PASSWORD = "[password]";//Uma_35128    cable_billing_pwd:123456
    public static String PARENT_ID = "[parent_id]";
    public static String FEE_ID = "[fee_id]";
    public static String PAYMENT_MODE = "[payment_mode]";
    public static String EMAIL = "[email]";//ak@ak.com
    public static String EMPLOYEE_ID = "[employee_id]";
    public static String CUSTOMER_ID = "[customer_id]";
    public static String MAC_ID="[mac_id]";
    public static String STB_NO="[stb_no]";
    public static String CARD_NO="[card_no]";
    public static String PendingAmount="[pendingamount]";
    public static String LOGIN_TYPE = "login_type";
    public static String CUSTLOGIN_TYPE = "custlogin_type";
    public static String AMOUNT = "[amount]";
    public static String TRANSACTION_TYPE = "[transaction_type]";
    public static String CHEQUE_NUMBER = "[cheque_numebr]";
    public static String BANK_NAME = "[bank_name]";
    public static String BRANCH_NAME = "[branch_name]";
    public static String TRANSACTION_DATE = "[transaction_date]";
    public static String REMARKS = "[remarks]";
    public static String MOBILE_NUMBER = "[mobile_number]";
    public static String NEXT_PAYMENT_DATE = "[next_payment_date]";
    public static String CUSTOMER_DATA = "customer_data";
    public static String PAYMENT_DATA = "payment_data";
    public static String COMPLAINT_MSG = "complaint_msg";
    public static String COMPLAINT_CATEGORY = "complaint_category_id";
    public static String COMPLAINT_ID = "COMPLAINT_ID";
    public static String COMPLAINT_STATUS = "complaint_status";
    public static String COMPLAINTS_DATA = "complaints_data";
    public static String RESULT = "result";
    public static String Adderss;
    public static int COMPLAINTS_CODE = 100;
    public static String CUSTOMER_PAYMENT = "customer_payment";
//    public static String SERVICES[] = {"Cable Billing", "Shakthi Digital", "Ramanthapur Communcation", "Cabledemo", "Srfibernet", "Sadha Digital"};
    public static String SERVICES_name;
    public static String SERVICE_MOBILE_NUMBER;
//    public static String[] loginTypes = {"school", "paper", "cable", "temple","Customer","Apartment"};
    public static String[] loginTypes = {"school", "paper", "cable","Customer","Apartment"};
    public static String[] CustloginTypes = {"cable","Apartment"};
    public static String baseUrl = "https://digitalrupay.com/digitalwebservice/";
    public static String loginUrl = baseUrl + "parentLogin?username=" + USERNAME + "&password=" + PASSWORD;
    public static String feeListUrl = baseUrl + "wards?parent_id=" + PARENT_ID;
    public static String feeDetailsUrl = baseUrl + "pay?feeId=" + FEE_ID;
    public static String feePaymentUrl = baseUrl + "get_checksum?feeId=" + FEE_ID + "&payment_mode=" + PAYMENT_MODE;
    public static String Customerid = "[customer_id]";
    public static String CustmobileNumber = "[CustmobileNumber]";
    public static String Custstb_no = "[Custstb_no]";
    public static String Custcomplaint = "[complaint]";
    public static String Custcomp_cat = "[comp_cat]";

//    public static String baseEmployeeUrl = "http://gayatricable50.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srisainathcable51.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://dmccomm52.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srirajarajeswari53.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://sireeshacable54.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://slvcablenetwork55.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://laxmicablenetworks.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://mrkcommunications.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://nikithacablenetworks.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://ramanthapurcomm.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://saipraneethcablenetwork.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://sairamcablenetwork.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://scvc.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://shakthidigital.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srfibernet.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srisaicable.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srisaistarcable.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://sse.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://ssvcnetwork.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://svcn.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://vajrabroadband.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://vsrcable.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://shivasaicomm56.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://rsbroadband.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://subhashinicable57.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://mallikarjunacabletv61.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://ganeshcabletv62.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://srisairamacabletv63.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://sriramacabletv64.digitalrupay.com/webservices/";
//      public static String baseEmployeeUrl = "http://sscboradband70.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://rajucablenetwork67.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://citycablenetwork68.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://venkatadrihills69.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://pandurangacable65.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://bwcablenetwork66.digitalrupay.com/webservices/";

//    public static String baseEmployeeUrl = "http://ganeshstartvnetwork58.digitalrupay.com/webservices/";
     public static String baseEmployeeUrl = "http://cablebilling.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://broadband.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://aptdemo.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://cabledemo1.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://sadha.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://rainbowvisions59.digitalrupay.com/webservices/";
//    public static String baseEmployeeUrl = "http://svrstartv60.digitalrupay.com/webservices/";

//     public static String baseEmployeeUrl = "http://newsaichitradarshini71.digitalrupay.com/webservices/";
//       public static String baseEmployeeUrl = "http://srimanikantacable72.digitalrupay.com/webservices/";
// public static String baseEmployeeUrl = "http://suryacablenet77.digitalrupay.com/webservices/";
//  public static String baseEmployeeUrl = "http://srcommunications73.digitalrupay.com/webservices/";
//public static String baseEmployeeUrl = "http://jabbarcommunications76.digitalrupay.com/webservices/";
//public static String baseEmployeeUrl = "http://rainbowvisions59.digitalrupay.com/webservices/";
//     public static String baseEmployeeUrl = "http://kalyanientertainer74.digitalrupay.com/webservices/";
//     public static String baseEmployeeUrl = "http://ssvcablenetwork75.digitalrupay.com/webservices/";
//     public static String baseEmployeeUrl = "http://fusion.digitalrupay.com/webservices/";

    public static String employeeLoginUrl = baseEmployeeUrl + "emp_services.php?email=" + EMAIL + "&pwd=" + PASSWORD;
    public static String customerListUrl = baseEmployeeUrl + "emp_customers.php?emp_id=" + EMPLOYEE_ID;
    public static String customerDetailsUrl = baseEmployeeUrl + "customer_details.php?cust_id=" + CUSTOMER_ID + "&emp_id=" + EMPLOYEE_ID;
    public static String cablePaymentUrl = baseEmployeeUrl + "make-a-payment.php?cust_id=" + CUSTOMER_ID + "&emp_id=" + EMPLOYEE_ID + "&amount=" +
            AMOUNT + "&transactionType=" + TRANSACTION_TYPE + "&cheque_number=" + CHEQUE_NUMBER + "&bank=" + BANK_NAME +
            "&branch=" + BRANCH_NAME + "&idate=" + TRANSACTION_DATE + "&remarks=" + REMARKS;
    public static String complaintsDetailsUrl = baseEmployeeUrl + "complaints_details.php?emp_id=" + EMPLOYEE_ID;
    public static String categoriesUrl = baseEmployeeUrl + "get_comp_cat.php";
    public static String addComplaintUrl = baseEmployeeUrl + "complaints_add.php?cust_id=" + CUSTOMER_ID + "&comp_cat=" + COMPLAINT_CATEGORY
            + "&complaint=" + COMPLAINT_MSG + "&emp_id=" + EMPLOYEE_ID;
    public static String updateComplaintUrl = baseEmployeeUrl + "complaints_edit.php?complaint_id=" + COMPLAINT_ID + "&comp_status=" + COMPLAINT_STATUS +
            "&emp_id=" + EMPLOYEE_ID + "&remarks=" + REMARKS;
    public static String todaysTotalCollectionUrl = baseEmployeeUrl + "today_collections.php?emp_id=" + EMPLOYEE_ID;
    public static String updateUserMobileUrl = baseEmployeeUrl + "edit_mobile_no.php?mobile=" + MOBILE_NUMBER + "&cust_no=" + CUSTOMER_ID+"&mac_id="+MAC_ID+"&stb_no="+STB_NO+"&card_no="+CARD_NO+"&pendingamount="+PendingAmount;
    public static String nextPaymentDateUrl = baseEmployeeUrl + "next_pay_date.php?emp_id=" + EMPLOYEE_ID + "&cust_id=" + CUSTOMER_ID + "&next_date=" + NEXT_PAYMENT_DATE;
    public static String businessLogoUrl = baseEmployeeUrl + "business_info.php";
    public static String todaysCollectionUrl = baseEmployeeUrl + "getempdetails.php?emp_id=" + EMPLOYEE_ID;
//    public static String CustomerURL="http://cablebilling.digitalrupay.com/webservices/";
//    public static String CustomerURL="http://srfibernet.digitalrupay.com/webservices/";
//    public static String CustomerURL="http://sscboradband70.digitalrupay.com/webservices/";
    public static String CustomerLogin = baseEmployeeUrl+"customer_login.php?email="+EMAIL+"&pwd="+PASSWORD+"";
    public static String Customer_Complaints_History = baseEmployeeUrl+"customer_complaints_details.php?cust_id="+Customerid ;
    public static  String Customer_Payment_History =baseEmployeeUrl+"customer_payment_details.php?cust_id="+Customerid;
    public static String Customerupdate = baseEmployeeUrl+"customer_update_details.php?cust_id="+Customerid+"&mobile="+CustmobileNumber;
    public static String  Customer_Complaints = baseEmployeeUrl+"customer_complaints_add.php?cust_id="+Customerid+"&complaint="+Custcomplaint+"&comp_cat="+Custcomp_cat;
    public static String events_info=baseEmployeeUrl+"events_info.php";
    public static String emp_tot_collections=baseEmployeeUrl+"emp_tot_collections.php?emp_id="+ EMPLOYEE_ID;
    public static String emp_tot_outstanding=baseEmployeeUrl+"emp_tot_outstanding.php?emp_id="+ EMPLOYEE_ID;
    public static String emp_summary=baseEmployeeUrl+"emp_summary.php?emp_id="+ EMPLOYEE_ID;
    public static String CustomercablePaymentUrl = baseEmployeeUrl + "make-a-payment.php?cust_id=" + CUSTOMER_ID + "&emp_id=0&amount=" +
            AMOUNT + "&transactionType="+TRANSACTION_TYPE+"&cheque_number=" + CHEQUE_NUMBER + "&bank=" + BANK_NAME +
            "&branch=" + BRANCH_NAME + "&idate=" + TRANSACTION_DATE + "&remarks=" + REMARKS;
    public static String CustGetPendingAmount=baseEmployeeUrl+"get_customer_by_id.php?cust_id="+CUSTOMER_ID;
    public static String businessCustomerLogoUrl = CustomerLogin + "business_info.php";
    public static String CustomercategoriesUrl = baseEmployeeUrl + "get_comp_cat.php";
}
