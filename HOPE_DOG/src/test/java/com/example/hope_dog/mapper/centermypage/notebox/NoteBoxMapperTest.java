package com.example.hope_dog.mapper.centermypage.notebox;

import com.example.hope_dog.dto.centermypage.notebox.NoteboxReceiveDTO;
import com.example.hope_dog.dto.centermypage.notebox.NoteboxReciveListDTO;
import com.example.hope_dog.dto.centermypage.notebox.NoteboxSendListDTO;
import jdk.dynalink.linker.support.Guards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteBoxMapperTest {

    @Autowired
    private NoteBoxMapper noteBoxMapper;

//    보낸쪽지함 목록
    @Test
    void testFindNoteboxSendList() {
        Long noteboxMemberNo = 12L; // 테스트에 사용할 centerMemberNo

        List<NoteboxSendListDTO> result = noteBoxMapper.selectSendList(noteboxMemberNo);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty(); // 결과가 비어있지 않음을 확인
        // 추가적인 Assertions을 통해 결과 검증 가능
    }

//    받은쪽지함 목록
    @Test
    void testFindNoteboxReceiveList() {
        Long noteboxMemberNo = 21L;

        List<NoteboxReciveListDTO> result = noteBoxMapper.selectReceiveList(noteboxMemberNo);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();



    }


}