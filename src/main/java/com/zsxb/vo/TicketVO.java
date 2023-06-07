package com.zsxb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zsxb.po.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * ClassName: TicketPO
 * Package: com.zsxb.po
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/29 21:32
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketVO extends Ticket {

    // 座位行号
    private Integer seatRow;
    // 座位列号
    private Integer seatColumn;
    // 演出厅名称
    private String studioName;
    // 剧目名称
    private String playName;
    /** 演出时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date schedTime;
    private Integer count;
}
