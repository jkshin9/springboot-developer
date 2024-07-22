package me.shinsunyoung.springbootdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-member.sql")
    @Test
    void getAllMembers() {

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-member.sql")
    @Test
    void getMemberById() {

        // When
        Member foundMember = memberRepository.findById(2L).orElseThrow();

        // Then
        assertThat(foundMember.getName()).isEqualTo("B");
    }

    @Sql("/insert-member.sql")
    @Test
    void getMemberByName() {

        // When
        Member foundMember = memberRepository.findByName("C").get();

        // Then
        assertThat(foundMember.getId()).isEqualTo(3);

    }

    @Test
     void saveMember() throws Exception {
        // given
        Member member = new Member(4L, "D");

        // when
        memberRepository.save(member);
        Member foundMember = memberRepository.findById(4L).orElseThrow();

        // then
        assertThat(foundMember.getName()).isEqualTo("D");

    }

    @Sql("/insert-member.sql")
    @Test
    void deleteMember() {
        // when
        memberRepository.deleteById(2L);

        // then
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }

    @Sql("/insert-member.sql")
    @Test
    void updateMember() {
        // given
        Member member = memberRepository.findById(2L).get();

        // when
        member.changeName("BC");

        // then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");

    }
}