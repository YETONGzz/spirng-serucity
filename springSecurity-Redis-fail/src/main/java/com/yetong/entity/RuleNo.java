package com.yetong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yetong
 * @since 2023-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rule_no")
@ApiModel(value="RuleNo对象", description="")
public class RuleNo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "seq_id", type = IdType.ASSIGN_ID)
    private String seqId;

    @ApiModelProperty(value = "流水号名称")
    private String name;

    @ApiModelProperty(value = "流水数据")
    private String digit;

    @ApiModelProperty(value = "递增值")
    private String step;

    @ApiModelProperty(value = "初始值")
    private String init;

    @ApiModelProperty(value = "当前值")
    private String currentVal;

    @ApiModelProperty(value = "生成规则")
    private String rule;


}
