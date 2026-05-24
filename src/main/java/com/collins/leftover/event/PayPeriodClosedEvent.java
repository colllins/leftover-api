package com.collins.leftover.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PayPeriodClosedEvent {

    private long payPeriodId;
    private long userId;
    private LocalDateTime closedAt;
}
