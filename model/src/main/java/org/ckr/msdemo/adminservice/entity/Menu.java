package org.ckr.msdemo.adminservice.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity()
@Table(name = "MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = -9008334019361686964L;


    private String code;


    private String parentCode;


    private String description;


    private String functionPoint;


    private String module;


    @Id
    @Column(name = "CODE", unique = true, nullable = false, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "PARENT_CODE", length = 100)
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "FUNCTION_POINT", length = 100)
    public String getFunctionPoint() {
        return functionPoint;
    }

    public void setFunctionPoint(String functionPoint) {
        this.functionPoint = functionPoint;
    }

    @Column(name = "MODULE", length = 100)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
