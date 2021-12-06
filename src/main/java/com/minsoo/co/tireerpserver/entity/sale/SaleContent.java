package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
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
public class SaleContent extends BaseTimeEntity {

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

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Integer quantity;

    public SaleContent(Sale sale) {
        this.sale = sale;
    }

    public static SaleContent of(Sale sale, TireDot tireDot, SaleContentRequest saleContentRequest) {
        SaleContent saleContent = new SaleContent(sale);
        return saleContent.update(tireDot, saleContentRequest);
    }

    public SaleContent update(TireDot tireDot, SaleContentRequest saleContentRequest) {
        this.tireDot = tireDot;
        this.price = saleContentRequest.getPrice();
        this.quantity = saleContentRequest.getQuantity();
        return this;
    }
}
