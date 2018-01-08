package com.antya.common.error;

public enum ErrorCode {
    RESOURCE_NOT_FOUND_ERROR("resource.not.found"),
    MISSING_HTTP_HEADER("missing.http.header"),
    MISSING_SESSION_LISTENER_DTO("missing.session.listener.dto"),
    MISSING_BUNDLE_NAME("missing.bundle.name"),
    TOO_MANY_UIS_INTERACTIONS("too.many.uis.interactions"),
    BUNDLE_PRODUCT_LIST_EMPTY("bundle.product.list.empty"),
    COMMON_SESSION_ATTRIBUTE_BUNDLE_PRODUCT_CANNOT_BE_NULL("commonSessionAttribute.bundleProduct.cannot.be.null"),
    REST_CLIENT_EXCEPTION("rest.client.exception"),
	INVALID_PAYMENT_OPTION("invalid.payment.option"),
	PAYMENT_OPTION_ID_NOT_FOUND("payment.option.id.not.found"),
	PAYMENT_CARD_DETAILS_NOT_FOUND("payment.card.details.not.found"),
	DIRECT_DEBIT_DETAILS_NOT_FOUND("direct.debit.details.not.found"),
	DIRECT_DEBIT_DETAILS_NOT_APPLICABLE_FOR_CARD_ONLY_PAYMENT("direct.debit.details.not.applicable.for.card.only.payment"),
	NO_QUOTE_MARKED_FOR_PURCHASE("no.quote.marked.for.purchase"),
	PAYMENT_STATUS_FOR_QUOTE_NOT_INITIALZED("payment.status.for.quote.not.initialized");
	

    private final String code;

    private ErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
