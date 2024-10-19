package com.meysam.common.customsecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleDTO implements Serializable {

    private Long id;

    @NotNull(message = "invalid name")
    @Size(max = 50)
    private String name;

    private String enKey;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    private String code;

    @NotNull(message = "invalid date time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @NotNull
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date modifiedAt;

    private Long modifiedBy;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final RoleDTO other = (RoleDTO) obj;
        if ((this.getId() == null) ? (other.getId() != null) : !this.getId().equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }
}