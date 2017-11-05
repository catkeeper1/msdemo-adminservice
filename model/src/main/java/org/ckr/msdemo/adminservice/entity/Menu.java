package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;
import org.ckr.msdemo.entity.BaseEntity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity()
@Table(name = "MENU")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = -9008334019361686964L;


    private String code;


    private String parentCode;


    private String description;


    private String functionPointCode;


    private String module;

    private FunctionPoint functionPoint;

    @Id
    @Column(name = "MENU_CODE", unique = true, nullable = false, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "PARENT_MENU_CODE", length = 100)
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Column(name = "MENU_DESCRIPTION", length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "FUN_POINT_CODE", length = 100)
    public String getFunctionPointCode() {
        return functionPointCode;
    }

    public void setFunctionPointCode(String functionPointCode) {
        this.functionPointCode = functionPointCode;
    }

    @Column(name = "MODULE", length = 100)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @OneToOne
    @JoinColumn(name = "FUN_POINT_CODE", updatable = false, insertable = false)
    public FunctionPoint getFunctionPoint() {
        return this.functionPoint;
    }

    public void setFunctionPoint(FunctionPoint functionPoint) {
        this.functionPoint = functionPoint;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("parentCode", parentCode)
                .add("description", description)
                .add("functionPointCode", functionPointCode)
                .add("module", module)
                .toString();
    }
}
