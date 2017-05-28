package org.ckr.msdemo.adminservice.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "Menu")
@Table(name = "MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = -9008334019361686964L;

    @Id
    @Column(name = "CODE", unique = true, nullable = false, length = 100)
    private String code;

    @Column(name = "PARENT_CODE", length = 100)
    private String parentCode;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @Column(name = "FUNCTION_POINT", length = 100)
    private String functionPoint;

    @Column(name = "MODULE", length = 100)
    private String module;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFunctionPoint() {
        return functionPoint;
    }

    public void setFunctionPoint(String functionPoint) {
        this.functionPoint = functionPoint;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
