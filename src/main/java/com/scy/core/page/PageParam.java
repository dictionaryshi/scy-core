package com.scy.core.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

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

    @DecimalMin(value = "0", inclusive = false, message = "页码必须大于0")
    @ApiModelProperty(value = "查询页码", required = true, example = "1")
    private int page;

    @DecimalMin(value = "0", inclusive = false, message = "每页查询量必须大于0")
    @DecimalMax(value = "20", message = "每页查询量必须小于等于20")
    @ApiModelProperty(value = "每页查询数", required = true, example = "10")
    private int limit;


    @ApiModelProperty(value = "查询开始行数", hidden = true, example = "0")
    private int offset;

    public PageParam(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getOffset() {
        // (当前页码-1) * 每页读取长度
        return (this.page - 1) * limit;
    }
}

/*
        int safetyCounter = 0;
        int count = 100;
        long lastProcessedId = 0;
        List<SkuCategoryEntity> list;
        while (true) {
            list = selectListByPage(lastProcessedId, count);

            // 检查查询结果是否为空, 为空则退出循环
            if (list.isEmpty()) {
                break;
            }

            // 处理查询结果
            processList(list);

            // 获取当前页的最后一条记录的ID
            lastProcessedId = list.get(list.size() - 1).getId();

            // 如果当前页的记录数小于每页最大记录数, 则表示已经是最后一页
            if (list.size() < count) {
                break;
            }

            // 安全计数器增加, 超过阈值则退出循环
            if (++safetyCounter > 100_0000) {
                break;
            }
        }
 */
