package com.scy.core.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * PageParam
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
@ApiModel("分页查询参数")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageParam {

    @ApiModelProperty(value = "查询页码", required = true, example = "1")
    private int page;

    @ApiModelProperty(value = "每页查询数", required = true, example = "10")
    private int limit;

    @ApiModelProperty(value = "查询开始行数", hidden = true, example = "0")
    private int startRowNumber;

    public PageParam(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getStartRowNumber() {
        // (当前页码-1) * 每页读取长度
        return (this.page - 1) * limit;
    }

    public static String appendLimit(String sql, PageParam pageParam) {
        return sql + " limit " + pageParam.getStartRowNumber() + ", " + pageParam.getLimit();
    }
}
