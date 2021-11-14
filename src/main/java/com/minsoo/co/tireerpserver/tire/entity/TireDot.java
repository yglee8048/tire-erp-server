package com.minsoo.co.tireerpserver.tire.entity;

import com.minsoo.co.tireerpserver.shared.error.exceptions.BadRequestException;
import com.minsoo.co.tireerpserver.stock.entity.Stock;
import com.minsoo.co.tireerpserver.stock.model.ModifyStock;
import com.minsoo.co.tireerpserver.stock.model.StockRequest;
import com.minsoo.co.tireerpserver.tire.model.dot.TireDotRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

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

    @OneToMany(mappedBy = "tireDot", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<Stock> stocks = new HashSet<>();

    //== Business ==//
    public static TireDot of(Tire tire, String dot) {
        TireDot tireDot = new TireDot();
        return tireDot.update(tire, dot);
    }

    public TireDot update(Tire tire, String dot) {
        this.tire = tire;
        this.dot = dot;

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
