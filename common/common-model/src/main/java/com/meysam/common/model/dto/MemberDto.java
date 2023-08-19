package com.meysam.common.model.dto;

import com.meysam.common.model.entity.Member;
import lombok.*;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto{

    private String username;
    private String email;
    private String firstName;
    private String lastName;


    public static MemberDto maptoMemberDto(Member member){
        return MemberDto.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .build();
    }

}
