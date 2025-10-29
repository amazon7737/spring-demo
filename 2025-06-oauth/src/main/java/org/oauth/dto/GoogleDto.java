package org.oauth.dto;

public class GoogleDto {
    private String id;
    private String email;
    private String name;

    public static GoogleDto of(String id, String email, String name) {
        GoogleDto dto = new GoogleDto();
        dto.id = id;
        dto.email = email;
        dto.name = name;
        return dto;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
