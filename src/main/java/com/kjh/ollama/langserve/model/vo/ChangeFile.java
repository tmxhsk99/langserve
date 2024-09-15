package com.kjh.ollama.langserve.model.vo;


public record ChangeFile(String newPath,String oldPath,boolean isNewFile) {

}
