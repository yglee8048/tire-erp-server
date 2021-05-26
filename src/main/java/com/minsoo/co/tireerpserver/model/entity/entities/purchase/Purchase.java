package com.minsoo.co.tireerpserver.model.entity.entities.purchase;

import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.code.PurchaseStatus;
import com.minsoo.co.tireerpserver.model.dto.purchase.UpdatePurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Vendor;
import com.minsoo.co.tireerpserver.model.entity.entities.tire.TireDot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "purchase_id", length = 20, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", nullable = false)
    private Vendor vendor;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private PurchaseStatus status;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @OneToMany(mappedBy = "purchase", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<PurchaseContent> contents = new HashSet<>();

    //== Business ==//
    public Purchase(Vendor vendor, LocalDate purchaseDate) {
        this.vendor = vendor;
        this.status = PurchaseStatus.REQUESTED;
        this.purchaseDate = purchaseDate;
    }

    public static Purchase of(Vendor vendor, LocalDate purchaseDate) {
        return new Purchase(vendor, purchaseDate);
    }

    public Purchase update(Vendor vendor, LocalDate purchaseDate) {
        this.vendor = vendor;
        this.purchaseDate = purchaseDate;

        return this;
    }

    public Purchase updateStatus(PurchaseStatus status) {
        this.status = status;

        return this;
    }

    public Optional<PurchaseContent> findContentByTireDotId(Long tireDotId) {
        return this.contents
                .stream()
                .filter(purchaseContent -> purchaseContent.getTireDot().getId().equals(tireDotId))
                .findAny();
    }

    /**
     * 매입 항목을 수정한다.
     * 새로운 항목이 요청되면 생성하고, 기존 항목이 없다면 삭제한다.
     *
     * @param contentRequest 항목 수정 요청 DTO
     * @param tireDot        수정 요청된 TireDot 엔티티
     * @return 신규 생성인 경우 null, 수정인 경우 PurchaseContent 객체의 id를 반환한다.
     */
    public Optional<Long> modifyContents(UpdatePurchaseContentRequest contentRequest, TireDot tireDot) {
        // 신규 추가된 항목
        if (contentRequest.getPurchaseContentId() == null) {
            this.getContents()
                    .add(PurchaseContent.of(this, tireDot, contentRequest.getPrice(), contentRequest.getQuantity()));
        }
        // 기존에 있었던 항목
        else {
            this.getContents()
                    .stream()
                    .filter(purchaseContent -> purchaseContent.getId().equals(contentRequest.getPurchaseContentId()))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("매입 항목", contentRequest.getPurchaseContentId()))
                    .update(tireDot, contentRequest.getPrice(), contentRequest.getQuantity());
        }
        return Optional.ofNullable(contentRequest.getPurchaseContentId());
    }
}
