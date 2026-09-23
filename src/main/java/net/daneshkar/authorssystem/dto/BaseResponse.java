package net.daneshkar.authorssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean success;
    private T data;
    private int resultCode;
    private String resultDescription;

    public static <T> BaseResponse<T> success(T data, int resultCode, String resultDescription) {
        return new BaseResponse<>(true, data, resultCode, resultDescription);
    }

    public static <T> BaseResponse<T> failure(int resultCode, String resultDescription) {
        return new BaseResponse<>(false, null, resultCode, resultDescription);
    }
}
