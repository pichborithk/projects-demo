package dev.pichborith.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    private boolean success = false;
    private String message;
    private T data = null;

}
