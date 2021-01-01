package com.minsoo.co.tireerpserver.model.entity.embedded;

import com.minsoo.co.tireerpserver.model.entity.Admin;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString(exclude = {"updatedBy"})
@Embeddable
public class AdminHistory {

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Admin updatedBy;

    public AdminHistory(Admin updatedBy) {
        this.updatedTime = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}
