package com.gjie.wxmanager.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName operate_log
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@TableName(value ="operate_log")
public class OperateLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 访问资源名
     */
    private String pathName;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String response;

    /**
     * 
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 访问路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 访问路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 访问资源名
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * 访问资源名
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 请求参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 返回结果
     */
    public String getResponse() {
        return response;
    }

    /**
     * 返回结果
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OperateLog other = (OperateLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getPathName() == null ? other.getPathName() == null : this.getPathName().equals(other.getPathName()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getResponse() == null ? other.getResponse() == null : this.getResponse().equals(other.getResponse()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getPathName() == null) ? 0 : getPathName().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getResponse() == null) ? 0 : getResponse().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", path=").append(path);
        sb.append(", pathName=").append(pathName);
        sb.append(", params=").append(params);
        sb.append(", response=").append(response);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}