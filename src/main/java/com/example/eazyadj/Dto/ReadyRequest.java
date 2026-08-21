package com.example.eazyadj.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadyRequest {

    @JsonProperty("driverId")
    private Long driverId;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("idempotencyKey")
    private String idempotencyKey;

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    private String cid;

    @JsonProperty(
            value = "partner_order_id",
            access = JsonProperty.Access.READ_ONLY
    )
    private String partnerOrderId;

    @JsonProperty(
            value = "partner_user_id",
            access = JsonProperty.Access.READ_ONLY
    )
    private String partnerUserId;

    @JsonProperty(
            value = "item_name",
            access = JsonProperty.Access.READ_ONLY
    )
    private String itemName;

    @JsonProperty(
            value = "quantity",
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer quantity;

    @JsonProperty(
            value = "tax_free_amount",
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer taxFreeAmount;

    @JsonProperty(
            value = "vat_amount",
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer vatAmount;

    @JsonProperty(
            value = "approval_url",
            access = JsonProperty.Access.READ_ONLY
    )
    private String approvalUrl;

    @JsonProperty(
            value = "cancel_url",
            access = JsonProperty.Access.READ_ONLY
    )
    private String cancelUrl;

    @JsonProperty(
            value = "fail_url",
            access = JsonProperty.Access.READ_ONLY
    )
    private String failUrl;


    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTaxFreeAmount() {
        return taxFreeAmount;
    }

    public void setTaxFreeAmount(Integer taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
    }

    public Integer getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Integer vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

}