package com.scy.core.page;

import com.scy.core.format.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页结果
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
@ApiModel("分页结果")
@Getter
@Setter
@ToString
public class PageResult<T> {

    @ApiModelProperty(value = "查询页码", required = true, example = "1")
    private int page;

    @ApiModelProperty(value = "每页查询数", required = true, example = "10")
    private int limit;

    @ApiModelProperty(value = "数据总记录数", required = true, example = "100")
    private int total;

    @ApiModelProperty(value = "数据总页数", required = true, example = "10")
    private int maxPage;

    @ApiModelProperty(value = "分页数据", required = true)
    private List<T> datas;

    public int getMaxPage() {
        if (limit == NumberUtil.ZERO.intValue()) {
            return NumberUtil.ZERO.intValue();
        }
        // 计算最大页码(向上取整)
        return (int) Math.ceil(((double) total) / limit);
    }
}
