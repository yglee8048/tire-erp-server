package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.api.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStock;
import com.minsoo.co.tireerpserver.model.dto.stock.StockRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import lombok.*;

import javax.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_dot",
        uniqueConstraints = {@UniqueConstraint(name = "tire_dot_unique_dot", columnNames = {"tire_id", "dot"})})
public class TireDot {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_dot_id", length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    @Column(name = "retail_price", nullable = false)
    private Long retailPrice;

    @OneToMany(mappedBy = "tireDot", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<Stock> stocks = new HashSet<>();

    //== Business ==//
    public TireDot(Tire tire, TireDotRequest tireDotRequest) {
        this.tire = tire;
        this.dot = tireDotRequest.getDot();
        this.retailPrice = tireDotRequest.getRetailPrice();
    }

    public static TireDot of(Tire tire, TireDotRequest tireDotRequest) {
        return new TireDot(tire, tireDotRequest);
    }

    public TireDot update(Tire tire, TireDotRequest tireDotRequest) {
        this.tire = tire;
        this.dot = tireDotRequest.getDot();
        this.retailPrice = tireDotRequest.getRetailPrice();

        return this;
    }

    public Long getSumOfQuantity() {
        return this.stocks.stream()
                .map(Stock::getQuantity)
                .reduce(0L, Long::sum);
    }

    public boolean isValidAdjustQuantity(List<StockRequest> stockRequests) {
        return getSumOfStockRequestQuantity(stockRequests).equals(this.getSumOfQuantity());
    }

    public boolean isValidPurchaseQuantity(List<StockRequest> stockRequests, Long purchaseQuantity) {
        return getSumOfStockRequestQuantity(stockRequests).equals(this.getSumOfQuantity() + purchaseQuantity);
    }

    private Long getSumOfStockRequestQuantity(List<StockRequest> stockRequests) {
        return stockRequests.stream()
                .map(StockRequest::getQuantity)
                .reduce(0L, Long::sum);
    }

    public Optional<Stock> findStockByNickname(String nickname) {
        return this.stocks.stream()
                .filter(stock -> stock.getNickname().equals(nickname))
                .findAny();
    }

    public void modifyStocks(List<ModifyStock> modifyStocks) {
        // validation: 닉네임 중복이 있어서는 안 된다.
        List<String> nicknames = modifyStocks.stream()
                .map(ModifyStock::getNickname)
                .collect(Collectors.toList());
        if (nicknames.size() != (new HashSet<>(nicknames)).size()) {
            throw new BadRequestException("닉네임은 중복될 수 없습니다.");
        }

        // DELETE
        List<Stock> removed = this.stocks.stream()
                .filter(stock -> !nicknames.contains(stock.getNickname()))
                .collect(Collectors.toList());
        removed.forEach(Stock::removeFromTireDot);

        // CREATE or UPDATE
        modifyStocks.forEach(modifyStock -> findStockByNickname(modifyStock.getNickname())
                .ifPresentOrElse(
                        stock -> stock.update(this, modifyStock.getWarehouse(), modifyStock.getNickname(), modifyStock.getQuantity(), modifyStock.isLock()),
                        () -> this.stocks.add(Stock.of(this, modifyStock.getWarehouse(), modifyStock.getNickname(), modifyStock.getQuantity(), modifyStock.isLock()))));
    }
}
