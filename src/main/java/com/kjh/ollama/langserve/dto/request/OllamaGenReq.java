package com.kjh.ollama.langserve.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * POST /api/generate
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class OllamaGenReq {
    /**
     * 모델명 필수
     */
    private String model;
    /**
     * 응답을 생성할 프롬프트
     */
    private String prompt;
    /**
     * 모델 응답 후의 텍스트
     */
    private String suffix;
    /**
     * 선택사항 : Base64로 인코딩된 이미지 목록 (예: 멀티 모달 모델용 llava)
     */
    private String images;

    /**
     * 고급 매개변수 (선택사항)
     */

    @Builder.Default
    /**
     * 응답을 반환할 형식 
     * 현재 json만 가능
     */
    private String format = "json";

    /**
     * Modelfile에 대한 설명에 나열된 추가 모델 매개변수 (예: temperature)
     */
    private String options;

    /**
     * 사용할 프롬프트 탬플릿 (ModelFile의 정의된 내용을 재정의함)
     */
    private String template;

    /**
     * 이전 요청에서 반환된 컨텍스트 매개변수
     * /generate는 짧은 대화 메모리를 유지하는데 사용할수 있다.
     */
    private String context;

    /**
     * 만약 false 라면 응답이 객체 스트림이 아닌 단일 응답객체로 반환된다.
     */
    private boolean stream;

    /**
     *  true인 경우 프롬프트에 서식이 적용되지 않습니다. API에 대한 요청에
     *  전체 템플릿 프롬프트를 지정하는 경우 원시 매개변수를 사용하도록 선택할 수 있습니다.
     */
    private String raw;

    /**
     * 요청 후 모델이 메모리에 로든 상태로 유지되는 시간을 제어
     * (기본값: 5m)
     */
    @JsonProperty("keep_alive")
    private String keepAlive;

}
