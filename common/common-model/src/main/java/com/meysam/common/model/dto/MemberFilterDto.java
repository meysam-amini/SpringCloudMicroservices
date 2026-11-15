package com.meysam.common.model.dto;

import com.meysam.common.model.entity.QMember;
import com.meysam.common.model.pagination.PageQueryBaseModel;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberFilterDto extends PageQueryBaseModel {

    private String username;
    private String email;
    private String firstName;
    private String lastName;

    @Override
    public List<BooleanExpression> getBooleanExpressions() {
        List<BooleanExpression> booleanExpressionList = new ArrayList<>();
        if (Objects.nonNull(this.username))
            booleanExpressionList.add(QMember.member.username.like("%" + this.username + "%"));
        if (Objects.nonNull(this.email))
            booleanExpressionList.add(QMember.member.email.like("%" + this.email + "%"));
        if (Objects.nonNull(this.firstName))
            booleanExpressionList.add(QMember.member.firstName.like("%" + this.firstName + "%"));
        if (Objects.nonNull(this.lastName))
            booleanExpressionList.add(QMember.member.lastName.like("%" + this.lastName + "%"));
        return booleanExpressionList;
    }

}
