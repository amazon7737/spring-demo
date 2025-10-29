package com.example.demo.testLib;

import com.example.demo.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; // JUnit 5
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.naming.NameNotFoundException;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions;

/**
 *
 *
 */

public class JUnitTest {

    @Test
    public void basicUseJunitTest() {
        /**
         * title이 micro economics 인지 확인을 해보겠다. 이런식으로 쉬운 테스트부터 진행함
         */
        var note = new Note(UUID.randomUUID(),
                "micro economics",
                "chapter1",
                true
                );


        assertEquals("micro economics", note.title());
        assertTrue(note.isNew());

        /**
         * 이 메소드의 실행값이 null일 것으로 예상이된다 -> assertNull()
         * true , false -> assertTrue()..
         *
         * assertEquals 정도만 알고있으면 거의다 체크 가능하다
         *
         * AssertJ를 할때 조금 더 자세히 할꺼기때문에 Assertions 에서는 assertNull(), assertTrue(), assertEquals 정도만 하고 간다.
         *
         */
    }

    @Test
    @DisplayName("IDE를 사용할 때 도움이 됩니다.")
    public void messageSupplierTest() {
        var note = new Note(UUID.randomUUID(),
                "micro economics",
                "chapter1",
                false
        );

        /**
         * supplier 로 명시가 되어있으면 () -> {} 와 같이 람다식으로 표현이 가능하다는 뜻이다.
         * 코딩을 하다보면 Exception 클래스를 많이 만들어 내야 될 때가 있다. 얼마나 Exception 클래스를 예쁘게 만드냐가 시니어와 주니어의 차이 중 하나라고 생각한다.
         * 실패했을때의 메시지를 Exception 으로 만들다보면 그룹이 생기게되고 특정 도메인에 대한 Exception group을 만들어 둘 수 있게 된다.
         * 미리 Exception 코드들을 잘 정리를 테스트 짜면서 해나가다 보면 미리 정리가 가능해둘수 있을거다.
         * 언더 엔지니어링을 유지할 수 있다.
         */
        assertFalse(note.isNew(), errorMessage.apply(Code.NotNewBook));
    }

    private final Function<Code, Supplier<String>> errorMessage = code -> {
        return () -> code + " : " + code.getMessage();
    };

    enum Code {
        NotNewBook("새로운 노트가 아닙니다"), NotFound("노트가 없습니다.");

        private final String message;

        private Code(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     *
     * 에러 코드에 대해서 고민을 하셧던 분들은 위에 코드가 이해가 쉽게 잘 될것이다.
     *
     */

    /**
     * ---
     * Exception 테스트를 다루는 JUnit 테스트
     *
     */

    @Test
    @DisplayName("throw Exception")
    public void exceptionTest() {
        var note = new Note(UUID.randomUUID(),
                "micro economics",
                "chapter1",
                false
        );

        assertThrows(
            RuntimeException.class, () -> {
                note.removeNote(UUID.randomUUID());
        });

    }

    /**
     * Parameterized Value Test
     * 테스트 메소드 자체가 파라미터를 받아서 테스트할 수 있도록 하는 테스트방식
     */

    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {2015, 2025, 2035})
//    @MethodSource
    public void parameterizedTest(int year) {

    }

    /**
     * assertAll 도 있긴한데 많이 사용하지는 않았다고 함
     */

}