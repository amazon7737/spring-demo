package org.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FindByTest {

    private static final Integer LOOP_COUNT = 10;
    private static final String DEFAULT_NICKNAME = "user";

    @BeforeEach
    void setUp() {
        List<Member> members = new ArrayList<>();

        for(int i=0; i< LOOP_COUNT; i++) {
            members.add(Member.of(i, DEFAULT_NICKNAME + i));
        }
    }

    @Test
    void 아이디번호별_탐색() {

    }

}
