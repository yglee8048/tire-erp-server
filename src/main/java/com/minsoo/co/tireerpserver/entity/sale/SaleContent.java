package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.entity.tire.TireDot;
import com.minsoo.co.tireerpserver.model.request.sale.SaleContentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale_content")
public class SaleContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", referencedColumnName = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_dot_id", referencedColumnName = "tire_dot_id")
    private TireDot tireDot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id")
    private Stock stock;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Integer quantity;

    private SaleContent(Sale sale, TireDot tireDot, Stock stock) {
        this.sale = sale;
        this.tireDot = tireDot;
        this.stock = stock;
        this.quantity = 0;
    }

    public static SaleContent of(Sale sale, TireDot tireDot, Stock stock, SaleContentRequest saleContentRequest) {
        SaleContent saleContent = new SaleContent(sale, tireDot, stock);
        return saleContent.update(saleContentRequest);
    }

    public SaleContent update(SaleContentRequest saleContentRequest) {
        this.price = saleContentRequest.getPrice();


        this.stock.addQuantity(this.quantity - saleContentRequest.getQuantity());   // 재고 변경 바로 적용
        this.quantity = saleContentRequest.getQuantity();

        return this;
    }
}
