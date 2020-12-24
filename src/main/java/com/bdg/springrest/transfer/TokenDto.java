package com.bdg.springrest.transfer;

import com.bdg.springrest.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;

    public static TokenDto from(Token token) {
        return new TokenDto(token.getValue());
    }
}
