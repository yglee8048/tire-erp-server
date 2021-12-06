package com.minsoo.co.tireerpserver.model.response.grid;

import com.minsoo.co.tireerpserver.constant.PurchaseStatus;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.minsoo.co.tireerpserver.model.response.management.VendorResponse;
import com.minsoo.co.tireerpserver.model.response.management.WarehouseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseContentGridResponse {

    private Long purchaseContentId;
    private Long purchaseId;
    private LocalDate transactionDate;
    private PurchaseStatus status;
    private VendorResponse vendor;
    private TireStandardDTO tireInfo;
    private Long sumOfOpenedStock;
    private Long sumOfStock;
    private Double averageOfPurchasePrice;
    private String dot;
    private WarehouseResponse warehouse;
    private Long quantity;
    private Integer price;
    private Long purchasePrice;

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    /**
     * purchase-content & purchase & vendor & tire-dot & tire & stock & warehouse
     */
    public PurchaseContentGridResponse(PurchaseContent purchaseContent) {
        this.purchaseContentId = purchaseContent.getId();
        this.purchaseId = purchaseContent.getPurchase().getId();
        this.transactionDate = purchaseContent.getPurchase().getTransactionDate();
        this.status = purchaseContent.getPurchase().getStatus();
        this.vendor = new VendorResponse(purchaseContent.getPurchase().getVendor());
        this.tireInfo = new TireStandardDTO(purchaseContent.getTireDot().getTire());
        this.sumOfOpenedStock = purchaseContent.getTireDot().getSumOfOpenedStockQuantity();
        this.sumOfStock = purchaseContent.getTireDot().getSumOfStockQuantity();
        this.averageOfPurchasePrice = purchaseContent.getTireDot().getAveragePurchasePrice();
        this.dot = purchaseContent.getTireDot().getDot();
        this.warehouse = new WarehouseResponse(purchaseContent.getWarehouse());
        this.quantity = purchaseContent.getQuantity();
        this.price = purchaseContent.getPrice();
        this.purchasePrice = this.quantity * this.price;

        this.createdAt = purchaseContent.getCreatedAt();
        this.lastModifiedAt = purchaseContent.getLastModifiedAt();
    }
}
