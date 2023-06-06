package com.zsxb.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zsxb.po.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ClassName: SeatVO
 * Package: com.zsxb.vo
 * Description:
 *
 * @Author lyh
 * @Create 2023/6/6 8:46
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatVO extends Seat {

    // 票价
    private BigDecimal ticketPrice;

    // 座位状态 0正常，1被预定
    private Integer seatStatus;


}
