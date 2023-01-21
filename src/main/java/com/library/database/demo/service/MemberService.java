package com.library.database.demo.service;


import com.library.database.demo.entity.Book;
import com.library.database.demo.entity.Member;
import com.library.database.demo.entity.ResponseMessage;
import com.library.database.demo.repository.BookRepository;
import com.library.database.demo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired(required = false)
    MemberRepository MemberRepository;

    public List<Member> getMember() {
        return (List<Member>) MemberRepository.findAll();
    }

    public Member getMemberWithId(Integer memberId) {
        return MemberRepository.findById(memberId).get();
    }

    public Member getMemberWithName(String nama) {
        List<Member> membersList = (List<Member>) MemberRepository.findAll();
        for (Member member : membersList) {
            if (nama.equals(member.getNama())) {
                return member;
            }
        }
        return new Member(0,"NONE","NONE", "NONE");
    }

    public Member addMember(Member addedMember) {
        long size = MemberRepository.count();
        log.info("[user] REPOSITORY SIZE IS: " + size);
        Integer newId = (int) size + 1;
        log.info("[user] newId IS: " + newId);
        Member member = new Member(newId, addedMember.getNama(), addedMember.getNomorHP(), addedMember.getAlamat());

        MemberRepository.save(member);
        log.info("[user] newly added member in repository is: " + MemberRepository.findById(newId).get());
        return MemberRepository.findById(newId).get();
    }

    public Member updateMember(Member updateMember) {
        MemberRepository.save(updateMember);
        return MemberRepository.findById(updateMember.getId()).get();
    }

    public ResponseMessage deleteMember(Integer memberId) {
        MemberRepository.deleteById(memberId);
        return new ResponseMessage("Member deleted");
    }
}
