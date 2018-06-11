package com.myapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.param.PageSortParam;

public class TUserSch extends PageSortParam {

    private Integer idSch;
    private String usernameSch;
    private Byte stateSch;
    private Boolean isdelSch;
    private String remarkSch;
    private Date addTimeSch;
    private BigDecimal moneySch;
    private Float leftMoneySch;

    public void setIdSch(Integer idSch){
        this.idSch = idSch;
    }
    
    @Condition(column = "id")
    public Integer getIdSch(){
        return this.idSch;
    }

    public void setUsernameSch(String usernameSch){
        this.usernameSch = usernameSch;
    }
    
    @Condition(column = "username")
    public String getUsernameSch(){
        return this.usernameSch;
    }

    public void setStateSch(Byte stateSch){
        this.stateSch = stateSch;
    }
    
    @Condition(column = "state")
    public Byte getStateSch(){
        return this.stateSch;
    }

    public void setIsdelSch(Boolean isdelSch){
        this.isdelSch = isdelSch;
    }
    
    @Condition(column = "isdel")
    public Boolean getIsdelSch(){
        return this.isdelSch;
    }

    public void setRemarkSch(String remarkSch){
        this.remarkSch = remarkSch;
    }
    
    @Condition(column = "remark")
    public String getRemarkSch(){
        return this.remarkSch;
    }

    public void setAddTimeSch(Date addTimeSch){
        this.addTimeSch = addTimeSch;
    }
    
    @Condition(column = "add_time")
    public Date getAddTimeSch(){
        return this.addTimeSch;
    }

    public void setMoneySch(BigDecimal moneySch){
        this.moneySch = moneySch;
    }
    
    @Condition(column = "money")
    public BigDecimal getMoneySch(){
        return this.moneySch;
    }

    public void setLeftMoneySch(Float leftMoneySch){
        this.leftMoneySch = leftMoneySch;
    }
    
    @Condition(column = "left_money")
    public Float getLeftMoneySch(){
        return this.leftMoneySch;
    }


}