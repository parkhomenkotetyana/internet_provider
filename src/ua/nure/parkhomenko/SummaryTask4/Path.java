package ua.nure.parkhomenko.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public final class Path {
	
	// pages
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_LIST_TARIFFS_SERVICES_EDIT = "/WEB-INF/jsp/admin/list_tariffs_edit.jsp";
	public static final String PAGE_LIST_TARIFFS_SERVICES_DELETE = "/WEB-INF/jsp/admin/list_tariffs_delete.jsp";
	public static final String PAGE_LIST_TARIFFS_SERVICES = "/WEB-INF/jsp/common/list_tariffs_services.jsp";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE_ADD_SUBSCRIBER = "/WEB-INF/jsp/admin/add_subscriber.jsp";
	public static final String PAGE_LIST_SUBSCRIBERS = "/WEB-INF/jsp/admin/list_subscribers.jsp";
	public static final String PAGE_ACCOUNT = "/WEB-INF/jsp/subscriber/account.jsp";
	public static final String PAGE_ADD_TARIFF = "WEB-INF/jsp/admin/add_tariff.jsp";
	public static final String PAGE_EDITING_TARIFF = "/WEB-INF/jsp/admin/edit_tariff.jsp";
	public static final String PAGE_MAKE_ORDER = "/WEB-INF/jsp/subscriber/make_order.jsp";
	
	// commands
	public static final String COMMAND_LOGIN = "controller?command=login";
	public static final String COMMAND_LIST_TARIFFS = "controller?command=listTariffs";
	public static final String COMMAND_LIST_TARIFFS_SERVICES = "controller?command=listTariffsServices";
	public static final String COMMAND_ADD_SUBSCRIBER = "controller?command=addSubscriber";
	public static final String COMMAND_ADD_SUBSCRIBER_FORM = "controller?command=addSubscriberForm";
	public static final String COMMAND_LIST_SUBSCRIBERS = "controller?command=listSubscribers";
	public static final String COMMAND_ACCOUNT_PAY = "controller?command=accountPay";
	public static final String COMMAND_ADD_TARIFF = "controller?command=addTariff";
	public static final String COMMAND_ADD_TARIFF_FORM = "controller?command=addTariffForm";
	public static final String COMMAND_DELETE_TARIFF = "controller?command=deleteTariff";
	public static final String COMMAND_EDIT_TARIFF = "controller?command=editTariff";
	public static final String COMMAND_VIEW_ACCOUNT = "controller?command=viewAccount";
	public static final String COMMAND_VIEW_SETTINGS = "controller?command=viewSettings";

}