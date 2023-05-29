package com.zsxb.mapper;

import com.zsxb.po.Play;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *  Mapper 接口
 *
 * @author dz
 * @date 2023-05-09
 */
public interface PlayMapper extends BaseMapper<Play> {

    /**
     * 根据剧目id将剧目状态设置为下线
     * @param playId
     * @return
     */
    int delete(int playId);

}
