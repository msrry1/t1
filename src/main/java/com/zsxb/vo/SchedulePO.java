package com.zsxb.vo;

import com.zsxb.po.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClassName: ScheduleVO
 * Package: com.zsxb.vo
 * Description:
 *
 * @Author lyh
 * @Create 2023/5/29 10:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SchedulePO extends Schedule {

    // 演出厅名
    private String studioName;
    // 剧目名
    private String playName;

}
