package com.example.hope_dog.service.note;

import com.example.hope_dog.mapper.note.NoteInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteInfoService {

    private final NoteInfoMapper noteInfoMapper;

    public Long getUnreadCount(Long userNo) {
        return noteInfoMapper.getUnreadCount(userNo);
    }

    public void markAllAsRead(Long userNo) {
        noteInfoMapper.markAllAsRead(userNo);
    }
}
