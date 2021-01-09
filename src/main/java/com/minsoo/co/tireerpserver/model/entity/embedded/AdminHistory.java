package com.minsoo.co.tireerpserver.model.entity.embedded;

import com.minsoo.co.tireerpserver.model.entity.Admin;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class AdminHistory {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Admin updatedBy;

    public AdminHistory(Admin updatedBy) {
        this.updatedBy = updatedBy;
    }
}
