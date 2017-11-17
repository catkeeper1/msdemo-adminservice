package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;
import org.ckr.msdemo.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/11/4.
 */
@Entity()
@Table(name = "FUNCTION_POINT")
public class FunctionPoint extends BaseEntity {

    private static final long serialVersionUID = -8148548168711916671L;

    private String functionPointCode;

    private String functionPointDescription;

    @Id
    @Column(name = "FUN_POINT_CODE", unique = true, nullable = false, length = 100)
    public String getFunctionPointCode() {
        return functionPointCode;
    }

    public void setFunctionPointCode(String functionPointCode) {
        this.functionPointCode = functionPointCode;
    }

    @Column(name = "FUN_POINT_DESCRIPTION", length = 100)
    public String getFunctionPointDescription() {
        return functionPointDescription;
    }

    public void setFunctionPointDescription(String functionPointDescription) {
        this.functionPointDescription = functionPointDescription;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("functionPointCode", functionPointCode)
                .add("functionPointDescription", functionPointDescription)
                .toString();
    }


}
