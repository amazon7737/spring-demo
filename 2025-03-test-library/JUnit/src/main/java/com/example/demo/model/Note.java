package com.example.demo.model;

import java.util.UUID;

public record Note(
        UUID noteId,
        String title,
        String contents,
        boolean isNew
){
    public int removeNote(UUID noteId) {
        // 노트가 있는지 검사
        if (!this.noteId.equals(noteId)) {
            throw new RuntimeException("노트가 없습니다.");
        }
        // 노트 삭제 후 성공하면 return 1
        return 1;
    }
}

